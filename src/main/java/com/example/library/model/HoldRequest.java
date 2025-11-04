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
