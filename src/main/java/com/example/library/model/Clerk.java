package com.example.library.model;

public class Clerk extends Staff {
    public int deskNo;

    public Clerk(int id, String name, String address, int phone, double salary, int deskNo) {
        super(id, name, address, phone, salary);
        this.deskNo = deskNo;
    }
}
