package com.example.library.menu;

import com.example.library.facade.AdminFacade;
import com.example.library.factory.ClerkFactory;
import com.example.library.factory.LibrarianFactory;
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
        System.out.println("""
                --------------------------------------------------------
                \tWelcome to Admin's Portal
                --------------------------------------------------------""");
    }

    @Override
    protected void printOptions() {
        System.out.println("""
                1- Add Clerk
                2- Add Librarian
                3- View Issued Books History
                4- View All Books in Library
                5- Logout
                ---------------------------------------------
                Enter Choice: """);
    }

    @Override
    protected boolean handleChoice(String choice) {
        switch (choice) {
            case "1" -> clerkFactory.create(scanner, reader);
            case "2" -> librarianFactory.create(scanner, reader);
            case "3" -> adminFacade.viewIssuedBooksHistory();
            case "4" -> adminFacade.viewAllBooks();
            case "5" -> {
                System.out.println("🔒 Logging out...");
                return false;
            }
            default -> System.out.println("Invalid choice.");
        }
        System.out.print("\nPress any key to continue...");
        scanner.nextLine();
        return true;
    }
}
