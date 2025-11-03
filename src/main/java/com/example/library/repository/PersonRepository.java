package com.example.library.repository;

import com.example.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<com.example.library.model.Person> findByEmail(String email);
}

