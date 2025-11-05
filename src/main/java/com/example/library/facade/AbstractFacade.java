package com.example.library.facade;

import java.util.Scanner;

public abstract class AbstractFacade {
    protected final Scanner scanner = new Scanner(System.in);

    protected Long readValidId(String label) {
        System.out.print("Enter " + label + " ID: ");
        String input = scanner.nextLine().trim();
        if (input.matches("\\d+")) {
            return Long.parseLong(input);
        } else {
            System.out.println(" Invalid ID.");
            return null;
        }
    }

    protected String readOptional(String label) {
        System.out.print("Enter new " + label + " (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    protected String readRequired(String label) {
        System.out.print("Enter " + label + ": ");
        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            System.out.println(" " + label + " is required.");
            return readRequired(label);
        }
        return input;
    }

    protected double readDouble(String label) {
        System.out.print("Enter " + label + ": ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number.");
            return readDouble(label);
        }
    }

    protected int readInt(String label) {
        System.out.print("Enter " + label + ": ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println(" Invalid number.");
            return readInt(label);
        }
    }
}
