package com.adanali.library.service;

import com.adanali.library.model.Book;
import com.adanali.library.model.Borrower;
import com.adanali.library.model.User;
import com.adanali.library.repository.BooksRepository;

import java.time.LocalDate;
import java.util.Optional;

public class LibraryService {
    private UserService userService;
    private BookService bookService;
    private BorrowingService borrowingService;

    public LibraryService(){
        userService = new UserService();
        bookService = new BookService();
        borrowingService = new BorrowingService();
    }

    // User Management

    public User login(String email, String Password){
        return userService.authenticate(email, Password);
    }

    public boolean updateUserName(String email,String usernameToSet){
        return userService.updateUserName(email,usernameToSet);

    }

    public boolean updateUserPassword(String email, String passwordToSet){
        return userService.updatePassword(email,passwordToSet);
    }

    public boolean registerStudent(User user){
        return userService.addUser(user);
    }

    public boolean removeStudent(String email){
        Optional<User> studentToRemove = userService.getUserByEmail(email);
        if (studentToRemove.isPresent() && studentToRemove.get().getRole().equals("Student")){
            userService.removeUser(email);
            return true;
        }
        return false;
    }

    public void printAllLibrarians(){
        userService.listAllUsers().stream().filter(x->x.getRole().equalsIgnoreCase("Librarian")).forEach(System.out::println);
    }

    public void printAllStudents(){
        userService.listAllUsers().stream().filter(x->x.getRole().equalsIgnoreCase("Student")).forEach(System.out::println);
    }

    public void searchForUser(String query){
        userService.searchUsers(query).forEach(System.out::println);
    }

    // Books Management

    public boolean addNewBook(String isbn, String title, String authorName, String genre, LocalDate publicationDate, int quantity){
        return bookService.addBook(new Book(isbn,title,authorName,genre,publicationDate,quantity));
    }

    public boolean removeABook(String isbn){
        if (bookService.getBookByIsbn(isbn).isPresent()){
            Book book = bookService.getBookByIsbn(isbn).get();
            if (!borrowingService.hasActiveBorrowings(book)){
                return bookService.removeBook(isbn);
            }
        }
        return false;
    }

    public boolean updateBookTitle(String isbn, String title){
        return bookService.updateBookTitle(isbn, title);
    }

    public boolean updateBookAuthor(String isbn, String author){
        return bookService.updateBookAuthor(isbn, author);
    }

    public boolean updateBookGenre(String isbn, String genre){
        return bookService.updateBookGenre(isbn, genre);
    }

    public boolean updateBookPublicationDate(String isbn, LocalDate publicationDate){
        return bookService.updateBookPublicationDate(isbn, publicationDate);
    }

    public void printAllBooks(){
        bookService.listAllBooks().forEach(System.out::println);
    }

    public void searchForBook(String query){
        bookService.searchBooks(query, BooksRepository.SearchAttribute.ALL).forEach(System.out::println);
    }

    public void searchForBookByTitle(String query){
        bookService.searchBooks(query, BooksRepository.SearchAttribute.TITLE).forEach(System.out::println);
    }

    public void searchForBookByAuthor(String query){
        bookService.searchBooks(query, BooksRepository.SearchAttribute.AUTHOR).forEach(System.out::println);
    }

    public void searchForBookByGenre(String query){
        bookService.searchBooks(query, BooksRepository.SearchAttribute.GENRE).forEach(System.out::println);
    }

    // Borrowing Services

    public boolean addBorrowedBook(String email, String isbn){
        if (userService.getUserByEmail(email).isPresent()){
            User borrower = userService.getUserByEmail(email).get();
            if (bookService.getBookByIsbn(isbn).isPresent()){
                Book bookToBorrow = bookService.getBookByIsbn(isbn).get();
                if (borrower instanceof Borrower){
                    return borrowingService.borrowBook((Borrower) borrower,bookToBorrow);
                }
            }
        }
        return false;
    }

    public boolean addReturnedBook(String email, String isbn){
        if (userService.getUserByEmail(email).isPresent()){
            User borrower = userService.getUserByEmail(email).get();
            if (bookService.getBookByIsbn(isbn).isPresent()){
                Book bookToBorrow = bookService.getBookByIsbn(isbn).get();
                if (borrower instanceof Borrower){
                    return borrowingService.returnBook((Borrower) borrower,bookToBorrow);
                }
            }
        }
        return false;
    }

    public boolean addPaidFine(String email,int amount){
        if (userService.getUserByEmail(email).isPresent()){
            User borrower = userService.getUserByEmail(email).get();
            if (borrower instanceof Borrower){
                return borrowingService.payFine((Borrower) borrower,amount);
            }
        }
        return false;
    }

    // See Borrowing Records

    public void printActiveBorrowings(){
        borrowingService.getActiveBorrowings().forEach(System.out::println);
    }

    public void printReturnedBorrowings(){
        borrowingService.getReturnedBorrowings().forEach(System.out::println);
    }

    public void printOverdueBorrowings(){
        borrowingService.getOverdueBorrowings().forEach(System.out::println);
    }

    public void printBorrowingsByUser(String email){
        if (userService.getUserByEmail(email).isPresent()){
            User borrower = userService.getUserByEmail(email).get();
            if (borrower instanceof Borrower){
                borrowingService.getBorrowingsByBorrower((Borrower) borrower).forEach(System.out::println);
            }
        }
    }

    public void printBorrowingsByBook(String isbn){
        if (bookService.getBookByIsbn(isbn).isPresent()){
            Book book = bookService.getBookByIsbn(isbn).get();
            borrowingService.getBorrowingsByBook(book).forEach(System.out::println);
        }
    }

    public void printAllBorrowingRecords(){
        borrowingService.getAllRecords().forEach(System.out::println);
    }

}
