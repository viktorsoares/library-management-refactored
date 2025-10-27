package com.example.library.factory;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.Loan;

import java.time.Instant;

public class EntityFactory {

    public static Book createBook(String title, String subject, String author) {
        Book b = new Book();
        b.setTitle(title);
        b.setSubject(subject);
        b.setAuthor(author);
        b.setIssued(false);
        return b;
    }

    public static Borrower createBorrower(String name, String role) {
        Borrower u = new Borrower();
        u.setName(name);
        u.setRole(role);
        return u;
    }

    public static Loan createLoan(Borrower borrower, Book book) {
        Loan l = new Loan();
        l.setBorrower(borrower);
        l.setBook(book);
        l.setIssuedDate(Instant.now());
        l.setState(Loan.State.BORROWED);
        return l;
    }
}
