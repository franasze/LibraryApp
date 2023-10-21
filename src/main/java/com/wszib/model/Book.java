package com.wszib.model;

public class Book {
    private final String author;
    private final String title;
    private final int ISBN;
    private final boolean status;

    public Book(int ISBN, String author, String title, boolean status){
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }


    public String getTitle() {
        return title;
    }


    public int getISBN() {
        return ISBN;
    }


    public boolean getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%-35s %-35s %-13s %-9s",title,author,ISBN,status? "AVAILABLE" : "BORROWED");
    }


}
