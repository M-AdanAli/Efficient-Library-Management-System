package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowingRecord {

    private String recordId;
    private final Book book;
    private final Borrower borrower;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private int fine;

    public BorrowingRecord(String recordId, Book book, Borrower borrower, LocalDate borrowDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        fine=0;
        updateStatus();
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        if (StringUtil.isNumber(recordId)){
            this.recordId = recordId;
        }else {
            System.err.println("Invalid Record ID!");
        }
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

    public boolean setReturnDate(LocalDate returnDate) {
        if (returnDate != null && !returnDate.isBefore(getBorrowDate())){
            this.returnDate = returnDate;
            updateStatus();
            updateFine();
            return true;
        }else {
            System.err.println("Pass a valid return date!");
        }
        return false;
    }

    public String getStatus() {
        return status;
    }

    /**
     * Updates the status based on return date and due date.
     */
    public void updateStatus() {
        if (returnDate != null) {
            if (returnDate.isAfter(dueDate)){
                status = "Overdue";
            }else {
                status = "Returned";
            }
        } else if (LocalDate.now().isAfter(dueDate)) {
            status = "Overdue";
        } else {
            status = "Active";
        }
    }

    public int getFine() {
        return fine;
    }

    public void setFine(int fine) {
        if (fine >= 0){
            this.fine = fine;
            borrower.addPendingFine(fine);
        }else{
            System.err.println("Fine cannot be negative!");
        }
    }

    public void updateFine() {
        long days = 0;
        if (Objects.equals(status, "Overdue")) {
            days = dueDate.datesUntil(LocalDate.now()).count();
        }
        int fine = (int) (30 * days);
        setFine(fine);
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
