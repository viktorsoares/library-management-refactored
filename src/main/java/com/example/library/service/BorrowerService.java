package com.example.library.service;

import com.example.library.model.Borrower;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BorrowerService {

    private final List<Borrower> borrowers = new ArrayList<>();
    private long idCounter = 1;

    public Borrower createBorrower(String name, String email) {
        Borrower borrower = new Borrower();
        borrower.setId(idCounter++);
        borrower.setName(name);
        borrower.setEmail(email);
        borrower.setRole("STUDENT"); // default role
        borrowers.add(borrower);
        return borrower;
    }

    public void updateBorrower(Long id, String name, String email) {
        Borrower borrower = borrowers.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        borrower.setName(name);
        borrower.setEmail(email);
    }

    public String getBorrowerInfo(Long id) {
        Borrower borrower = borrowers.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Borrower not found"));
        return "ID: " + borrower.getId() + ", Name: " + borrower.getName() + ", Email: " + borrower.getEmail() + ", Role: " + borrower.getRole();
    }

    public Optional<Borrower> findById(Long id) {
        return borrowers.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    public List<Borrower> listAll() {
        return borrowers;
    }
}
