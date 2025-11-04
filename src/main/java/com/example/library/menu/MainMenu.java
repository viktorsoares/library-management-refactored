package com.example.library.menu;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MainMenu {
    private final Scanner scanner = new Scanner(System.in);
    private final AdminMenu adminMenu;
    private final ClerkMenu clerkMenu;

    public MainMenu(AdminMenu adminMenu, ClerkMenu clerkMenu) {
        this.adminMenu = adminMenu;
        this.clerkMenu = clerkMenu;
    }

    public void show() {
        boolean running = true;

        while (running) {
            System.out.println("""
                    --------------------------------------------------------
                    \tWelcome to Library Management System
                    --------------------------------------------------------
                    Following Functionalities are available: 
                    
                    1- Login as Clerk
                    2- Exit
                    3- Administrative Functions
                    -----------------------------------------
                    Enter Choice: """);

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> loginClerk();
                case "2" -> {
                    System.out.println("Exiting... Goodbye!");
                    running = false;
                }
                case "3" -> loginAdmin();
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void loginClerk() {
        System.out.print("Enter Clerk ID: ");
        String id = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        if (id.equals("1") && password.equals("1")) {
            System.out.println("\n Login Successful\n");
            clerkMenu.runMenu();
        } else {
            System.out.println(" Invalid ID or Password.");
        }
    }

    private void loginAdmin() {
        System.out.print("Enter Admin Password: ");
        String password = scanner.nextLine().trim();

        if (password.equals("lib")) {
            System.out.println("\n Access granted to Admin Portal\n");
            adminMenu.runMenu();
        } else {
            System.out.println(" Incorrect admin password.");
        }
    }
}
