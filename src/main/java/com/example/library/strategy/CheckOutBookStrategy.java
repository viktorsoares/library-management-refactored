package com.example.library.strategy;

import com.example.library.service.LibraryService;

import java.util.Scanner;

public class CheckOutBookStrategy implements BookOperationStrategy {
    private final LibraryService service;
    private final Scanner scanner;

    public CheckOutBookStrategy(LibraryService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Enter Book ID: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        System.out.print("Enter Borrower ID: ");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        boolean success = service.loanBook(bookId, borrowerId);

        if (success) {
            System.out.println(" Book issued successfully.");
        } else {
            System.out.println(" Could not issue book. Check availability or hold queue.");
        }
    }
}
