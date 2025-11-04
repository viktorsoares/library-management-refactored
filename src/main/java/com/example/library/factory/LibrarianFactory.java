package com.example.library.factory;

import com.example.library.model.Librarian;
import com.example.library.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
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

            System.out.println("Enter Phone Number: ");
            String phone = scanner.nextLine();

            System.out.println("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            System.out.println("Enter Office No: ");
            int officeNo = Integer.parseInt(scanner.nextLine());

            Librarian librarian = new Librarian(name, address, phone, salary, officeNo);

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
}
