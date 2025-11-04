package com.example.library.factory;

import com.example.library.model.Book;
import com.example.library.model.Person;
import com.example.library.service.LibraryService;

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
}
