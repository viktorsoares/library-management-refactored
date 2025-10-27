package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "loans")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Loan {
    public enum State { CREATED, BORROWED, RETURNED, OVERDUE }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Borrower borrower;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne
    private Borrower receiver; // staff who received return

    private Instant issuedDate;
    private Instant returnedDate;

    private boolean finePaid = false;

    @Enumerated(EnumType.STRING)
    private State state = State.CREATED;

    // business helper (simple)
    public void payFine() {
        this.finePaid = true;
    }
}
