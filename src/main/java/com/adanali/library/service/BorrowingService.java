package com.adanali.library.service;

import com.adanali.library.exceptions.EntityDuplicationException;
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

    public void addRecord(String recordId, Book book, Borrower borrower, LocalDate borrowDate, LocalDate dueDate) throws EntityDuplicationException {
        BorrowingRecord borrowingRecord = new BorrowingRecord(recordId, book, borrower, borrowDate, dueDate);
        if (getRecordById(borrowingRecord.getRecordId()).isEmpty()){
            records.add(borrowingRecord);
        }else throw new EntityDuplicationException(BorrowingRecord.class, "Record already exists!");
    }

    public Optional<BorrowingRecord> getRecordById(String recordId){
        StringUtil.validateNotNullOrBlank(recordId,"Record ID");
        return records.stream().filter(record -> record.getRecordId().equals(recordId)).findFirst();
    }

    public void borrowBook(Borrower borrower , Book book) throws EntityDuplicationException {
        if (borrower.canBorrow()){
            if (book.isAvailableForBorrow()){
                String newId;
                do {
                    newId = System.currentTimeMillis()+ "-" + UUID.randomUUID();
                }while (getRecordById(newId).isPresent());
                addRecord(newId, book, borrower, LocalDate.now(), LocalDate.now().plusWeeks(borrower.getBorrowDurationInWeeks()));
                book.changeQuantityByValue(-1);
            }else throw new IllegalStateException("Book is not available for borrowing right now.");
        }else throw new IllegalStateException("Borrower has a pending fine!");
    }

    public void returnBook(Borrower borrower, Book book){
        BorrowingRecord borrowingRecord = records.stream().filter(record -> record.getBorrower().equals(borrower) && record.getBorrowedBook().equals(book) && record.getStatus().equals(BorrowingStatus.ACTIVE)).findFirst().orElseThrow(()->new IllegalStateException("Borrower does not have an active borrowing record for the book : "+book.getTitle()));
        borrowingRecord.setReturnDate(LocalDate.now());
        book.changeQuantityByValue(1);
    }

    public void payFine(Borrower borrower, int amount){
        if (amount > 0){
            if (borrower.getPendingFine() > 0 ){
                borrower.reducePendingFine(amount);
            }else throw new IllegalStateException("No pending fine");
        }else throw new IllegalArgumentException("Invalid payment amount!");
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
        }else throw new IllegalArgumentException("Pass valid Book!");
    }

    public List<BorrowingRecord> getAllRecords(){
        return records.stream().collect(Collectors.toUnmodifiableList());
    }
}
