package com.example.library.facade;

import com.example.library.model.Book;
import com.example.library.model.Person;
import com.example.library.service.LibraryService;
import com.example.library.strategy.BookOperationContext;
import com.example.library.strategy.CheckOutBookStrategy;
import com.example.library.strategy.ReturnBookStrategy;
import com.example.library.strategy.RenewBookStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class LibrarianFacade {

    private final LibraryService service;

    @PersistenceContext
    private EntityManager em;

    private final Scanner scanner = new Scanner(System.in);

    public LibrarianFacade(LibraryService service) {
        this.service = service;
    }

    public void addNewBook() {
        System.out.print("Enter title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter author: ");
        String author = scanner.nextLine().trim();

        System.out.print("Enter subject: ");
        String subject = scanner.nextLine().trim();

        System.out.print("Enter publisher: ");
        String publisher = scanner.nextLine().trim();

        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();

        System.out.print("Enter edition: ");
        String edition = scanner.nextLine().trim();

        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Enter price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        System.out.print("Enter number of pages: ");
        int pages = Integer.parseInt(scanner.nextLine().trim());

        service.addBook(title, author, subject, publisher, isbn, edition, year, price, pages);
        System.out.println(" Book added successfully.");
    }

    public void removeBook() {
        System.out.print("Enter Book ID to remove: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        Optional<Book> book = service.findBook(bookId);
        if (book.isEmpty()) {
            System.out.println(" Book not found.");
            return;
        }

        service.deleteBook(book.get());
        System.out.println(" Book removed successfully.");
    }

    public void updateBookInfo() {
        System.out.print("Enter Book ID to update: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        Optional<Book> optBook = service.findBook(bookId);
        if (optBook.isEmpty()) {
            System.out.println(" Book not found.");
            return;
        }

        Book book = optBook.get();

        System.out.print("Enter new title (leave blank to keep current): ");
        String title = scanner.nextLine().trim();
        if (!title.isEmpty()) book.setTitle(title);

        System.out.print("Enter new author (leave blank to keep current): ");
        String author = scanner.nextLine().trim();
        if (!author.isEmpty()) book.setAuthor(author);

        System.out.print("Enter new subject (leave blank to keep current): ");
        String subject = scanner.nextLine().trim();
        if (!subject.isEmpty()) book.setSubject(subject);

        service.updateBook(book);
        System.out.println(" Book information updated.");
    }

    public void viewClerkInfo() {
        System.out.print("Enter Clerk ID: ");
        Long clerkId = service.validateLongInput(scanner.nextLine().trim());

        Optional<Person> person = service.findPerson(clerkId);
        if (person.isEmpty() || !"staff".equalsIgnoreCase(person.get().getRole())) {
            System.out.println(" Clerk not found.");
        } else {
            System.out.println("\n--- Clerk Information ---");
            person.get().printInfo();
        }
    }

    public void checkOutBook() {
        BookOperationContext context = new BookOperationContext();
        context.setStrategy(new CheckOutBookStrategy(service, scanner));
        context.execute();
    }

    public void checkInBook() {
        BookOperationContext context = new BookOperationContext();
        context.setStrategy(new ReturnBookStrategy(service, scanner));
        context.execute();
    }

    public void renewBook() {
        BookOperationContext context = new BookOperationContext();
        context.setStrategy(new RenewBookStrategy(service, scanner));
        context.execute();
    }
}
