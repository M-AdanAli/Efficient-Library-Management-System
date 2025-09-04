package com.adanali.library.service;

import com.adanali.library.model.Book;
import com.adanali.library.repository.BooksRepository;
import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookService {
    private BooksRepository booksRepository;

    public BookService(){
        booksRepository = new BooksRepository();
    }

    public boolean addBook(Book book){
        if (book != null){
            return booksRepository.add(book);
        } else {
            System.err.println("Invalid input!");
            return false;
        }
    }

    public boolean removeBook(String isbn){
        if (StringUtil.isValidIsbn(isbn)){
            return booksRepository.remove(isbn);
        }else {
            System.err.println("Pass a valid ISBN Number!");
            return false;
        }
    }

    public Optional<Book> getBookByIsbn(String isbn){
        if (StringUtil.isValidIsbn(isbn)){
            return booksRepository.getById(isbn);
        }else{
            System.out.println("Invalid Isbn");
            return Optional.empty();
        }
    }

    public List<Book> listAllBooks(){
        return booksRepository.getAll();
    }

    public boolean updateBookTitle(String isbn, String newTitle) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && StringUtil.isNotNullOrBlank(newTitle)) {
            book.setTitle(newTitle);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid Title!");
            return false;
        }
    }

    public boolean updateBookAuthor(String isbn, String author) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && StringUtil.isNotNullOrBlank(author)) {
            book.setAuthor(author);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid Author!");
            return false;
        }
    }

    public boolean updateBookGenre(String isbn, String genre) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && StringUtil.isNotNullOrBlank(genre)) {
            book.setGenre(genre);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid Genre!");
            return false;
        }
    }

    public boolean updateBookPublicationDate(String isbn, LocalDate publicationDate) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && publicationDate != null) {
            book.setPublicationDate(publicationDate);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid Publication Date!");
        }
        return false;
    }

    public boolean increaseBookQuantity(String isbn, int value) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && value>0) {
            book.changeQuantityByValue(value);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid increment!");
            return false;
        }
    }

    public boolean decreaseBookQuantity(String isbn, int value) {
        Book book = getBookByIsbn(isbn).get();
        if (book != null && value>0) {
            book.changeQuantityByValue(-value);
            return true;
        }else {
            System.err.println("Book does not exists OR Invalid decrement!");
            return false;
        }
    }

    public List<Book> searchBooks(String query , BooksRepository.SearchAttribute searchAttribute){
        if (StringUtil.isNotNullOrBlank(query)){
            return booksRepository.searchBooks(query,searchAttribute);
        }
        return Collections.unmodifiableList(new ArrayList<>(0));
    }

    public boolean isBookAvailable(String isbn){
            Optional<Book> book = getBookByIsbn(isbn);
            return book.isPresent() && book.get().getQuantity()>0;
    }
}
