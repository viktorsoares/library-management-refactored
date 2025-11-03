package com.example.library.service;

import com.example.library.model.Librarian;
import com.example.library.model.Person;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private List<Person> persons;
    private Librarian librarian;

    public LibraryService() {
        persons = new ArrayList<>();
    }

    public void addPerson(Person p) {
        persons.add(p);
        if (p instanceof Librarian) librarian = (Librarian) p;
    }

    public Person login(int id, String password) {
        for (Person p : persons) {
            if (p.getId() == id && p.getPassword().equals(password)) return p;
        }
        return null;
    }

    public Librarian getLibrarian() {
        return librarian;
    }

    public List<Person> getPersons() {
        return persons;
    }
}
