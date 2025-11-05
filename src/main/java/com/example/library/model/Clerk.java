package com.example.library.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
@DiscriminatorValue("clerk")
public class Clerk extends Staff {

    @Column(name = "DESK_NO")
    private int deskNo;

    public Clerk() {
    }

    public Clerk(String name, String address, String phone, double salary, int deskNo) {
        super(name, address, phone, salary);
        this.deskNo = deskNo;
    }

    @Override
    public void printInfo() {
        System.out.printf("Clerk: %s | Desk: %d\n", getName(), getDeskNo());
    }
}



