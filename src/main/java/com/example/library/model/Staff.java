package com.example.library.model;

public abstract class Staff extends Person {
    private double salary;

    public Staff(int id, String name, String address, int phoneNumber, double salary) {
        super(id, name, address, phoneNumber);
        this.salary = salary;
    }

    public double getSalary() { return salary; }
}
