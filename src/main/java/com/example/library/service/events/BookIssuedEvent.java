package com.example.library.service.events;

import com.example.library.model.Book;
import com.example.library.model.Borrower;

public class BookIssuedEvent {
    private Book book;
    private Borrower borrower;

    public BookIssuedEvent(Book book, Borrower borrower) {
        this.book = book;
        this.borrower = borrower;
    }

    public Book getBook() { return book; }
    public Borrower getBorrower() { return borrower; }
}
