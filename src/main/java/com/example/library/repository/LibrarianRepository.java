package com.example.library.repository;

import com.example.library.model.Librarian;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
}
