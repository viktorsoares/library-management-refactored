package com.example.library.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("staff")
public class Staff extends Person {
    private double salary;

    public Staff() {
    }

    public Staff(String name, String address, String phone, double salary) {
        super(name, address, phone, "staff");
        this.salary = salary;
    }

    @Override
    public void printInfo() {
        System.out.printf("Staff: %s | Salary: %.2f\n", getName(), salary);
    }
}