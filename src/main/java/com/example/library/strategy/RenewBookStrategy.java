package com.example.library.strategy;

import com.example.library.service.LibraryService;
import com.example.library.util.MessagePrinter;

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
        MessagePrinter.prompt("Book ID to renew");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        boolean success = service.renewBook(bookId, 14);

        if (success) {
            MessagePrinter.success("Book renewed successfully.");
        } else {
            MessagePrinter.error("Book not found or not currently borrowed.");
        }
    }
}
