package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("LIBRARIAN")
public class Librarian extends Staff {

    @Column(name = "OFFICE_NO")
    private int officeNo;

    public Librarian() {
    }

    public Librarian(String name, String address, String phone, double salary, int officeNo) {
        super(name, address, phone, salary);
        this.officeNo = officeNo;
    }

    public int getOfficeNo() {
        return officeNo;
    }

    @Override
    public void printInfo() {
        System.out.printf("Librarian: %s | Office: %d\n", getName(), getOfficeNo());
    }
}