package com.wszib.database;

import com.wszib.model.Book;
import com.wszib.model.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class BookDAO {

    private static final BookDAO instance = new BookDAO();
    private final Connection connection;

    private BookDAO(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/libraryDB",
                    "root",
                    "");
        }catch (SQLException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public List<Book> getBooks() { // sql -> java
        ArrayList<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM tbooks";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int ISBN = rs.getInt("ISBN");
                String author = rs.getString("author");
                String title = rs.getString("title");
                boolean status = rs.getBoolean("status");

                Book book = new Book(ISBN,author,title,status);
                        books.add(book);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public boolean rentBook(String title, Optional <User> user) {
        try {

            String sql = "SELECT * FROM tbooks WHERE title = ?";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return false;
            }

            int ISBN = rs.getInt("ISBN");
            boolean status = rs.getBoolean("status");

            if(!rs.getBoolean("status")){
                return false;
            }
            String sql2 = "SELECT * FROM tuser WHERE login = ?";

            PreparedStatement ups = this.connection.prepareStatement(sql2);

            ups.setString(1, user.get().getLogin());

            ResultSet urs = ups.executeQuery();

            if (!urs.next()) {
                System.out.println("User doesn't exist");
            }

            String login = urs.getString("login");
            LocalDate returnDate = LocalDate.now().plusDays(14);

            String sql3 = "INSERT INTO tborrowed (login, ISBN, return_date_of_book) VALUES (?, ?, ?)";
            PreparedStatement bps = this.connection.prepareStatement(sql3);
            bps.setString(1, login);
            bps.setInt(2, ISBN);
            bps.setString(3, returnDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
            bps.executeUpdate();

            String updateSql = "UPDATE tbooks SET status = ? WHERE ISBN = ?";
            PreparedStatement updatePs = this.connection.prepareStatement(updateSql);
            updatePs.setBoolean(1, false);
            updatePs.setInt(2,  ISBN);
            updatePs.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBook(Book book) { // java -> sql
        try {
            String sql = "INSERT INTO tbooks " +
                    "(author, title, ISBN, status) VALUES (?,?,?,?)";
            PreparedStatement ps = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getAuthor());
            ps.setString(2, book.getTitle());
            ps.setInt(3, book.getISBN());
            ps.setBoolean(4, book.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void listBorrowedBooks() {
        try {
            String query = "SELECT tbooks.title,tbooks.author,tbooks.ISBN,tuser.first_name,tuser.last_name,tborrowed.return_date_of_book "+
                    "FROM tbooks JOIN tborrowed ON tbooks.ISBN = tborrowed.ISBN " +
                    "JOIN tuser ON tuser.login = tborrowed.login WHERE tborrowed.return_date_of_book >= ?";
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("Borrowed books:");
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("ISBN: " + rs.getString("ISBN"));
                System.out.println("First name: " + rs.getString("first_name"));
                System.out.println("Last name: " + rs.getString("last_name"));
                System.out.println("Days Left: " + (int) ChronoUnit.DAYS.between(LocalDate.now(),
                        rs.getDate("return_date_of_book").toLocalDate()));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listAvailableBooks(String varchar) {
        try {
            String variable = "%"+ varchar.toLowerCase() +"%";
            String sql = "SELECT * FROM tbooks WHERE (LOWER(title) LIKE ? OR LOWER(author) LIKE ? OR LOWER(ISBN) LIKE ?)";
            PreparedStatement ps = this.connection.prepareStatement(sql);
            ps.setString(1, variable);
            ps.setString(2, variable);
            ps.setString(3, variable);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("ISBN"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getBoolean("status"));
                System.out.println(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void listBorrowedBooksAfterTheDeadline() {
        try {
            String query = "SELECT tbooks.title,tbooks.author,tbooks.ISBN,tuser.first_name,tuser.last_name,tborrowed.return_date_of_book "+
                    "FROM tbooks JOIN tborrowed ON tbooks.ISBN = tborrowed.ISBN "+
                    "JOIN tuser ON tuser.login = tborrowed.login WHERE tborrowed.return_date_of_book < ?";
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                System.out.println("Books after the deadline:");
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("ISBN: " + rs.getString("ISBN"));
                System.out.println("First name: " + rs.getString("first_name"));
                System.out.println("Last name: " + rs.getString("last_name"));
                System.out.println("Days after the deadline: " + (int) ChronoUnit.DAYS.between(rs.getDate("return_date_of_book").toLocalDate(), LocalDate.now()));
                System.out.println("\n");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static BookDAO getInstance(){
        return instance;
    }

}