package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents a student user in the library system.
 */
public class Student extends User {
    private List<Book> borrowedBooks;
    private int pendingFine;
    private String address;

    public Student(String name, String email, String password, String address) {
        super(name, email, password);
        setAddress(address);
        borrowedBooks = new ArrayList<>();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (StringUtil.isNotNullOrBlank(address)) {
            this.address = address;
        } else {
            System.err.println("Address cannot be empty!");
        }
    }

    public int getPendingFine() {
        return pendingFine;
    }

    public void addPendingFine(int fine) {
        if (fine > 0) {
            this.pendingFine += fine;
        } else {
            System.err.println("Fine cannot be negative!");
        }
    }

    public void reducePendingFine(int fine) {
        if (fine > 0) {
            if (fine <= this.pendingFine) {
                this.pendingFine -= fine;
            } else {
                this.pendingFine = 0;
                System.out.println("Pending fine is less.");
            }
        } else {
            System.err.println("Fine cannot be negative!");
        }
    }

    public void addBorrowedBook(Book book) {
        if (book == null) {
            System.err.println("Pass a valid book!");
        } else {
            borrowedBooks.add(book);
        }
    }

    public void removeBorrowedBook(Book book) {
        if (book == null) {
            System.err.println("Pass a valid book!");
        } else {
            borrowedBooks.remove(book);
        }
    }

    /** Returns an unmodifiable view of the borrowed books list. */
    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(borrowedBooks);
    }

    /** Prints all borrowed books to the console. */
    public void printBorrowedBooks() {
        for (Book book : borrowedBooks) {
            System.out.println("| " + book + " |");
        }
    }

    /** Returns true if the student can borrow books (no pending fine). */
    public boolean canBorrow() {
        return pendingFine == 0;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return String.format("Student[E-mail=%s, Name=%s, Address=%s, PendingFine=%d]",
                getEmail(), getName(), address, pendingFine);
    }
}
