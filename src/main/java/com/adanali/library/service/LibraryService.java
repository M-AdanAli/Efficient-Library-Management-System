package com.adanali.library.service;

import com.adanali.library.exceptions.EntityDuplicationException;
import com.adanali.library.exceptions.InvalidCredentialsException;
import com.adanali.library.exceptions.EntityNotFoundException;
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

    public Optional<User> login(String email, String password){
        try {
            User user = userService.authenticate(email, password);
            System.out.println("Login Successful!");
            return Optional.of(user);
        }
        catch (IllegalArgumentException | InvalidCredentialsException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e) {
            System.out.println("User not registered!");
        }
        return Optional.empty();
    }

    public void updateUserName(String email,String usernameToSet){
        try {
            userService.updateUserName(email,usernameToSet);
            System.out.println("Username Successfully Updated!");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("User not registered!");
        }
    }

    public void updateUserPassword(String email, String passwordToSet){
        try {
            userService.updatePassword(email,passwordToSet);
            System.out.println("Password Successfully Updated!");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("User not registered!");
        }
    }

    public void registerStudent(String name, String email, String password, String address){
        try {
            userService.addUser(name,email,password,address, UserService.UserRole.STUDENT);
            System.out.println("Student is successfully registered!");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (EntityDuplicationException e) {
            System.out.println("User is already registered");
        }
    }

    public void removeStudent(String email){
        try {
            User studentToRemove = userService.getUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email : "+email));
            if (studentToRemove.getRole().equals("Student")){
                userService.removeUser(email);
                System.out.println("Student removed successfully");
            }else throw new IllegalStateException("Provided email ("+email+") does not belongs to a Student");
        } catch (IllegalStateException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("Student does not exists!");
        }
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

    public void addNewBook(String isbn, String title, String authorName, String genre, LocalDate publicationDate, int quantity){
        try {
            bookService.addBook(isbn,title,authorName,genre,publicationDate,quantity);
            System.out.println("Book is successfully added in the Library.");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityDuplicationException e) {
            System.out.println("Book is already added!");
        }
    }

    public void removeABook(String isbn){
        try {
            Book book = bookService.getBookByIsbn(isbn).orElseThrow(()-> new EntityNotFoundException("Book not found with ISBN : "+isbn));
            if (!borrowingService.hasActiveBorrowings(book)){
                bookService.removeBook(isbn);
                System.out.println("Book is successfully removed!");
            }else throw new IllegalStateException("Could not remove the Book, it has active borrowings!");
        }catch (IllegalArgumentException | IllegalStateException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e) {
            System.out.println("Book does not exist!");
        }

    }

    public void updateBookTitle(String isbn, String title){
        try {
            bookService.updateBookTitle(isbn, title);
            System.out.println("Title updated successfully");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e) {
            System.out.println("Book does not exist!");
        }
    }

    public void updateBookAuthor(String isbn, String author){
        try {
            bookService.updateBookAuthor(isbn, author);
            System.out.println("Author Name updated successfully");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e) {
            System.out.println("Book does not exist!");
        }
    }

    public void updateBookGenre(String isbn, String genre){
        try {
            bookService.updateBookGenre(isbn, genre);
            System.out.println("Genre updated successfully");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e) {
            System.out.println("Book does not exist!");
        }
    }

    public void updateBookPublicationDate(String isbn, LocalDate publicationDate){
        try {
            bookService.updateBookPublicationDate(isbn, publicationDate);
            System.out.println("Publication date updated successfully");
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e){
            System.out.println("Book does not exist!");
        }
    }

    public void incrementBookQuantity(String isbn, int value){
        try {
            bookService.increaseBookQuantity(isbn, value);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e){
            System.out.println("Book does not exist!");
        }
    }

    public void decrementBookQuantity(String isbn, int value){
        try {
            bookService.decreaseBookQuantity(isbn, value);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }catch (EntityNotFoundException e){
            System.out.println("Book does not exist!");
        }
    }

    public void printAllBooks(){
        bookService.listAllBooks().forEach(System.out::println);
    }

    public void searchForBookOverall(String query){
        try {
            bookService.searchBooks(query, BooksRepository.SearchAttribute.ALL).forEach(System.out::println);
        }catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
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

    public void addBorrowedBook(String email, String isbn){
        try {
            User borrower = userService.getUserByEmail(email).orElseThrow(()->new EntityNotFoundException("There is no Borrower registered with the email : "+email));
            Book bookToBorrow = bookService.getBookByIsbn(isbn).orElseThrow(()->new EntityNotFoundException("There is no book in library with ISBN : "+isbn));
            if (borrower instanceof Borrower){
                borrowingService.borrowBook((Borrower) borrower,bookToBorrow);
                System.out.println("Record added successfully");
            }else throw new IllegalAccessException("Only Users with borrow rights can borrow!");
        } catch (IllegalArgumentException | EntityDuplicationException | IllegalStateException | EntityNotFoundException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addReturnedBook(String email, String isbn){
        try {
            User borrower = userService.getUserByEmail(email).orElseThrow(()->new EntityNotFoundException("There is no Borrower registered with the email : "+email));
            Book bookToBorrow = bookService.getBookByIsbn(isbn).orElseThrow(()->new EntityNotFoundException("There is no book in library with ISBN : "+isbn));
            if (borrower instanceof Borrower) {
                borrowingService.returnBook((Borrower) borrower, bookToBorrow);
                System.out.println("Record has been updated successfully");
            }else throw new IllegalAccessException("The User don't even have borrowing rights!");
        } catch (IllegalArgumentException | EntityNotFoundException | IllegalAccessException | IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addPaidFine(String email,int amount){
        try {
            User borrower = userService.getUserByEmail(email).orElseThrow(()->new EntityNotFoundException("There is no Borrower registered with the email : "+email));
            if (borrower instanceof Borrower){
                borrowingService.payFine((Borrower) borrower,amount);
                System.out.println("The Borrower's pending amount has been reduced by amount : "+amount);
            }else throw new IllegalAccessException("The User don't even have borrowing rights!");
        }catch (EntityNotFoundException | IllegalAccessException | IllegalArgumentException | IllegalStateException e){
            System.out.println(e.getMessage());
        }

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
        try {
            User borrower = userService.getUserByEmail(email).orElseThrow(()->new EntityNotFoundException("There is no Borrower registered with the email : "+email));
            if (borrower instanceof Borrower){
                borrowingService.getBorrowingsByBorrower((Borrower) borrower).forEach(System.out::println);
            }else throw new IllegalAccessException("The User don't even have borrowing rights!");
        } catch (EntityNotFoundException | IllegalAccessException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printBorrowingsByBook(String isbn){
        try {
            Book book = bookService.getBookByIsbn(isbn).orElseThrow(()->new EntityNotFoundException("There is no book in library with ISBN : "+isbn));
            borrowingService.getBorrowingsByBook(book).forEach(System.out::println);
        }catch (EntityNotFoundException | IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public void printAllBorrowingRecords(){
        borrowingService.getAllRecords().forEach(System.out::println);
    }

}
