package com.example.library.model;

import java.util.Date;

public class HoldRequest {
    private Borrower borrower;
    private Book book;
    private Date requestDate;

    public HoldRequest(Borrower borrower, Book book, Date requestDate) {
        this.borrower = borrower;
        this.book = book;
        this.requestDate = requestDate;
    }

    public Borrower getBorrower() { return borrower; }
    public Book getBook() { return book; }
    public Date getRequestDate() { return requestDate; }
}
