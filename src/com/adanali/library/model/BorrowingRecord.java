package com.adanali.library.model;

import java.time.LocalDate;
import java.util.Objects;

public class BorrowingRecord {

    private String recordId;
    private final Book book;
    private final User borrower;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private int fine;

    public BorrowingRecord(String recordId, Book book, User borrower, LocalDate borrowDate, LocalDate dueDate) {
        this.recordId = recordId;
        this.book = book;
        this.borrower = borrower;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        status = "Active";
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        if (recordId == null || recordId.isEmpty() || !recordId.matches("\\d+")){
            System.err.println("Invalid Record ID!");
        }else {
            this.recordId = recordId;
        }
    }

    public Book getBorrowedBook() {
        return book;
    }

    public User getBorrower() {
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
        if (returnDate != null && returnDate.isAfter(borrowDate)){
            this.returnDate = returnDate;
            updateStatus();
        }else {
            System.err.println("Pass a valid return date!");
        }

    }

    public String getStatus() {
        return status;
    }

    /**
     * Updates the status based on return date and due date.
     */
    public void updateStatus() {
        if (returnDate != null) {
            status = "Returned";
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
        }else{
            System.err.println("Fine cannot be negative!");
        }
    }

    public void updateFine() {
        long days = 0;
        if (Objects.equals(status, "Overdue")) {
            days = dueDate.datesUntil(LocalDate.now()).count();
        }
        else if(returnDate != null && returnDate.isAfter(dueDate)){
            days = dueDate.datesUntil(returnDate).count();
        }
        int fine = (int) (30 * days);
        setFine(fine);
    }
}
