package com.example.library.presentation.menu;

import com.example.library.application.facade.AdminFacade;
import com.example.library.application.factory.ClerkFactory;
import com.example.library.application.factory.LibrarianFactory;
import com.example.library.domain.model.Person;
import com.example.library.infrastructure.util.MessagePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
public class AdminMenu extends MenuTemplate {

    private final AdminFacade adminFacade;
    private final ClerkFactory clerkFactory;
    private final LibrarianFactory librarianFactory;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    @Autowired
    public AdminMenu(AdminFacade adminFacade,
                     ClerkFactory clerkFactory,
                     LibrarianFactory librarianFactory) {
        this.adminFacade = adminFacade;
        this.clerkFactory = clerkFactory;
        this.librarianFactory = librarianFactory;
    }

    @Override
    protected void printHeader() {
        MessagePrinter.header("Welcome to Admin's Portal");
    }

    @Override
    protected void printOptions() {
        System.out.println("""
                1 - Add Clerk
                2 - Add Librarian
                3 - View Issued Books History
                4 - View All Books in Library
                5 - Logout
                ---------------------------------------------
                Enter Choice: """);
    }

    @Override
    protected boolean handleChoice(String choice) {
        switch (choice) {
            case "1" -> {
                Person clerk = clerkFactory.create(scanner, reader);
                if (clerk != null) {
                    MessagePrinter.success(String.format("Clerk created successfully. ID: %d | Password: %s",
                            clerk.getId(), clerk.getPassword()));
                }
            }
            case "2" -> {
                Person librarian = librarianFactory.create(scanner, reader);
                if (librarian != null) {
                    MessagePrinter.success(String.format("Librarian created successfully. ID: %d | Password: %s",
                            librarian.getId(), librarian.getPassword()));
                }
            }
            case "3" -> adminFacade.viewIssuedBooksHistory();
            case "4" -> adminFacade.viewAllBooks();
            case "5" -> {
                MessagePrinter.info("Logging out...");
                return false;
            }
            default -> MessagePrinter.warning("Invalid choice.");
        }
        MessagePrinter.pressAnyKey();
        scanner.nextLine();
        return true;
    }
}
