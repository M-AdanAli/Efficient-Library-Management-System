package com.adanali.library.app;

import com.adanali.library.model.Librarian;
import com.adanali.library.model.Student;
import com.adanali.library.model.User;
import com.adanali.library.service.LibraryService;
import com.adanali.library.util.ConsoleUtil;

import java.time.LocalDate;

public class LibraryApp {
    private static final LibraryService library = new LibraryService();
    private static User currentUser;

    public static void main(String[] args){
        boolean reRun = true;
        do {
            ConsoleUtil.clearConsole();
            loginScreen();
            ConsoleUtil.delay(500);
            if (currentUser == null){
                ConsoleUtil.printNewLines(2);
                System.out.println("Wanna Retry?");
                System.out.println("Yes - Enter \"1\"");
                System.out.println("No - Enter any key");
                if (!ConsoleUtil.inputString("Enter").equals("1")) {
                    reRun = false;
                }
            }else {
                homeScreen(currentUser);
            }
        }while (reRun);


    }

    private static void loginScreen(){
        ConsoleUtil.printLibraryHeader();
        System.out.println("- Login -");
        ConsoleUtil.printNewLines(2);
        String email = ConsoleUtil.inputString("Enter E-mail");
        String password = ConsoleUtil.inputString("Enter Password");

        currentUser = library.login(email,password);
    }

