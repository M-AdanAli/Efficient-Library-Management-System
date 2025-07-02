package com.adanali.library.model;

import java.time.LocalDate;

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
        if (inputIsbn == null || inputIsbn.length() != 13 || !inputIsbn.matches("\\d{13}")) {
            throw new IllegalArgumentException("ISBN must be a 13-digit number.");
        }
        this.isbn = inputIsbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty.");
        }
        this.title = title;
    }

    public String getAuthor() {
        return authorName;
    }

    public void setAuthor(String author) {
        if (author == null || author.isEmpty()) {
            throw new IllegalArgumentException("Author name cannot be empty.");
        }
        this.authorName = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        if (genre == null || genre.isEmpty()) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }
        this.genre = genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate == null) {
            throw new IllegalArgumentException("Publication date cannot be null.");
        }
        this.publicationDate = publicationDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("Book[ISBN=%s, Title=%s, Author=%s, Genre=%s, Published=%s, Quantity=%d]",
                isbn, title, authorName, genre, publicationDate, quantity);
    }
}
