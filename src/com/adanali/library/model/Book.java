package com.adanali.library.model;

import com.adanali.library.util.StringUtil;

import java.time.LocalDate;
import java.util.Objects;

public class Book {

    private String isbn;
    private String title;
    private String authorName;
    private String genre;
    private LocalDate publicationDate;
    private int quantity;

    public Book(String isbn, String title, String authorName, String genre, LocalDate publicationDate, int quantity) {
        setIsbn(isbn);
        setTitle(title);
        setAuthor(authorName);
        setGenre(genre);
        setPublicationDate(publicationDate);
        setQuantity(quantity);
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String inputIsbn) {
        if (StringUtil.isValidIsbn(inputIsbn)) {
            this.isbn = inputIsbn;
        }else {
            System.err.println("Not a valid ISBN number!");
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (StringUtil.isNotNullOrBlank(title)) {
            this.title = title;
        }else{
            System.err.println("Title cannot be empty.");
        }

    }

    public String getAuthor() {
        return authorName;
    }

    public void setAuthor(String author) {
        if (StringUtil.isNotNullOrBlank(author)) {
            this.authorName = author;
        }else{
            System.err.println("Author name cannot be empty.");
        }

    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (StringUtil.isNotNullOrBlank(genre)) {
            this.genre = genre;
        }else{
            System.err.println("Genre cannot be empty.");
        }

    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate != null) {
            this.publicationDate = publicationDate;
        }else {
            System.err.println("Publication date cannot be null.");
        }

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }else{
            System.err.println("Quantity cannot be zero/negative.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals(getIsbn(), book.getIsbn()) && Objects.equals(getTitle(), book.getTitle()) && Objects.equals(authorName, book.authorName) && Objects.equals(getGenre(), book.getGenre()) && Objects.equals(getPublicationDate(), book.getPublicationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsbn(), getTitle(), authorName, getGenre(), getPublicationDate());
    }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, Title=%s, Author=%s, Genre=%s, Published=%s, Quantity=%d]",
                getIsbn(), getTitle(), getAuthor(), getGenre(), getPublicationDate(), getQuantity());
    }
}
