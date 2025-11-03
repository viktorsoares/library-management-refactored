package com.example.library.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Person {
    private static int IDCounter = 0;
    private int id;
    private String name;
    private String address;
    private int phoneNumber;
    private String password;

    public Person(int id, String name, String address, int phoneNumber) {
        if (id == -1) {
            this.id = ++IDCounter;
        } else {
            this.id = id;
            if (id > IDCounter) IDCounter = id;
        }
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = "1"; // default password
    }

    public int getID() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public int getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }

    public static void setIDCount(int max) { IDCounter = max; }
}
