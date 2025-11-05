package com.example.library.util;

public class MessagePrinter {

    public static void success(String message) {
        System.out.println("Success: " + message);
    }

    public static void error(String message) {
        System.out.println("Error: " + message);
    }

    public static void info(String message) {
        System.out.println("Info: " + message);
    }

    public static void warning(String message) {
        System.out.println("Warning: " + message);
    }

    public static void prompt(String label) {
        System.out.print("Enter " + label + ": ");
    }

    public static void optionalPrompt(String label) {
        System.out.print("Enter new " + label + " (leave blank to keep current): ");
    }

    public static void pressAnyKey() {
        System.out.print("\nPress any key to continue...");
    }

    public static void separator(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    public static void header(String title) {
        System.out.println("--------------------------------------------------------");
        System.out.println("\t" + title);
        System.out.println("--------------------------------------------------------");
    }
}
