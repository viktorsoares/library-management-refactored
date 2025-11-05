package com.example.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;

    private String name;
    private String password;
    private String address;
    private String phone;
    private String email;

    @OneToMany(mappedBy = "borrower", fetch = FetchType.EAGER)
    private List<Loan> loans;

    @Column(name = "role", insertable = false, updatable = false)
    private String role;


    public Person() {
    }

    public Person(String name, String address, String phone, String role) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.password = "";
        this.role = role;
    }

    public void printInfo() {
        System.out.printf("Person: %s | ID: %d\n", name, id);
    }

}