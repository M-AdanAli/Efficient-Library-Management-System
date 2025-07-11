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
        if (book != null && getBookByIsbn(book.getIsbn()) == null){
            books.add(book);
        } else {
            System.err.println("Book already exists or invalid input!");
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

    public boolean updateBook(String isbn, Book updatedBook){
        if (updatedBook != null && isbn != null && isbn.length() == 13 && isbn.matches("\\d{13}")){
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getIsbn().equals(isbn)) {
                    books.set(i, updatedBook);
                    return true;
                }
            }
        }else {
            System.err.println("Pass valid Arguments!");
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
        List<Book> result = new ArrayList<Book>();
        if (query != null && !query.isEmpty()){
            query = query.toLowerCase();
            for (Book book : books){
                String titleLowercase = book.getTitle().toLowerCase();
                String authorLowercase = book.getAuthor().toLowerCase();
                String genreLowercase = book.getGenre().toLowerCase();
                if (titleLowercase.contains(query) || authorLowercase.contains(query) || genreLowercase.contains(query)){
                    result.add(book);
                }
            }
        }else {
            System.err.println("Pass a valid argument!");
        }
        return result;
    }

    public List<Book> listAllBooks(){
        return Collections.unmodifiableList(books);
    }

    public boolean isBookAvailable(String isbn){
            Book b = getBookByIsbn(isbn);
            return b!=null && b.getQuantity()>0;
    }
}
