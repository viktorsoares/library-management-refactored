package com.example.library.web;

import com.example.library.model.*;
import com.example.library.factory.EntityFactory;
import com.example.library.facade.LibraryFacade;
import com.example.library.service.listener.EmailNotifier;

import java.util.ArrayList;
import java.util.Scanner;

public class LibraryController {

    private final Scanner scanner = new Scanner(System.in);
    private final ArrayList<Book> books = new ArrayList<>();
    private final ArrayList<Person> persons = new ArrayList<>();
    private final LibraryFacade facade;

    public LibraryController() {
        this.facade = new LibraryFacade(books, persons);
        this.facade.addListener(new EmailNotifier()); // Adicionando listener de eventos
    }

    public void start() {
        System.out.println("===== Welcome to Library System =====");

        boolean exit = false;
        while (!exit) {
            System.out.println("\n1. Create Person");
            System.out.println("2. Create Book");
            System.out.println("3. Issue Book");
            System.out.println("4. View All Books");
            System.out.println("5. Search Book");
            System.out.println("6. View Borrower Loans");
            System.out.println("7. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            switch (choice) {
                case 1 -> createPersonMenu();
                case 2 -> createBookMenu();
                case 3 -> issueBookMenu();
                case 4 -> viewAllBooks();
                case 5 -> searchBookMenu();
                case 6 -> viewBorrowerLoansMenu();
                case 7 -> exit = true;
                default -> System.out.println("Invalid option!");
            }
        }

        System.out.println("Exiting Library System...");
    }

    private void createPersonMenu() {
        System.out.println("\nEnter type (clerk/librarian/borrower): ");
        String type = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Address: ");
        String address = scanner.nextLine();

        System.out.println("Enter Phone: ");
        int phone = scanner.nextInt();
        scanner.nextLine();

        double salary = 0;
        int deskOrOffice = 0;

        if (type.equals("clerk") || type.equals("librarian")) {
            System.out.println("Enter Salary: ");
            salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.println("Enter Desk/Office No: ");
            deskOrOffice = scanner.nextInt();
            scanner.nextLine();
        }

        Person person = EntityFactory.createPerson(type, -1, name, address, phone, salary, deskOrOffice);
        if (person != null) {
            persons.add(person);
            System.out.println(type + " created successfully! ID: " + person.getID());
        }
    }

    private void createBookMenu() {
        System.out.println("\nEnter Book Title: ");
        String title = scanner.nextLine();

        System.out.println("Enter Book Author: ");
        String author = scanner.nextLine();

        System.out.println("Enter Book Subject: ");
        String subject = scanner.nextLine();

        Book book = EntityFactory.createBook(-1, title, subject, author, false);
        books.add(book);
        System.out.println("Book created successfully! Title: " + book.getTitle());
    }

    private void issueBookMenu() {
        System.out.println("\nEnter Borrower ID: ");
        int borrowerId = scanner.nextInt();
        scanner.nextLine();

        Borrower borrower = null;
        for (Person p : persons) {
            if (p instanceof Borrower && p.getID() == borrowerId) {
                borrower = (Borrower) p;
                break;
            }
        }
        if (borrower == null) {
            System.out.println("Borrower not found!");
            return;
        }

        System.out.println("Enter Book ID to issue: ");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        Book book = null;
        for (Book b : books) {
            if (b.getID() == bookId) {
                book = b;
                break;
            }
        }
        if (book == null) {
            System.out.println("Book not found!");
            return;
        }

        facade.issueBook(borrower, book);
    }

    private void viewAllBooks() {
        if (books.isEmpty()) {
            System.out.println("\nNo books in library.");
            return;
        }
        System.out.println("\nID\tTitle\tAuthor\tSubject\tIssued");
        for (Book b : books) {
            System.out.println(b.getID() + "\t" + b.getTitle() + "\t" + b.getAuthor() + "\t" + b.getSubject() + "\t" + b.getIssuedStatus());
        }
    }

    private void searchBookMenu() {
        System.out.println("\nSearch by (title/author/subject): ");
        String searchBy = scanner.nextLine().trim().toLowerCase();

        System.out.println("Enter search value: ");
        String value = scanner.nextLine();

        boolean found = false;
        for (Book b : books) {
            if ((searchBy.equals("title") && b.getTitle().equalsIgnoreCase(value)) ||
                    (searchBy.equals("author") && b.getAuthor().equalsIgnoreCase(value)) ||
                    (searchBy.equals("subject") && b.getSubject().equalsIgnoreCase(value))) {
                System.out.println("Found: ID=" + b.getID() + " Title=" + b.getTitle());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found for your query.");
        }
    }

    private void viewBorrowerLoansMenu() {
        System.out.println("\nEnter Borrower ID: ");
        int borrowerId = scanner.nextInt();
        scanner.nextLine();

        Borrower borrower = null;
        for (Person p : persons) {
            if (p instanceof Borrower && p.getID() == borrowerId) {
                borrower = (Borrower) p;
                break;
            }
        }
        if (borrower == null) {
            System.out.println("Borrower not found!");
            return;
        }

        if (borrower.getBorrowedBooks().isEmpty()) {
            System.out.println("No loans found for this borrower.");
            return;
        }

        System.out.println("\nID\tBook Title\tIssued Date\tReturned");
        for (Loan loan : borrower.getBorrowedBooks()) {
            System.out.println(loan.getBook().getID() + "\t" + loan.getBook().getTitle() +
                    "\t" + loan.getIssuedDate() + "\t" + (loan.getReturnDate() != null ? loan.getReturnDate() : "--"));
        }
    }
}
