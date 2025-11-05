package com.example.library.facade;

import com.example.library.model.*;
import com.example.library.service.LibraryService;
import com.example.library.strategy.BookOperationContext;
import com.example.library.strategy.CheckOutBookStrategy;
import com.example.library.strategy.ReturnBookStrategy;
import com.example.library.strategy.RenewBookStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class ClerkFacade {
    @PersistenceContext
    private EntityManager em;

    private final LibraryService service;
    private final Scanner scanner = new Scanner(System.in);

    public ClerkFacade(LibraryService service) {
        this.service = service;
    }

    public void searchBook() {
        System.out.println("Enter search keyword (title or author): ");
        String keyword = scanner.nextLine();

        List<Book> books = em.createQuery("""
                        SELECT b FROM Book b
                        WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
                           OR LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))
                        """, Book.class)
                .setParameter("keyword", keyword)
                .getResultList();

        if (books.isEmpty()) {
            System.out.println("No books found matching: " + keyword);
        } else {
            System.out.println("\n--- Search Results ---");
            books.forEach(Book::printInfo);
        }
    }

    public void placeHold() {
        System.out.print("Enter Book ID to place on hold: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        System.out.print("Enter Borrower ID: ");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        Book book = em.find(Book.class, bookId);
        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (book == null) {
            System.out.println(" Book not found.");
            return;
        }

        if (borrower == null) {
            System.out.println(" Borrower not found.");
            return;
        }

        service.expireOldHoldRequests(book, 3);

        if (service.hasExistingHold(book, borrower)) {
            System.out.println(" Borrower already has a hold request for this book.");
            return;
        }

        HoldRequest hold = new HoldRequest();
        hold.setBook(book);
        hold.setBorrower(borrower);
        hold.setRequestDate(LocalDate.now());

        em.persist(hold);

        System.out.printf(" Hold placed successfully for book '%s' by borrower '%s'.\n", book.getTitle(), borrower.getName());
    }

    public void viewBorrowerInfo() {
        System.out.print("Enter Borrower ID: ");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (borrower == null) {
            System.out.println(" Borrower not found.");
        } else {
            System.out.println("\n--- Borrower Information ---");
            borrower.printInfo();
        }
    }

    public void viewBorrowerFine() {
        System.out.print("Enter Borrower ID: ");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (borrower == null) {
            System.out.println(" Borrower not found.");
        } else {
            System.out.printf(" Total Fine for %s: R$ %.2f\n", borrower.getName(), borrower.getTotalFine());
        }
    }

    public void viewHoldQueue() {
        System.out.print("Enter Book ID: ");
        Long bookId = service.validateLongInput(scanner.nextLine().trim());

        Book book = em.find(Book.class, bookId);

        if (book == null) {
            System.out.println(" Book not found.");
            return;
        }

        List<HoldRequest> queue = em.createQuery("""
                        SELECT h FROM HoldRequest h
                        WHERE h.book.id = :bookId
                        ORDER BY h.requestDate ASC
                        """, HoldRequest.class)
                .setParameter("bookId", bookId)
                .getResultList();

        if (queue.isEmpty()) {
            System.out.println(" No hold requests for this book.");
        } else {
            System.out.println("\n--- Hold Queue ---");
            queue.forEach(HoldRequest::print);
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

    public void addBorrower() {
        System.out.print("Enter name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter phone: ");
        String phone = scanner.nextLine().trim();

        Borrower borrower = new Borrower();
        borrower.setName(name);
        borrower.setEmail(email);
        borrower.setPhone(phone);
        borrower.setTotalFine(0.0);

        em.persist(borrower);

        System.out.printf(" Borrower '%s' added successfully with ID %d.\n", name, borrower.getId());
    }

    public void updateBorrower() {
        System.out.print("Enter Borrower ID to update: ");
        Long borrowerId = service.validateLongInput(scanner.nextLine().trim());

        Optional<Person> opt = service.findPerson(borrowerId);
        if (opt.isEmpty() || !(opt.get() instanceof Borrower borrower)) {
            System.out.println(" Borrower not found.");
            return;
        }

        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine().trim();
        if (!name.isEmpty()) borrower.setName(name);

        System.out.print("Enter new email (leave blank to keep current): ");
        String email = scanner.nextLine().trim();
        if (!email.isEmpty()) borrower.setEmail(email);

        System.out.print("Enter new phone (leave blank to keep current): ");
        String phone = scanner.nextLine().trim();
        if (!phone.isEmpty()) borrower.setPhone(phone);

        em.merge(borrower);
        System.out.println(" Borrower information updated successfully.");
    }
}
