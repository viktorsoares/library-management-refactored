package com.example.library.menu;

import com.example.library.facade.LibraryFacade;
import com.example.library.model.Person;
import com.example.library.state.AdminState;
import com.example.library.state.ClerkState;
import com.example.library.state.LibrarianState;
import com.example.library.state.UserContext;
import com.example.library.state.UserState;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AdminMenu adminMenu;
    private final ClerkMenu clerkMenu;
    private final LibrarianMenu librarianMenu;
    private final LibraryFacade libraryFacade;

    public MainMenu(AdminMenu adminMenu, ClerkMenu clerkMenu,
                    LibrarianMenu librarianMenu, LibraryFacade libraryFacade) {
        this.adminMenu = adminMenu;
        this.clerkMenu = clerkMenu;
        this.librarianMenu = librarianMenu;
        this.libraryFacade = libraryFacade;
    }

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println("""
                    Welcome to Library Management System
                    --------------------------------------------------------
                    Following Functionalities are available:
                    
                    1 - Login
                    2 - Administrative Functions
                    3 - Exit
                    --------------------------------------------------------
                    Enter Choice:""");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> loginUser();
                case "2" -> loginAdmin();
                case "3" -> {
                    System.out.println("🔒 Exiting system...");
                    running = false;
                }
                default -> System.out.println(" Invalid choice. Try again.");
            }
        }
    }

    private void loginUser() {
        System.out.print("Enter User ID: ");
        String input = scanner.nextLine().trim();
        if (!input.matches("\\d+")) {
            System.out.println(" Invalid ID.");
            return;
        }
        Long id = Long.parseLong(input);

        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        Optional<Person> opt = libraryFacade.findPerson(id);
        if (opt.isEmpty()) {
            System.out.println(" User not found.");
            return;
        }

        Person person = opt.get();
        if (!password.equals(person.getPassword())) {
            System.out.println(" Incorrect password.");
            return;
        }

        String role = person.getRole().toLowerCase();
        UserContext context = new UserContext();

        UserState state = switch (role) {
            case "clerk" -> new ClerkState(clerkMenu);
            case "librarian" -> new LibrarianState(librarianMenu);
            default -> null;
        };

        if (state == null) {
            System.out.println(" Unauthorized role: " + role);
            return;
        }

        context.setState(state);
        context.execute();
    }

    private void loginAdmin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();

        if (password.equals("lib")) {
            UserContext context = new UserContext();
            context.setState(new AdminState(adminMenu));
            context.execute();
        } else {
            System.out.println(" Incorrect admin password.");
        }
    }
}
