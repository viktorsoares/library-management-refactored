package com.example.library.factory;

import com.example.library.model.*;

public class PersonFactory {

    public static Person createPerson(String type, int id, String name, String address, int phoneNumber, double salary, int deskOrOffice) {
        switch (type.toLowerCase()) {
            case "clerk":
                return new Clerk(id, name, address, phoneNumber, salary, deskOrOffice);
            case "librarian":
                return new Librarian(id, name, address, phoneNumber, salary, deskOrOffice);
            case "borrower":
                return new Borrower(id, name, address, phoneNumber);
            default:
                throw new IllegalArgumentException("Invalid person type: " + type);
        }
    }
}
