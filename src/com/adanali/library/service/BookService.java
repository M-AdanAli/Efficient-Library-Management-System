package com.adanali.library.service;

import com.adanali.library.model.Book;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookService {
    private List<Book> books;

    public BookService(){
        books = new ArrayList<>();
    }

    public void addBook(Book book){
        if (book != null){
            books.add(book);
        }else {
            System.err.println("Pass a valid Book!");
        }
    }

    public boolean removeBook(String isbn){
        if (isbn != null && isbn.length() == 13 && isbn.matches("\\d{13}")){
            return books.removeIf(b -> b.getIsbn().equals(isbn));
        }else {
            System.err.println("Pass a valid ISBN Number!");
        }
        return false;
    }

    public boolean updateBookStatus(String isbn, Book updatedBook){
        if (updatedBook != null){
            if (isbn != null && isbn.length() == 13 && isbn.matches("\\d{13}")){
                for (Book book : books){
                    if (book.getIsbn().equals(isbn)){
                        book = updatedBook;
                        return true;
                    }

                }
            }else {
                System.err.println("Pass a valid Isbn Number!");
            }
        }else {
            System.err.println("Pass a valid Book!");
        }
        return false;
    }

    public Book getBookByIsbn(String isbn){
        if (isbn != null && isbn.length() == 13 && isbn.matches("\\d{13}")){
            for (Book book : books){
                if (book.getIsbn().equals(isbn)){
                    return book;
                }
            }
        }else {
            System.err.println("Pass a valid ISBN number!");
        }
        return null;
    }

    public List<Book> searchBook(String query){
        if (query != null && !query.isEmpty()){
            List<Book> result = new ArrayList<Book>();
            query = query.toLowerCase();
            for (Book book : books){
                String titleLowercase = book.getTitle().toLowerCase();
                String authorLowercase = book.getAuthor().toLowerCase();
                String genreLowercase = book.getGenre().toLowerCase();
                if (titleLowercase.contains(query) || authorLowercase.contains(query) || genreLowercase.contains(query)){
                    result.add(book);
                }
            }
            return result;
        }else {
            System.err.println("Pass a valid argument!");
            return null;
        }
    }

    public List<Book> listAllBooks(){
        return Collections.unmodifiableList(books);
    }

    public boolean isBookAvailable(String isbn){
            Book b = getBookByIsbn(isbn);
            return b!=null && b.getQuantity()>0;
    }
}
