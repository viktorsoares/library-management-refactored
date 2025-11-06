package com.example.library.application.factory;

import com.example.library.domain.model.Book;
import com.example.library.domain.model.Borrower;
import com.example.library.domain.model.Clerk;
import com.example.library.domain.model.Librarian;
import com.example.library.domain.model.Person;
import com.example.library.domain.service.LibraryService;

public class EntityFactory {
    private final LibraryService service;

    public EntityFactory(LibraryService service) {
        this.service = service;
    }

    public Book createAndSaveBook(String title, String author, String subject, String publisher,
                                  String ISBN, String edition, int year, double price, int pages) {
        return service.addBook(title, author, subject, publisher, ISBN, edition, year, price, pages);
    }

    public Person createAndSavePerson(String name, String address, String phone, String role, String password) {
        return service.addPerson(name, address, phone, role, password);
    }

    public Borrower createAndSaveBorrower(String name, String email, String phone) {
        Borrower borrower = new Borrower();
        borrower.setName(name);
        borrower.setEmail(email);
        borrower.setPhone(phone);
        borrower.setTotalFine(0.0);
        return service.saveBorrower(borrower);
    }

    public Clerk createAndSaveClerk(String name, String address, String phone, String email, Double salary, int deskNo) {
        Clerk clerk = new Clerk(name, address, phone, email, salary, deskNo);
        return service.saveClerk(clerk);
    }

    public Librarian createAndSaveLibrarian(String name, String address, String phone, String email, Double salary, int officeNo) {
        Librarian librarian = new Librarian(name, address, phone, email, salary, officeNo);
        return service.saveLibrarian(librarian);
    }
}
