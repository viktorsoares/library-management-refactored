package com.example.library.service;

import com.example.library.model.Librarian;
import com.example.library.repository.LibrarianRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final LibrarianRepository repository;

    public AuthService(LibrarianRepository repository) {
        this.repository = repository;
    }

    public Optional<Librarian> login(String name, String password) {
        return repository.findById(1L);
    }
}
