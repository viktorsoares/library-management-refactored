package com.example.library.util.scanner;

import com.example.library.service.AuthService;
import com.example.library.service.LibraryService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleMenu {

    private final AuthService authService;
    private final LibraryService libraryService;

    public ConsoleMenu(AuthService authService, LibraryService libraryService) {
        this.authService = authService;
        this.libraryService = libraryService;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("--------------------------------------------------------");
            System.out.println("Welcome to Library Management System");
            System.out.println("--------------------------------------------------------");
            System.out.println("Following Functionalities are available: \n");
            System.out.println("1- Login");
            System.out.println("2- Exit");
            System.out.println("3- Administrative Functions");
            System.out.println("-----------------------------------------");
            System.out.print("Enter Choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login(scanner);
                    break;
                case "2":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                case "3":
                    System.out.println("Accessing Admin Functions...");
                    if (!authService.isAdminLogged()) {
                        System.out.println("Admin login required!");
                        login(scanner);
                    }
                    libraryService.adminMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid Choice! Try again.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.print("Enter ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        if (authService.login(id, password)) {
            System.out.println("Login Successful!");
        } else {
            System.out.println("Login Failed! Wrong ID or Password.");
        }
    }
}
