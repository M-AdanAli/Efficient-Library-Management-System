package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

/**
 * Represents a student user in the library system.
 */
public class Student extends User implements Borrower{
    private int pendingFine;
    private String address;

    public Student(String name, String email, String password, String address) {
        super(name, email, password);
        setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        StringUtil.validateNotNullOrBlank(address,"Address");
        this.address = address;
    }

    @Override
    public int getPendingFine() {
        return pendingFine;
    }

    @Override
    public void addPendingFine(int fine) {
        if (fine > 0) {
            this.pendingFine += fine;
        } else throw new IllegalArgumentException("Fine cannot be negative!");
    }

    @Override
    public void reducePendingFine(int fine) {
        if (fine > 0) {
            if (fine <= this.pendingFine) {
                this.pendingFine -= fine;
            } else {
                this.pendingFine = 0;
                System.out.println("Pending fine is less.");
                System.out.printf("Following amount should be returned to the user : %d",fine-pendingFine);
            }
        } else throw new IllegalArgumentException("Fine cannot be negative!");
    }

    @Override
    public byte getBorrowDurationInWeeks() {
        return 2;
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public String toString() {
        return String.format("Student[E-mail=%s, Name=%s, Address=%s, PendingFine=%d]",
                getEmail(), getName(), getAddress(), getPendingFine());
    }
}
