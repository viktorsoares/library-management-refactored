package com.example.library.factory;

import com.example.library.model.Clerk;
import com.example.library.model.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.util.Scanner;

@Service
public class ClerkFactory implements PersonFactory {

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

            System.out.println("Enter Desk No: ");
            int deskNo = Integer.parseInt(scanner.nextLine());

            Clerk clerk = new Clerk(name, address, phone, salary, deskNo);

            em.persist(clerk);

            clerk.setPassword(String.valueOf(clerk.getId()));

            System.out.println(" Clerk created successfully.");
            System.out.println("Your ID is: " + clerk.getId());
            System.out.println("Your Password is: " + clerk.getPassword());

            return clerk;
        } catch (Exception e) {
            System.out.println(" Failed to create Clerk: " + e.getMessage());
            return null;
        }
    }


}
