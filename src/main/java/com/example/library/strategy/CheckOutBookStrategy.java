package com.example.library.strategy;

import com.example.library.service.LibraryService;
import com.example.library.util.MessagePrinter;

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
        MessagePrinter.prompt("Book ID");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        MessagePrinter.prompt("Borrower ID");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        boolean success = service.loanBook(bookId, borrowerId);

        if (success) {
            MessagePrinter.success("Book issued successfully.");
        } else {
            MessagePrinter.error("Could not issue book. Check availability or hold queue.");
        }
    }
}
