package com.example.library.web;

import com.example.library.service.LibraryService;
import com.example.library.model.Person;
import java.util.Scanner;

public class TerminalController {
    private LibraryService libraryService;
    private Scanner scanner = new Scanner(System.in);

    public TerminalController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void showLoginMenu() {
        System.out.println("1- Login");
        System.out.println("2- Exit");
        System.out.println("3- Administrative Functions");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Password: ");
                String pass = scanner.nextLine();
                Person p = libraryService.login(id, pass);
                if (p != null) {
                    System.out.println("Login Successful");
                    // redireciona para o menu do usuário
                } else {
                    System.out.println("Wrong ID or Password");
                }
                break;
            case 2:
                System.exit(0);
            case 3:
                System.out.print("Enter Admin Password: ");
                String adminPass = scanner.nextLine();
                if (adminPass.equals("lib")) {
                    System.out.println("Welcome Admin!");
                } else {
                    System.out.println("Wrong Password!");
                }
                break;
        }
    }
}
