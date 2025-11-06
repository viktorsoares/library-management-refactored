package com.example.library.factory;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.HoldRequest;
import com.example.library.model.Librarian;
import com.example.library.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class LibrarianFactory implements PersonFactory {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Person create(Scanner scanner, BufferedReader reader) {
        try {
            System.out.println("Enter Name: ");
            String name = reader.readLine();

            System.out.println("Enter Address: ");
            String address = reader.readLine();

            System.out.println("Enter Email: ");
            String email = reader.readLine();

            System.out.println("Enter Phone Number: ");
            String phone = scanner.nextLine();

            System.out.println("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter Office No: ");
            int officeNo = Integer.parseInt(scanner.nextLine());

            Librarian librarian = new Librarian(name, address, phone, email, salary, officeNo);

            em.persist(librarian);

            librarian.setPassword(String.valueOf(librarian.getId()));

            System.out.println(" Librarian created successfully.");
            System.out.println("Your ID is: " + librarian.getId());
            System.out.println("Your Password is: " + librarian.getPassword());

            return librarian;
        } catch (Exception e) {
            System.out.println(" Failed to create Librarian: " + e.getMessage());
            return null;
        }
    }

    public void removeFirstHoldRequest(Book book) {
        List<HoldRequest> queue = book.getHoldRequests();
        if (!queue.isEmpty()) {
            HoldRequest first = queue.get(0);
            em.remove(first);
            queue.remove(0);
            em.merge(book);
        }
    }

    public boolean hasExistingHold(Book book, Borrower borrower) {
        return book.getHoldRequests().stream()
                .anyMatch(hr -> hr.getBorrower().equals(borrower));
    }

    public void expireOldHoldRequests(Book book, int expiryDays) {
        LocalDate today = LocalDate.now();
        List<HoldRequest> expired = book.getHoldRequests().stream()
                .filter(hr -> hr.getRequestDate().plusDays(expiryDays).isBefore(today))
                .toList();

        for (HoldRequest hr : expired) {
            em.remove(hr);
            book.getHoldRequests().remove(hr);
        }

        em.merge(book);
    }

    @Transactional
    public boolean addLibrarian(Librarian librarian) {
        List<Librarian> existing = em.createQuery("""
                SELECT l FROM Librarian l
                """, Librarian.class).getResultList();

        if (existing.isEmpty()) {
            em.persist(librarian);
            return true;
        } else {
            System.out.println(" A librarian already exists. Cannot create another.");
            return false;
        }
    }


}