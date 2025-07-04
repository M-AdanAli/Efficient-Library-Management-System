package com.adanali.library.model;

import java.util.ArrayList;
import java.util.List;

public class Student extends User{
    private List<Book> borrowedBooks = new ArrayList<>();
    private int pendingFine;

    String address;

    public Student(String name, String userId, String password , String address) {
        super(name, userId, password);
        setAddress(address);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (address == null || address.isEmpty()){
            System.err.println("Address cannot be empty!");
        }else {
            this.address = address;
        }
    }

    public int getPendingFine() {
        return pendingFine;
    }

    public void addPendingFine(int fine) {
        if (fine > 0){
            this.pendingFine = this.pendingFine + fine;
        }else {
            System.err.println("Fine cannot be negative!");
        }
    }

    public void reducePendingFine(int fine) {
        if (fine > 0){
            if (fine <= this.pendingFine){
                this.pendingFine = this.pendingFine - fine;
            }else{
                this.pendingFine = 0;
                System.out.println("Pending fine is less.");
            }
        }else {
            System.err.println("Fine cannot be negative!");
        }
    }
    public void addBorrowedBook(Book book){
        if (book == null){
            System.err.println("pass a valid book!");
        }else {
            borrowedBooks.add(book);
        }
    }

    public void removeBorrowedBook(Book book){
        if (book == null){
            System.err.println("pass a valid book!");
        }else {
            borrowedBooks.remove(book);
        }
    }

    public void showBorrowedBooks(){
        for (Book book : borrowedBooks){
            System.out.println("| "+book +" |");
        }
    }

    @Override
    public String getRole() {
        return "Student";
    }
}
