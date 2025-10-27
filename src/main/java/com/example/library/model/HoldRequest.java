package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "hold_requests")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HoldRequest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Borrower borrower;

    @ManyToOne(optional = false)
    private Book book;

    private Instant requestDate = Instant.now();
}
