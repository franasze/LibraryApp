package com.wszib.database;

import com.wszib.model.Book;
import com.wszib.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class BookDAOTest {

    @BeforeEach
    public void prepare(){
        System.out.println("Preparation for the test\n !!!");
    }

    @Test
    public void testSuccessfullyAddNewBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> books;
        Book book = new Book(20, "Henryk Sienkiewicz", "Potop", true);
        bookDAO.addBook(book);
        books = bookDAO.getBooks();
        boolean flag = false;
        for (Book value : books) {
            if (book.getISBN() == (value.getISBN())
                    && book.getAuthor().equals(value.getAuthor())
                    && book.getTitle().equals(value.getTitle())) {
                flag = true;
                break;
            }
        }
       Assertions.assertTrue(flag);
    }

    @Test
    public void testSuccessfullyRentBook() {

        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book(101, "Andrzej Sapkowski", "Miecz Przeznaczenia", true);
        Optional<User> user = Optional.of(new User("admin","admin", User.Role.ADMIN,"Arek","Kera"));
        boolean borrowedBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertTrue(borrowedBook);
    }

    @Test
    public void testFailedRentBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book(102, "Test", "1234567890", true);
        Optional<User> user = Optional.of(new User("admin","admin",User.Role.ADMIN,"Arek","Kera"));
        boolean borrowedBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertFalse(borrowedBook);
    }
}
