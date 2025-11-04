package com.example.library.model;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@Data
public abstract class Staff extends Person {
    protected double salary;

    public Staff() {}

    public Staff(String name, String address, String phone, double salary) {
        super(name, address, phone, "staff");
        this.salary = salary;
    }

    @Override
    public void printInfo() {
        System.out.printf("Staff: %s | Salary: %.2f\n", getName(), getSalary());
    }
}

