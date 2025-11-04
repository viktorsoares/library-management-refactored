package com.example.library.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "LOAN")
@Setter
@Getter
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private LocalDate issueDate;

    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "PERSON_ID")
    private Person borrower;

    @ManyToOne
    @JoinColumn(name = "ISSUER_ID")
    private Staff issuer;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private Staff receiver;


    private boolean finePaid;
    private boolean copyReturned;
    private double amount;

    public Loan() {
    }

    public Loan(LocalDate issueDate, LocalDate dueDate) {
        this.issueDate = issueDate;
        this.dueDate = dueDate;
        this.finePaid = false;
        this.copyReturned = false;
        this.amount = 0.0;
    }

    public double computeFine() {
        return amount;
    }

    public void printInfo() {
        System.out.printf("Loan: Issued %s | Due %s | Returned %s | Fine Paid: %s\n",
                issueDate, dueDate, returnDate != null ? returnDate : "--", finePaid ? "Yes" : "No");
    }
}
