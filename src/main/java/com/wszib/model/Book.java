package com.wszib.model;

public class Book {
    String author;
    String title;
    int ISBN;
    private Status status;

    public Book(int ISBN, String author, String title, Status status){
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


    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("%-35s %-35s %-13s %-9s",title,author,ISBN,status);
    }

    public enum Status{
        AVAILABLE,
        BORROWED // w zasadzie przez to ze uzywam bazy z mysql to nie potrzebuje klasy enum,
        // wystarczylby String status ="AVAILABLE"
    }
}
