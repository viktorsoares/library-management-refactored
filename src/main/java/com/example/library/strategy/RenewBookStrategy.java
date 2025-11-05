package com.example.library.strategy;

import com.example.library.service.LibraryService;

import java.util.Scanner;

public class RenewBookStrategy implements BookOperationStrategy {
    private final LibraryService service;
    private final Scanner scanner;

    public RenewBookStrategy(LibraryService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter Book ID to renew: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        boolean success = service.renewBook(bookId, 14);

        if (success) {
            System.out.println(" Book renewed successfully.");
        } else {
            System.out.println(" Book not found or not currently borrowed.");
        }
    }
}
