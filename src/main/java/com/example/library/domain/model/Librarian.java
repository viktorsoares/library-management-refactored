package com.example.library.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("librarian")
public class Librarian extends Staff {

    @Column(name = "OFFICE_NO")
    private Integer officeNo;

    public Librarian() {
    }

    public Librarian(String name, String address, String phone, String email, double salary, int officeNo) {
        super(name, address, phone, email, salary);
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