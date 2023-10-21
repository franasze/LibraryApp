package com.wszib.gui;

import com.wszib.core.Authenticator;
import com.wszib.database.BookDAO;
import com.wszib.database.UserDAO;
import com.wszib.model.Book;
import com.wszib.model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Scanner;

public class GUI {

    private static final GUI instance = new GUI();
    private static final BookDAO bookDB = BookDAO.getInstance();
    private static final Authenticator authenticator = Authenticator.getInstance();
    private final Scanner scanner = new Scanner(System.in);

    private GUI() {
    }

    public String showLogMenu() {
        System.out.println("1.Registration");
        System.out.println("2.Login");
        System.out.println("3. Exit");
        return scanner.nextLine();
    }

    public String showMenu() {
        System.out.println("1. Search ");
        System.out.println("2. Borrow book");
        System.out.println("3. Logout");

        if (authenticator.getLoggedUser().isPresent() &&
                authenticator.getLoggedUser().get().getRole().equals(User.Role.ADMIN)) {
            System.out.println("4. Add book");
            System.out.println("5. List all book");
            System.out.println("6. List borrowed books");
            System.out.println("7. List books after the deadline");
            System.out.println("8. List users");
        }
        return scanner.nextLine();
    }

    public User readLoginAndPassword() {
        User user = new User();
        System.out.println("Login:");
        user.setLogin(scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(DigestUtils.md5Hex(scanner.nextLine() + authenticator.getSeed()));
        return user;
    }

    public User readLoginAndPasswordFirstTime() {

        User user = new User();

        System.out.println("Login:");
        user.setLogin(this.scanner.nextLine());
        System.out.println("Password:");
        user.setPassword(this.scanner.nextLine());
        System.out.println("First name:");
        user.setFirstName(scanner.nextLine());
        System.out.println("Last name:");
        user.setLastName(scanner.nextLine());
        return user;
    }

    public void searchBook() {
        System.out.println("Search book");
        bookDB.listAvailableBooks(scanner.nextLine());
    }


    public void showEffectRegistration(boolean effect) {
        if (effect)
            System.out.println("Registered successful");
        else
            System.out.println("login is taken, please try again");
    }

    public void showBooksList() {
        System.out.println("Title\t\t\t\t\t\t\t\tAuthor\t\t\t\t\t\t\t\tISBN\t\t  Status");
        bookDB.getBooks().forEach(System.out::println);
        System.out.println("\n");
    }

    public void showBorrowedBooksList2() {
        bookDB.listBorrowedBooks();
    }

    public void showBorrowedBooksAfterTheDeadlineList2() {
        bookDB.listBorrowedBooksAfterTheDeadline();
    }

    public void showUsersList() {
        UserDAO userDB = UserDAO.getInstance();
        System.out.println("First name\t\t\t\t Last name\t\t\t Login\t\t\tPassword\t\t\t\t\t\t   Role");
        userDB.getUsers().forEach(System.out::println);
    }

    public void showBorrowEffect(boolean effect) {
        if (effect) {
            System.out.println("The Book has been borrowed successfully\n");
        } else {
            System.out.println("The Book doesn't exist or it is currently on loan \n");
        }
    }

    public String readTitle() {
        System.out.println("Enter title: ");
        return scanner.nextLine();
    }

    public Book readNewBookData() {
        while (true) {
            try {
                System.out.println("Title:");
                String title = scanner.nextLine();
                System.out.println("Author:");
                String author = scanner.nextLine();
                System.out.println("ISBN: ");
                int ISBN = Integer.parseInt(scanner.nextLine());
                return new Book(ISBN, author, title, true);
            } catch (NumberFormatException e) {
                System.out.println("ISBN is the International Standard Book Number,\n try again with correct number");
            }
        }
    }

    public static GUI getInstance() {
        return instance;
    }
}