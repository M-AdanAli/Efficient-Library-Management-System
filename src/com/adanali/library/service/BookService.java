package com.adanali.library.service;

import com.adanali.library.model.Book;
import com.adanali.library.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookService {
    private List<Book> books;

    public BookService(){
        books = new ArrayList<>();
    }

    public void addBook(Book book){
        if (book != null && searchBookByIsbn(book.getIsbn()) == null){
            books.add(book);
        } else {
            System.err.println("Book already exists or invalid input!");
        }
    }

    public boolean removeBook(String isbn){
        if (StringUtil.isValidIsbn(isbn)){
            return books.removeIf(b -> b.getIsbn().equals(isbn));
        }else {
            System.err.println("Pass a valid ISBN Number!");
        }
        return false;
    }

    public boolean updateBook(String isbn, Book updatedBook){
        if (updatedBook != null && StringUtil.isValidIsbn(isbn)){
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

    public Book searchBookByIsbn(String isbn){
        if (StringUtil.isValidIsbn(isbn)){
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
        if (StringUtil.isNotNullOrBlank(query)){
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
        return Collections.unmodifiableList(result);
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> result = new ArrayList<>();
        if (StringUtil.isNotNullOrBlank(title)) {
            title = title.toLowerCase();
            for (Book book : books) {
                if (book.getTitle().toLowerCase().contains(title)) {
                    result.add(book);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<Book> searchBookByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        if (StringUtil.isNotNullOrBlank(author)) {
            author = author.toLowerCase();
            for (Book book : books) {
                if (book.getAuthor().toLowerCase().contains(author)) {
                    result.add(book);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    public List<Book> listAllBooks(){
        return Collections.unmodifiableList(books);
    }

    public boolean isBookAvailable(String isbn){
            Book b = searchBookByIsbn(isbn);
            return b!=null && b.getQuantity()>0;
    }
}
