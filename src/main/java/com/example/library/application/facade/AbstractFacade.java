package com.example.library.application.facade;

import com.example.library.infrastructure.util.MessagePrinter;

import java.util.Scanner;

public abstract class AbstractFacade {
    protected final Scanner scanner = new Scanner(System.in);

    protected Long readValidId(String label) {
        MessagePrinter.prompt(label + " ID");
        String input = scanner.nextLine().trim();
        if (input.matches("\\d+")) {
            return Long.parseLong(input);
        } else {
            MessagePrinter.error("Invalid ID.");
            return null;
        }
    }

    protected String readOptional(String label) {
        MessagePrinter.optionalPrompt(label);
        return scanner.nextLine().trim();
    }

    protected String readRequired(String label) {
        MessagePrinter.prompt(label);
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            MessagePrinter.warning(label + " is required.");
            return readRequired(label);
        }
        return input;
    }

    protected double readDouble(String label) {
        MessagePrinter.prompt(label);
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            MessagePrinter.error("Invalid number.");
            return readDouble(label);
        }
    }

    protected int readInt(String label) {
        MessagePrinter.prompt(label);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            MessagePrinter.error("Invalid number.");
            return readInt(label);
        }
    }
}
