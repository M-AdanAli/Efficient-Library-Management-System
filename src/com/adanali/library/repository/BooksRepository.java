package com.adanali.library.repository;

import com.adanali.library.model.Book;

import java.util.*;
import java.util.stream.Collectors;

public class BooksRepository implements RepositoryPattern<Book,String>{
    private Set<Book> bookSet;

    public BooksRepository(){
        this.bookSet = new HashSet<>();
    }

    public enum SearchAttribute{
        TITLE, AUTHOR, GENRE, ALL
    }

    public List<Book> searchBooks(String query , SearchAttribute attribute){

        String queryLower = query.toLowerCase();

        return bookSet.stream()
                .filter(book -> switch (attribute) {
                    case AUTHOR -> book.getAuthor().toLowerCase().contains(queryLower);
                    case TITLE -> book.getTitle().toLowerCase().contains(queryLower);
                    case GENRE -> book.getGenre().toLowerCase().contains(queryLower);
                    case ALL -> book.getAuthor().toLowerCase().contains(queryLower) ||
                            book.getTitle().toLowerCase().contains(queryLower) ||
                            book.getGenre().toLowerCase().contains(queryLower);
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean add(Book book) {
        if (getById(book.getIsbn()).isEmpty()){
            bookSet.add(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(String isbn) {
        return bookSet.removeIf(book -> book.getIsbn().equals(isbn));
    }

    @Override
    public Optional<Book> getById(String isbn) {
        return bookSet.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
    }

    @Override
    public List<Book> getAll() {
        return List.copyOf(bookSet);
    }
}
