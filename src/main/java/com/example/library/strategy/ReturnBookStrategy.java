package com.example.library.strategy;

import com.example.library.service.LibraryService;
import com.example.library.util.MessagePrinter;

import java.util.Scanner;

public class ReturnBookStrategy implements BookOperationStrategy {
    private final LibraryService service;
    private final Scanner scanner;

    public ReturnBookStrategy(LibraryService service, Scanner scanner) {
        this.service = service;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        MessagePrinter.prompt("Book ID to check in");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        boolean success = service.returnBook(bookId);

        if (success) {
            MessagePrinter.success("Book returned successfully.");
        } else {
            MessagePrinter.error("Could not return book. Check if it is currently borrowed.");
        }
    }
}