package com.example.library.util.scanner;

import com.example.library.facade.LibraryFacade;
import com.example.library.model.Book;
import com.example.library.model.HoldRequest;

import java.util.List;
import java.util.Scanner;

public class TerminalMenu {

    private final Scanner scanner;
    private final LibraryFacade libraryFacade;

    public TerminalMenu(LibraryFacade libraryFacade) {
        this.scanner = new Scanner(System.in);
        this.libraryFacade = libraryFacade;
    }

    public void start() {
        boolean running = true;

        while (running) {
            clearScreen();
            printMenu();

            System.out.print("Enter your choice: ");
            int choice = readInt();

            switch (choice) {
                case 1 -> searchBooks();
                case 2 -> placeHoldRequest();
                case 3 -> viewBorrowerInfo();
                case 4 -> calculateFine();
                case 5 -> viewHoldQueue();
                case 6 -> issueBook();
                case 7 -> returnBook();
                case 8 -> renewBook();
                case 9 -> addBorrower();
                case 10 -> updateBorrower();
                case 11 -> addBook();
                case 12 -> removeBook();
                case 13 -> updateBook();
                case 14 -> viewStaffInfo();
                case 0 -> {
                    System.out.println("Exiting system...");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }

            if (running) {
                System.out.println("\nPress ENTER to continue...");
                scanner.nextLine();
            }
        }
    }

    private void clearScreen() {
        for (int i = 0; i < 20; i++) System.out.println();
    }

    private void printMenu() {
        System.out.println("--------------------------------------------------------");
        System.out.println("Library Management System - Terminal Menu");
        System.out.println("--------------------------------------------------------");
        System.out.println("1 - Search for books");
        System.out.println("2 - Place a hold request");
        System.out.println("3 - View borrower information");
        System.out.println("4 - Calculate borrower fine");
        System.out.println("5 - View hold request queue");
        System.out.println("6 - Issue a book");
        System.out.println("7 - Return a book");
        System.out.println("8 - Renew a book");
        System.out.println("9 - Add a new borrower");
        System.out.println("10 - Update borrower information");
        System.out.println("11 - Add a new book");
        System.out.println("12 - Remove a book");
        System.out.println("13 - Update book information");
        System.out.println("14 - View staff information");
        System.out.println("0 - Exit");
        System.out.println("--------------------------------------------------------");
    }

    private int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // ======================= METHODS =======================

    private void searchBooks() {
        List<Book> books = libraryFacade.listBooks();
        if (books.isEmpty()) System.out.println("No books found.");
        else books.forEach(b -> System.out.println(b.toString()));
    }

    private void placeHoldRequest() {
        try {
            Long bookId = Long.parseLong(readLine("Enter Book ID: "));
            Long borrowerId = Long.parseLong(readLine("Enter Borrower ID: "));
            HoldRequest hold = libraryFacade.placeHold(bookId, borrowerId);
            System.out.println("Hold placed: " + hold.toString());
        } catch (Exception e) {
            System.out.println("Error placing hold: " + e.getMessage());
        }
    }

    private void viewBorrowerInfo() {
        try {
            Long id = Long.parseLong(readLine("Enter Borrower ID: "));
            System.out.println(libraryFacade.getBorrowerInfo(id));
        } catch (Exception e) {
            System.out.println("Error fetching borrower info: " + e.getMessage());
        }
    }

    private void calculateFine() {
        try {
            Long id = Long.parseLong(readLine("Enter Borrower ID: "));
            double fine = libraryFacade.calculateFine(id);
            System.out.println("Total fine: $" + fine);
        } catch (Exception e) {
            System.out.println("Error calculating fine: " + e.getMessage());
        }
    }

    private void viewHoldQueue() {
        try {
            Long bookId = Long.parseLong(readLine("Enter Book ID: "));
            Long receiverId = Long.parseLong(readLine("Receiver Id Book: "));
            List<HoldRequest> holds = libraryFacade.listHoldRequests(bookId, receiverId);
            if (holds.isEmpty()) System.out.println("No holds found.");
            else holds.forEach(h -> System.out.println(h.toString()));
        } catch (Exception e) {
            System.out.println("Error fetching hold queue: " + e.getMessage());
        }
    }

    private void issueBook() {
        try {
            Long bookId = Long.parseLong(readLine("Enter Book ID: "));
            Long borrowerId = Long.parseLong(readLine("Enter Borrower ID: "));
            Long staffId = Long.parseLong(readLine("Enter Staff ID: "));
            String result = libraryFacade.issueBook(bookId, borrowerId, staffId);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    private void returnBook() {
        try {
            Long loanId = Long.parseLong(readLine("Enter Loan ID: "));
            Long receiverId = Long.parseLong(readLine("Enter Receiver Staff ID: "));
            String result = libraryFacade.returnBook(loanId, receiverId);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    private void renewBook() {
        try {
            Long loanId = Long.parseLong(readLine("Enter Loan ID: "));
            libraryFacade.renewLoan(loanId);
            System.out.println("Loan renewed successfully.");
        } catch (Exception e) {
            System.out.println("Error renewing loan: " + e.getMessage());
        }
    }

    private void addBorrower() {
        try {
            String name = readLine("Enter name: ");
            String email = readLine("Enter email: ");
            libraryFacade.createBorrower(name, email);
            System.out.println("Borrower created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating borrower: " + e.getMessage());
        }
    }

    private void updateBorrower() {
        try {
            Long id = Long.parseLong(readLine("Enter Borrower ID: "));
            String name = readLine("Enter new name: ");
            String email = readLine("Enter new email: ");
            libraryFacade.updateBorrower(id, name, email);
            System.out.println("Borrower updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating borrower: " + e.getMessage());
        }
    }

    private void addBook() {
        try {
            String title = readLine("Enter title: ");
            String subject = readLine("Enter subject: ");
            String author = readLine("Enter author: ");
            libraryFacade.createBook(title, subject, author);
            System.out.println("Book created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating book: " + e.getMessage());
        }
    }

    private void removeBook() {
        try {
            Long id = Long.parseLong(readLine("Enter Book ID: "));
            libraryFacade.removeBook(id);
            System.out.println("Book removed successfully.");
        } catch (Exception e) {
            System.out.println("Error removing book: " + e.getMessage());
        }
    }

    private void updateBook() {
        try {
            Long id = Long.parseLong(readLine("Enter Book ID: "));
            String title = readLine("Enter new title: ");
            String subject = readLine("Enter new subject: ");
            String author = readLine("Enter new author: ");
            libraryFacade.updateBook(id, title, subject, author);
            System.out.println("Book updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    private void viewStaffInfo() {
        try {
            Long id = Long.parseLong(readLine("Enter Staff ID: "));
            System.out.println(libraryFacade.getStaffInfo(id));
        } catch (Exception e) {
            System.out.println("Error fetching staff info: " + e.getMessage());
        }
    }
}
