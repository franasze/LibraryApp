package com.wszib.database;

import com.wszib.model.Book;
import com.wszib.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookDAOTest {

    @BeforeEach
    public void prepare(){
        System.out.println("Przygotowanie do testow !!!");
    }

    @Test
    public void testSuccessedAddNewBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        List<Book> books;
        Book book = new Book(20, "Henryk Sienkiewicz", "Potop", Book.Status.AVAILABLE);
        bookDAO.addBook(book);
        books = bookDAO.getBooks();
        boolean flag = false;
        for(int i=0;i<books.size();i++) {
            if(book.getISBN() == (books.get(i).getISBN())
                    && book.getAuthor().equals(books.get(i).getAuthor())
                    && book.getTitle().equals(books.get(i).getTitle()))
                flag = true;
        }
       Assertions.assertTrue(flag);
    }

    @Test
    public void testSuccessedRentBook() {

        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book(101, "Andrzej Sapkowski", "Miecz Przeznaczenia", Book.Status.AVAILABLE);
        User user = new User("admin","admin", User.Role.ADMIN,"Arek","Kera");
        boolean borrowedBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertTrue(borrowedBook);
    }

    @Test
    public void testFailedRentBook() {
        BookDAO bookDAO = BookDAO.getInstance();
        Book book = new Book(102, "Test", "1234567890", Book.Status.AVAILABLE);
        User user = new User("admin","admin",User.Role.ADMIN,"Arek","Kera");
        boolean borrowedBook = bookDAO.rentBook(book.getTitle(), user);
        Assertions.assertFalse(borrowedBook);
    }

}
