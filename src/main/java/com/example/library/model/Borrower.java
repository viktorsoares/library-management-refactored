package com.example.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("borrower")
@Getter
@Setter
public class Borrower extends Person {

    private String libraryCardNumber;
    private Double totalFine;

    @OneToMany(mappedBy = "person")
    private List<Loan> loans;

    public Borrower() {
    }

    public Borrower(String name, String address, String phone, String email, String libraryCardNumber) {
        super(name, address, phone, email, "borrower");
        this.libraryCardNumber = libraryCardNumber;
    }

    @Override
    public void printInfo() {
        System.out.printf("Borrower: %s | Card: %s | Email: %s\n",
                getName(),
                libraryCardNumber != null ? libraryCardNumber : "--",
                getEmail() != null ? getEmail() : "--");
    }
}
