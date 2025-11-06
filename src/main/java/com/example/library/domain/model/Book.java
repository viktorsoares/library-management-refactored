package com.example.library.domain.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOK")
@Setter
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;
    private String subject;
    private String publisher;
    private String ISBN;
    private String edition;
    private int publicationYear;
    private double price;
    private int pages;
    private String status;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrowedBy;

    private LocalDate dueDate;

    @OneToMany(mappedBy = "book")
    private List<Loan> loans;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoldRequest> holdRequests = new ArrayList<>();

    public Book() {
    }

    public Book(String title, String author, String subject, String publisher,
                String ISBN, String edition, int publicationYear, double price, int pages, String status) {
        this.title = title;
        this.author = author;
        this.subject = subject;
        this.publisher = publisher;
        this.ISBN = ISBN;
        this.edition = edition;
        this.publicationYear = publicationYear;
        this.price = price;
        this.pages = pages;
        this.status = status;
    }

    public void printInfo() {
        System.out.printf("Book: %s | Author: %s | Status: %s\n", title, author, status);
    }

    public boolean checkAvailability() {
        return "available".equalsIgnoreCase(status);
    }
}
