package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Borrower {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String role; // e.g. "STUDENT", "TEACHER", "STAFF", "ADMIN"

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<Loan> borrowedLoans = new ArrayList<>();
}
