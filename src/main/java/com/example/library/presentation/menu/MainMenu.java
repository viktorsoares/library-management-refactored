package com.example.library.presentation.menu;

import com.example.library.domain.model.Person;
import com.example.library.domain.service.LibraryService;
import com.example.library.presentation.state.AdminState;
import com.example.library.presentation.state.ClerkState;
import com.example.library.presentation.state.LibrarianState;
import com.example.library.presentation.state.UserContext;
import com.example.library.presentation.state.UserState;
import com.example.library.infrastructure.util.MessagePrinter;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AdminMenu adminMenu;
    private final ClerkMenu clerkMenu;
    private final LibrarianMenu librarianMenu;
    private final LibraryService libraryService;

    public MainMenu(AdminMenu adminMenu, ClerkMenu clerkMenu,
                    LibrarianMenu librarianMenu, LibraryService libraryService) {
        this.adminMenu = adminMenu;
        this.clerkMenu = clerkMenu;
        this.librarianMenu = librarianMenu;
        this.libraryService = libraryService;
    }

    public void show() {
        boolean running = true;

        while (running) {
            MessagePrinter.header("Welcome to Library Management System");
            System.out.println("""
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
                    MessagePrinter.info("Exiting system...");
                    running = false;
                }
                default -> MessagePrinter.warning("Invalid choice. Try again.");
            }
        }
    }

    private void loginUser() {
        MessagePrinter.prompt("User ID");
        String input = scanner.nextLine().trim();
        if (!input.matches("\\d+")) {
            MessagePrinter.warning("Invalid ID.");
            return;
        }
        Long id = Long.parseLong(input);

        MessagePrinter.prompt("Password");
        String password = scanner.nextLine().trim();

        Optional<Person> opt = libraryService.findPerson(id);
        if (opt.isEmpty()) {
            MessagePrinter.error("User not found.");
            return;
        }

        Person person = opt.get();
        if (!password.equals(person.getPassword())) {
            MessagePrinter.error("Incorrect password.");
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
            MessagePrinter.error("Unauthorized role: " + role);
            return;
        }

        context.setState(state);
        context.execute();
    }

    private void loginAdmin() {
        MessagePrinter.prompt("Admin Password");
        String password = scanner.nextLine().trim();

        if (password.equals("lib")) {
            UserContext context = new UserContext();
            context.setState(new AdminState(adminMenu));
            context.execute();
        } else {
            MessagePrinter.error("Incorrect admin password.");
        }
    }
}
