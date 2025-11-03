package com.example.library.factory;

import com.example.library.model.*;

public class EntityFactory {

    public static Person createPerson(String type, int id, String name, String address, int phone, double salaryOrZero, int deskOrOfficeNo) {
        switch (type.toLowerCase()) {
            case "clerk":
                return new Clerk(id, name, address, phone, salaryOrZero, deskOrOfficeNo);
            case "librarian":
                return Librarian.addLibrarian(new Librarian(id, name, address, phone, salaryOrZero, deskOrOfficeNo)) ? Librarian.getInstance() : null;
            case "borrower":
                return new Borrower(id, name, address, phone);
            default:
                throw new IllegalArgumentException("Invalid person type");
        }
    }

    public static Book createBook(int id, String title, String subject, String author, boolean issued) {
        return new Book(id, title, subject, author, issued);
    }
}
