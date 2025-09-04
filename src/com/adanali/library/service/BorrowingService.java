package com.adanali.library.service;

import com.adanali.library.model.Book;
import com.adanali.library.model.Borrower;
import com.adanali.library.model.BorrowingRecord;
import com.adanali.library.model.BorrowingStatus;
import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class BorrowingService {
    private Set<BorrowingRecord> records;

    public BorrowingService() {
        records = new HashSet<>();
    }

    public boolean addRecord(BorrowingRecord borrowingRecord){
        if (borrowingRecord != null && getRecordById(borrowingRecord.getRecordId()).isEmpty()){
            records.add(borrowingRecord);
            return true;
        }else {
            System.err.println("Record already exists or invalid Input!");
            return false;
        }
    }

    public Optional<BorrowingRecord> getRecordById(String recordId){
        if (StringUtil.isNotNullOrBlank(recordId)){
            return records.stream().filter(record -> record.getRecordId().equals(recordId)).findFirst();
        }else {
            System.err.println("Pass valid record id!");
            return Optional.empty();
        }
    }

    public boolean borrowBook(Borrower borrower , Book book){
        if (borrower != null && book != null){
            if (!borrower.canBorrow()){
                System.err.println("Sorry! borrower has a pending fine.");
                return false;
            }
            String newId;
            do {
                newId = System.currentTimeMillis()+ "-" + UUID.randomUUID();
            }while (getRecordById(newId).isPresent());
            BorrowingRecord newRecord = new BorrowingRecord(newId, book, borrower, LocalDate.now(), LocalDate.now().plusWeeks(borrower.getBorrowDurationInWeeks()));
            if (addRecord(newRecord)){
                book.changeQuantityByValue(-1);
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
                if(record.getBorrower().equals(borrower) && record.getBorrowedBook().equals(book) && record.getStatus().equals(BorrowingStatus.ACTIVE)){
                    if (record.setReturnDate(LocalDate.now())){
                        book.changeQuantityByValue(1);
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

    public List<BorrowingRecord> getBorrowingsByStatus(BorrowingStatus status){
        return records.stream()
                .filter(record -> record.getStatus().equals(status))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<BorrowingRecord> getActiveBorrowings(){
        return getBorrowingsByStatus(BorrowingStatus.ACTIVE);
    }

    public List<BorrowingRecord> getReturnedBorrowings(){
        return getBorrowingsByStatus(BorrowingStatus.RETURNED);
    }

    public List<BorrowingRecord> getOverdueBorrowings(){
        return records.stream()
                .filter(record->( record.getReturnDate() == null ) && LocalDate.now().isAfter( record.getDueDate() ) )
                .collect(Collectors.toUnmodifiableList());
    }

    public List<BorrowingRecord> getBorrowingsByBorrower(Borrower borrower){
        return records.stream()
                .filter(record->record.getBorrower().equals(borrower))
                .collect(Collectors.toUnmodifiableList());
    }

    public List<BorrowingRecord> getBorrowingsByBook(Book book){
        return records.stream()
                .filter(record->record.getBorrowedBook().equals(book))
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean hasActiveBorrowings (Book book){
        if (book != null){
            return records.stream()
                    .filter(record->record.getBorrowedBook().equals(book))
                    .anyMatch(record->record.getStatus().equals(BorrowingStatus.ACTIVE));
        }else {
            System.err.println("Pass valid Book!");
            return false;
        }
    }

    public List<BorrowingRecord> getAllRecords(){
        return records.stream().collect(Collectors.toUnmodifiableList());
    }
}
