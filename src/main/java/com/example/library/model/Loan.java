package com.example.library.model;

import java.util.Date;

public class Loan {
    private Borrower borrower;
    private Book book;
    private Staff issuer;
    private Staff receiver;
    private Date issuedDate;
    private Date returnDate;
    private boolean fineStatus;

    public Loan(Borrower borrower, Book book, Staff issuer, Staff receiver, Date issuedDate, Date returnDate, boolean fineStatus) {
        this.borrower = borrower;
        this.book = book;
        this.issuer = issuer;
        this.receiver = receiver;
        this.issuedDate = issuedDate;
        this.returnDate = returnDate;
        this.fineStatus = fineStatus;
    }

    public Borrower getBorrower() { return borrower; }
    public Book getBook() { return book; }
    public Staff getIssuer() { return issuer; }
    public Staff getReceiver() { return receiver; }
    public Date getIssuedDate() { return issuedDate; }
    public Date getReturnDate() { return returnDate; }
    public boolean getFineStatus() { return fineStatus; }

    public double computeFine1() {
        // placeholder: pode ser refatorado com Strategy Pattern
        return 0;
    }
}
