package com.adanali.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowingRecord {

    private final String recordId;
    private final Book book;
    private final Borrower borrower;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private BorrowingStatus status;
    private int fine;

    public BorrowingRecord(String recordId, Book book, Borrower borrower, LocalDate borrowDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.fine=0;
        updateStatus();
    }

    public String getRecordId() {
        return recordId;
    }

    public Book getBorrowedBook() {
        return book;
    }

    public Borrower getBorrower() {
        return borrower;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        if (returnDate != null && !returnDate.isBefore(getBorrowDate())){
            this.returnDate = returnDate;
            updateStatus();
            updateFine();
        }else throw new IllegalArgumentException("Pass a valid return date!");
    }

    public BorrowingStatus getStatus() {
        return status;
    }

    /**
     * Updates the status based on return date and due date.
     */
    public void updateStatus() {
        if (returnDate != null) {
            if (returnDate.isAfter(dueDate)){
                status = BorrowingStatus.OVERDUE;
            }else {
                status = BorrowingStatus.RETURNED;
            }
        } else if (LocalDate.now().isAfter(dueDate)) {
            status = BorrowingStatus.OVERDUE;
        } else {
            status = BorrowingStatus.ACTIVE;
        }
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        if (fine >= 0){
            this.fine = fine;
            borrower.addPendingFine(fine);
        }else throw new IllegalArgumentException("Fine cannot be negative!");
    }

    public void updateFine() {
        int fineToUpdate = 0;
        if (Objects.equals(status, BorrowingStatus.OVERDUE)) {
            long daysOverdue = dueDate.datesUntil(returnDate).count();
            fineToUpdate = (int) (30 * daysOverdue);
        }
        setFine(fineToUpdate);
    }

    @Override
    public String toString() {
        return String.format("BorrowingRecord[RecordId=%s, Book=%s, Borrower=%s, BorrowDate=%s, DueDate=%s, ReturnDate=%s, status=%s, fine=%s]", getRecordId(), getBorrowedBook(), getBorrower(), getBorrowDate(), getDueDate(), getReturnDate(), getStatus(), getFine() );
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof BorrowingRecord)) {
            return false;
        }
        BorrowingRecord other = (BorrowingRecord) obj;
        return Objects.equals(this.getRecordId(),other.getRecordId());
    }

    @Override
    public int hashCode() {
        return recordId.hashCode();
    }

}