    private static void homeScreen(User user){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());
            if (user instanceof Librarian){
                System.out.println("1 - My Profile");
                System.out.println("2 - Manage Users");
                System.out.println("3 - Manage Books");
                System.out.println("4 - Borrowing Service");
                System.out.println("5 - Manage Borrowing Records");
                System.out.println("6 - Logout");

                String choice = ConsoleUtil.inputString("Enter your choice");
                switch (choice){
                    case "1" :
                        myProfile();
                        break;
                    case "2" :
                        manageUsers();
                        break;
                    case "3" :
                        manageBooks();
                        break;
                    case "4" :
                        borrowingService();
                        break;
                    case "5":
                        seeBorrowingRecords();
                        break;
                    case "6" :
                        currentUser = null;
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        ConsoleUtil.delay(1000);
                }
            }else if (user instanceof Student){
                System.out.println("1 - My profile");
                System.out.println("2 - Search Books");
                System.out.println("3 - Manage My Borrowings");
                System.out.println("4 - Logout");

                String choice = ConsoleUtil.inputString("Enter your choice");
                switch (choice){
                    case "1" :
                        myProfile();
                        break;
                    case "2" :
                        searchBooksOptions();
                        break;
                    case "3" :
                        library.printBorrowingsByUser(currentUser.getEmail());
                        break;
                    case "4" :
                        exit = true;
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid choice!");
                        ConsoleUtil.delay(1000);
                }

            }
        }
    }

    private static void myProfile(){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());
            System.out.println("Your Role : "+currentUser.getRole());
            ConsoleUtil.printNewLines(1);

            System.out.println("1 - Change User Name");
            System.out.println("2 - Change Password");
            System.out.println("3 - Go Back");

            String choice = ConsoleUtil.inputString("Enter your choice");
            switch (choice){
                case "1":
                    while (true){
                        String newUserName = ConsoleUtil.inputString("Enter new Username");
                        if (library.updateUserName(currentUser.getEmail(), newUserName)) {break;}
                    }
                    break;
                case "2":
                    while (true){
                        String passwordToCheck = ConsoleUtil.inputString("Enter current password");
                        if (passwordToCheck.equals(currentUser.getPassword())){
                            String newPassword = ConsoleUtil.inputString("Enter new password");
                            if (library.updateUserPassword(currentUser.getEmail(), newPassword)){
                                exit = true;
                                break;
                            }
                        }else {
                            System.err.println("Password does not match!");
                        }
                    }
                    break;
                case "3":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice!");
                    ConsoleUtil.delay(1000);
            }
        }
    }

    private static void manageUsers(){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());

            System.out.println("1 - Add Student");
            System.out.println("2 - Remove Student");
            System.out.println("3 - List of Users");
            System.out.println("4 - Search a User");
            System.out.println("5 - Go Back");

            String choice = ConsoleUtil.inputString("Enter your choice");
            switch (choice){
                case "1":
                    while (true){
                        User userToAdd = new Student(ConsoleUtil.inputString("Enter the name of student"),
                                ConsoleUtil.inputString("Enter the email of student"),
                                ConsoleUtil.inputString("Enter the password of student"),
                                ConsoleUtil.inputString("Enter the address of student"));
                        if (library.registerStudent(userToAdd)){break;}
                    }
                    break;
                case "2":
                    String emailOfTheStudentToRemove = ConsoleUtil.inputString("Enter the email of the Student");
                    if (library.removeStudent(emailOfTheStudentToRemove)){
                        System.out.println("Student removed successfully");
                    }else {
                        System.out.println("No such Student Exists!");
                    }
                    break;
                case "3":
                    boolean reRun = true;
                    while (reRun){
                        System.out.println("1 - Librarians");
                        System.out.println("2 - Students");
                        System.out.println("3 - Go Back");
                        String innerChoice = ConsoleUtil.inputString("Enter your choice");
                        switch (innerChoice){
                            case "1":
                                library.printAllLibrarians();
                                break;
                            case "2":
                                library.printAllStudents();
                                break;
                            case "3":
                                reRun = false;
                                break;
                            default:
                                System.err.println("Invalid Choice! Retry...");
                        }
                    }
                    break;
                case "4":
                    String searchQuery = ConsoleUtil.inputString("Enter search query");
                    library.searchForUser(searchQuery);
                    ConsoleUtil.delay(3500);
                    break;
                case "5":
                    exit=true;
                    break;
                default:
                    System.err.println("Invalid Input! Try Again...");
            }
        }


    }

    private static void manageBooks(){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());

            System.out.println("1- Add a book");
            System.out.println("2- Remove a book");
            System.out.println("3- Update a book");
            System.out.println("4- List all books");
            System.out.println("5- Search a book");
            System.out.println("6- Go back");

            String userChoice = ConsoleUtil.inputString("Enter your choice");

            switch (userChoice) {
                case "1":
                    boolean isBookAdded = library.addNewBook(ConsoleUtil.inputString("Enter the ISBN"),
                            ConsoleUtil.inputString("Enter the Title"),
                            ConsoleUtil.inputString("Enter Author name"),
                            ConsoleUtil.inputString("Enter Genre"),
                            ConsoleUtil.inputDate("Enter the Publication Date"),
                            ConsoleUtil.inputInteger("Enter the number of copies being added"));
                    if (isBookAdded) {
                        System.out.println("Book Added successfully");
                    } else {
                        System.out.println("Failed to add book");
                    }
                    break;
                case "2":
                    String isbnOfTheBookToRemove = ConsoleUtil.inputString("Enter the ISBN of the book to remove");
                    if (library.removeABook(isbnOfTheBookToRemove)) {
                        System.out.println("Book removed successfully");
                    } else {
                        System.out.println("Failed to remove the book");
                    }
                    break;
                case "3":
                    boolean reRun = true;
                    while (reRun) {
                        System.out.println("1 - Update Book's Title");
                        System.out.println("2 - Update Book's Author");
                        System.out.println("3 - Update Book's Genre");
                        System.out.println("4 - Update Book's Publication Date");
                        System.out.println("5 - Go Back");

                        String innerChoice = ConsoleUtil.inputString("Enter your choice");
                        String isbnOfTheBookToUpdate = ConsoleUtil.inputString("Enter the isbn of book");
                        switch (innerChoice) {
                            case "1":
                                String updatedTitle = ConsoleUtil.inputString("Enter the updated Title");
                                if (library.updateBookTitle(isbnOfTheBookToUpdate, updatedTitle)) {
                                    System.out.println("Title updated successfully");
                                } else {
                                    System.out.println("Failed to update Title");
                                }
                                break;
                            case "2":
                                String updatedAuthor = ConsoleUtil.inputString("Enter the updated Author Name");
                                if (library.updateBookAuthor(isbnOfTheBookToUpdate, updatedAuthor)) {
                                    System.out.println("Author Name updated successfully");
                                } else {
                                    System.out.println("Failed to update Author Name ");
                                }
                                break;
                            case "3":
                                String updatedGenre = ConsoleUtil.inputString("Enter the updated Genre");
                                if (library.updateBookGenre(isbnOfTheBookToUpdate, updatedGenre)) {
                                    System.out.println("Genre updated successfully");
                                } else {
                                    System.out.println("Failed to update Genre");
                                }
                                break;
                            case "4":
                                LocalDate updatedPublicationDate = ConsoleUtil.inputDate("Enter the updated Publication Date");
                                if (library.updateBookPublicationDate(isbnOfTheBookToUpdate, updatedPublicationDate)) {
                                    System.out.println("Genre updated successfully");
                                } else {
                                    System.out.println("Failed to update Genre");
                                }
                                break;
                            case "5":
                                reRun = false;
                                break;
                            default:
                                System.err.println("Invalid Input! Try Again...");
                        }
                    }
                    break;
                case "4":
                    library.printAllBooks();
                    break;
                case "5":
                    searchBooksOptions();
                    break;
                case "6":
                    exit = true;
                    break;
                default:
            }
        }
    }

    public static void searchBooksOptions(){
        boolean exit = false;
        while (!exit) {
            System.out.println("1 - Search Overall");
            System.out.println("2 - Search by title");
            System.out.println("3 - Search by Author Name");
            System.out.println("4 - Search by Genre");
            System.out.println("5 - Go Back");

            String innerChoice = ConsoleUtil.inputString("Enter your choice");
            String searchQuery = ConsoleUtil.inputString("Enter search query");
            switch (innerChoice) {
                case "1":
                    library.searchForBook(searchQuery);
                    break;
                case "2":
                    library.searchForBookByTitle(searchQuery);
                    break;
                case "3":
                    library.searchForBookByAuthor(searchQuery);
                    break;
                case "4":
                    library.searchForBookByGenre(searchQuery);
                    break;
                case "5":
                    exit = true;
                    break;
                default:
                    System.err.println("Invalid Input! Try Again...");
            }
        }
    }

    private static void borrowingService(){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());

            System.out.println("1 - Add a borrowed book");
            System.out.println("2 - Add a returned book");
            System.out.println("3 - Add paid fine");
            System.out.println("4 - Go Back");

            String choice = ConsoleUtil.inputString("Enter your choice");
            switch (choice){
                case "1":
                    String emailOfThePotentialBorrower = ConsoleUtil.inputString("Enter the email of borrower");
                    String isbnOfTheBookToBorrow = ConsoleUtil.inputString("Enter the isbn of book");
                    if (library.addBorrowedBook(emailOfThePotentialBorrower,isbnOfTheBookToBorrow)){
                        System.out.println("Record added successfully");
                    }else {
                        System.out.println("Failed to add the record");
                    }
                    break;
                case "2":
                    String emailOfTheBorrower = ConsoleUtil.inputString("Enter the email of borrower");
                    String isbnOfBorrowedBook = ConsoleUtil.inputString("Enter the isbn of book");
                    if (library.addReturnedBook(emailOfTheBorrower,isbnOfBorrowedBook)){
                        System.out.println("Record has been updated successfully");
                    }else {
                        System.out.println("Failed to update record");
                    }
                    break;
                case "3":
                    String emailOfPayer = ConsoleUtil.inputString("Enter the email of Paying borrower");
                    int paymentAmount = ConsoleUtil.inputInteger("Enter the amount paid");
                    if (library.addPaidFine(emailOfPayer,paymentAmount)){
                        System.out.println("Record updated successfully");
                    }else {
                        System.out.println("Failed to update record");
                    }
                    break;
                case "4":
                    exit = true;
                    break;
                default:
                    System.err.println("Invalid choice! Try again...");
            }
        }
    }

    private static void seeBorrowingRecords(){
        boolean exit = false;
        while (!exit){
            ConsoleUtil.printLibraryHeader();
            ConsoleUtil.printWelcomeUser(currentUser.getName());

            System.out.println("1 - print Borrowing records by Status");
            System.out.println("2 - print Borrowing records by User");
            System.out.println("3 - print Borrowing records by Book");
            System.out.println("4 - print all Borrowing Records");
            System.out.println("5 - Go Back");

            String choice = ConsoleUtil.inputString("Enter your choice");
            switch (choice){
                case "1":
                    boolean reRun = true;
                    while (reRun){
                        System.out.println("1 - Active Borrowings");
                        System.out.println("2 - Returned Borrowings");
                        System.out.println("3 - Overdue Borrowings");
                        System.out.println("4 - Go Back");

                        String innerChoice = ConsoleUtil.inputString("Enter your choice");
                        switch (innerChoice){
                            case "1":
                                library.printActiveBorrowings();
                                break;
                            case"2":
                                library.printReturnedBorrowings();
                                break;
                            case"3":
                                library.printOverdueBorrowings();
                                break;
                            case"4":
                                reRun = false;
                                break;
                            default:
                                System.out.println("Invalid Choice! Try again...");
                        }
                    }
                    break;
                case "2":
                    String email = ConsoleUtil.inputString("Enter User's Email");
                    library.printBorrowingsByUser(email);
                    break;
                case "3":
                    String isbn = ConsoleUtil.inputString("Enter ISBN of Book");
                    library.printBorrowingsByBook(isbn);
                    break;
                case "4":
                    library.printAllBorrowingRecords();
                    break;
                case "5":
                    exit = true;
                    break;
                default:
            }
        }
    }
}