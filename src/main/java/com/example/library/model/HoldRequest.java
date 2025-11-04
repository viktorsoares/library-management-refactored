package com.example.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "HOLD_REQUEST")
@Setter
@Getter
public class HoldRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private LocalDate requestDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "borrower_id")
    private Borrower borrower;

    public HoldRequest() {
    }

    public HoldRequest(LocalDate requestDate) {
        this.requestDate = requestDate;
    }

    public void print() {
        System.out.printf("Hold Request placed on: %s for book '%s' by borrower '%s'\n",
                requestDate,
                book != null ? book.getTitle() : "Unknown",
                borrower != null ? borrower.getName() : "Unknown");
    }

}
