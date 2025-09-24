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

    public void setTitle(String title) {
        StringUtil.validateNotNullOrBlank(title,"Title");
        this.title = title;
    }

    public String getAuthor() {
        return authorName;
    }

    public void setAuthor(String author) {
        StringUtil.validateNotNullOrBlank(author,"Author Name");
        this.authorName = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        StringUtil.validateNotNullOrBlank(genre,"Genre");
        this.genre = genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        if (publicationDate != null) {
            this.publicationDate = publicationDate;
        }else throw new IllegalArgumentException("Publication Date cannot be null");
    }

    public int getQuantity() {
        return quantity;
    }

    private void setQuantity(int quantity) {
        if (quantity > 0) {
            this.quantity = quantity;
        }else throw new IllegalArgumentException("Quantity cannot be zero/negative after change");
    }

    public void changeQuantityByValue(int value){
        if (value != 0){
            this.setQuantity(this.getQuantity()+value);
        }else throw new IllegalArgumentException("Invalid value to change book quantity!");
    }

    public boolean isAvailableForBorrow() {
        return this.getQuantity()>0;
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
        return String.format("%-16s | %-40s | %-25s | %-20s | %-16s | %-8d",
                getIsbn(), getTitle(), getAuthor(), getGenre(), getPublicationDate(), getQuantity());
    }
}
