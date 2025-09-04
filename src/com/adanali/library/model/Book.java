package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private final String isbn;
    private String title;
    private String authorName;
    private String genre;
    private LocalDate publicationDate;
    private int quantity;

    public Book(String isbn, String title, String authorName, String genre, LocalDate publicationDate, int quantity) {
        this.isbn = isbn;
        setTitle(title);
        setAuthor(authorName);
        setGenre(genre);
        setPublicationDate(publicationDate);
        setQuantity(quantity);
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public boolean setTitle(String title) {
        if (StringUtil.isNotNullOrBlank(title)) {
            this.title = title;
            return true;
        }else{
            System.err.println("Title cannot be empty.");
            return false;
        }
    }

    public String getAuthor() {
        return authorName;
    }

    public boolean setAuthor(String author) {
        if (StringUtil.isNotNullOrBlank(author)) {
            this.authorName = author;
            return true;
        }else{
            System.err.println("Author name cannot be empty.");
            return false;
        }
    }

    public String getGenre() {
        return genre;
    }

    public boolean setGenre(String genre) {
        if (StringUtil.isNotNullOrBlank(genre)) {
            this.genre = genre;
            return true;
        }else{
            System.err.println("Genre cannot be empty.");
            return false;
        }
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public boolean setPublicationDate(LocalDate publicationDate) {
        if (publicationDate != null) {
            this.publicationDate = publicationDate;
            return true;
        }else {
            System.err.println("Publication date cannot be null.");
            return false;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    private boolean setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
            return true;
        }else{
            System.err.println("Quantity cannot be zero/negative after change");
            return false;
        }
    }

    public boolean changeQuantityByValue(int value){
        if (value != 0){
            return this.setQuantity(this.getQuantity()+value);
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(this.getIsbn(), book.getIsbn());
    }

    @Override
    public int hashCode() {
        return this.getIsbn().hashCode();
    }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, Title=%s, Author=%s, Genre=%s, Published=%s, Quantity=%d]",
                getIsbn(), getTitle(), getAuthor(), getGenre(), getPublicationDate(), getQuantity());
    }
}
