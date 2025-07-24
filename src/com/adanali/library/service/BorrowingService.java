package com.adanali.library.service;

import com.adanali.library.model.*;
import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BorrowingService {
    private List<BorrowingRecord> records;
    private long recordCounter;

    public BorrowingService() {
        records = new ArrayList<>();
        recordCounter = 0;
    }

    public boolean addRecord(BorrowingRecord record){
        if (record != null && getRecordById(record.getRecordId()) == null){
            records.add(record);
            return true;
        }
        System.err.println("Record already exists or invalid Input!");
        return false;
    }

    public BorrowingRecord getRecordById(String recordId){
        if (StringUtil.isNumber(recordId)){
            for (BorrowingRecord record : records){
                if (record.getRecordId().equals(recordId)){
                    return record;
                }
            }
        }else {
            System.err.println("Pass a valid Record Id!");
        }
        return null;
    }

    public boolean borrowBook(Borrower borrower , Book book){
        if (borrower != null && book != null){
            if (book.getQuantity() <= 0 || !borrower.canBorrow()){
                System.err.println("Sorry! Either book not available for Borrowing OR you have a pending fine.");
                return false;
            }
            BorrowingRecord record = new BorrowingRecord(Long.toString(++recordCounter), book, borrower, LocalDate.now(), LocalDate.now().plusWeeks(borrower.getBorrowDurationInWeeks()));
            if (addRecord(record)){
                book.setQuantity(book.getQuantity()-1);
                return true;
            }
        }else {
            System.err.println("Invalid Arguments!");
        }
        return false;
    }

    public boolean returnBook(Borrower borrower, Book book){
        if (borrower != null && book != null ){
            for (BorrowingRecord record : records){
                if(record.getBorrower().equals(borrower) && record.getBorrowedBook().equals(book) && record.getStatus().equals("Active")){
                    if (record.setReturnDate(LocalDate.now())){
                        book.setQuantity(book.getQuantity()+1);
                        return true;
                    }
                }
            }
            System.err.println("Borrowing Record not Found!");
        }else {
            System.err.println("Invalid Arguments!");
        }
        return false;
    }

    public boolean payFine(Borrower borrower, int amount){
        if (borrower!=null && amount > 0){
            if (borrower.getPendingFine() > 0 ){
                return borrower.reducePendingFine(amount);
            }else {
                System.err.println("No pending fine");
                return false;
            }
        }
        return false;
    }

    public List<BorrowingRecord> getBorrowingsByStatus(String status){
        List<BorrowingRecord> result = new ArrayList<>();
        if (StringUtil.isValidBorrowingStatus(status)){
            for (BorrowingRecord record : records){
                if (record.getStatus().equalsIgnoreCase(status)){
                    result.add(record);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<BorrowingRecord> getActiveBorrowings(){
        return getBorrowingsByStatus("Active");
    }

    public List<BorrowingRecord> getReturnedBorrowings(){
        return getBorrowingsByStatus("Returned");
    }

    public List<BorrowingRecord> getOverdueBorrowings(){
        List<BorrowingRecord> result = new ArrayList<>();
        for (BorrowingRecord record : records){
            if (record.getReturnDate() == null && LocalDate.now().isAfter(record.getDueDate())){
                result.add(record);
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<BorrowingRecord> getBorrowingsByBorrower(Borrower borrower){
        List<BorrowingRecord> result = new ArrayList<>();
        if (borrower != null){
            for (BorrowingRecord record : records){
                if (record.getBorrower().equals(borrower)){
                    result.add(record);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<BorrowingRecord> getBorrowingsByBook(Book book){
        List<BorrowingRecord> result = new ArrayList<>();
        if (book != null){
            for (BorrowingRecord record : records){
                if (record.getBorrowedBook().equals(book)){
                    result.add(record);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<BorrowingRecord> getRecords(){
        return Collections.unmodifiableList(records);
    }
}
