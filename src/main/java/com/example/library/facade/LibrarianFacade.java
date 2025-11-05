package com.example.library.facade;

import com.example.library.model.Book;
import com.example.library.model.Person;
import com.example.library.service.LibraryService;
import com.example.library.strategy.BookOperationContext;
import com.example.library.strategy.CheckOutBookStrategy;
import com.example.library.strategy.RenewBookStrategy;
import com.example.library.strategy.ReturnBookStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibrarianFacade extends AbstractFacade {

    private final LibraryService service;

    @PersistenceContext
    private EntityManager em;

    public LibrarianFacade(LibraryService service) {
        this.service = service;
    }

    public void addNewBook() {
        String title = readRequired("title");
        String author = readRequired("author");
        String subject = readRequired("subject");
        String publisher = readRequired("publisher");
        String isbn = readRequired("ISBN");
        String edition = readRequired("edition");
        int year = readInt("year");
        double price = readDouble("price");
        int pages = readInt("number of pages");

        service.addBook(title, author, subject, publisher, isbn, edition, year, price, pages);
        System.out.println(" Book added successfully.");
    }

    public void removeBook() {
        Long bookId = readValidId("Book");
        if (bookId == null) return;

        Optional<Book> book = service.findBook(bookId);
        if (book.isEmpty()) {
            System.out.println(" Book not found.");
            return;
        }

        service.deleteBook(book.get());
        System.out.println(" Book removed successfully.");
    }

    public void updateBookInfo() {
        Long bookId = readValidId("Book");
        if (bookId == null) return;

        Optional<Book> optBook = service.findBook(bookId);
        if (optBook.isEmpty()) {
            System.out.println(" Book not found.");
            return;
        }

        Book book = optBook.get();

        String title = readOptional("title");
        if (!title.isEmpty()) book.setTitle(title);

        String author = readOptional("author");
        if (!author.isEmpty()) book.setAuthor(author);

        String subject = readOptional("subject");
        if (!subject.isEmpty()) book.setSubject(subject);

        service.updateBook(book);
        System.out.println(" Book information updated.");
    }

    public void viewClerkInfo() {
        Long clerkId = readValidId("Clerk");
        if (clerkId == null) return;

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
