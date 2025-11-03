package com.example.library.facade;

import com.example.library.model.*;
import com.example.library.service.LoanPolicyResolver;
import com.example.library.service.listener.EventListener;
import com.example.library.service.events.BookIssuedEvent;

import java.util.ArrayList;

public class LibraryFacade {

    private ArrayList<Book> books;
    private ArrayList<Person> persons;
    private LoanPolicyResolver loanPolicyResolver;
    private ArrayList<EventListener> listeners;

    public LibraryFacade(ArrayList<Book> books, ArrayList<Person> persons) {
        this.books = books;
        this.persons = persons;
        this.loanPolicyResolver = new LoanPolicyResolver();
        this.listeners = new ArrayList<>();
    }

    public void addListener(EventListener listener) {
        listeners.add(listener);
    }

    public void issueBook(Borrower borrower, Book book) {
        if (!loanPolicyResolver.canBorrow(borrower, book)) {
            System.out.println("Cannot issue book due to policy constraints.");
            return;
        }

        book.setIssuedStatus(true);
        Loan loan = new Loan(borrower, book, null, null, new java.util.Date(), null, false);
        borrower.addBorrowedBook(loan);

        BookIssuedEvent event = new BookIssuedEvent(book, borrower);
        for (EventListener listener : listeners) {
            listener.onBookIssued(event);
        }

        System.out.println("Book issued successfully!");
    }
}
