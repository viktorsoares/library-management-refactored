package com.example.library.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("clerk")
public class Clerk extends Staff {

    @Column(name = "DESK_NO")
    private Integer deskNo;

    public Clerk() {
    }

    public Clerk(String name, String address, String phone, String email, Double salary, int deskNo) {
        super(name, address, phone, email, salary);
        this.deskNo = deskNo;
    }

    public int getDeskNo() {
        return deskNo;
    }

    @Override
    public void printInfo() {
        System.out.printf("Clerk: %s | Desk: %d\n", getName(), getDeskNo());
    }
}
