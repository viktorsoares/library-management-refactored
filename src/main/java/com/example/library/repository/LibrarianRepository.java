package com.example.library.repository;

import com.example.library.model.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    //Optional<Librarian> findByUsernameAndPassword(String name, String password);
}
