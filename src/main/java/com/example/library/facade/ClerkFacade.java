package com.example.library.facade;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.HoldRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class ClerkFacade {
    @PersistenceContext
    private EntityManager em;

    private final Scanner scanner = new Scanner(System.in);

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
        Long bookId = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Enter Borrower ID: ");
        Long borrowerId = Long.parseLong(scanner.nextLine().trim());

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

        HoldRequest hold = new HoldRequest();
        hold.setBook(book);
        hold.setBorrower(borrower);
        hold.setRequestDate(LocalDate.now());

        em.persist(hold);

        System.out.println(" Hold placed successfully for book '" + book.getTitle() + "' by borrower '" + borrower.getName() + "'.");
    }


    public void viewBorrowerInfo() {
        System.out.print("Enter Borrower ID: ");
        Long borrowerId = Long.parseLong(scanner.nextLine().trim());

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
        Long borrowerId = Long.parseLong(scanner.nextLine().trim());

        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (borrower == null) {
            System.out.println(" Borrower not found.");
        } else {
            System.out.printf("💰 Total Fine for %s: R$ %.2f\n", borrower.getName(), borrower.getTotalFine());
        }
    }

    public void viewHoldQueue() {
        System.out.print("Enter Book ID: ");
        Long bookId = Long.parseLong(scanner.nextLine().trim());

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
        System.out.print("Enter Book ID: ");
        Long bookId = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Enter Borrower ID: ");
        Long borrowerId = Long.parseLong(scanner.nextLine().trim());

        Book book = em.find(Book.class, bookId);
        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (book == null || borrower == null) {
            System.out.println(" Book or Borrower not found.");
            return;
        }

        if (!book.getStatus().equalsIgnoreCase("available")) {
            System.out.println(" Book is not available for checkout.");
            return;
        }

        book.setStatus("borrowed");
        book.setBorrowedBy(borrower);
        book.setDueDate(LocalDate.now().plusDays(14));

        em.merge(book);

        System.out.printf(" Book '%s' checked out to %s. Due in 14 days.\n", book.getTitle(), borrower.getName());
    }

    public void checkInBook() {
        System.out.print("Enter Book ID to check in: ");
        Long bookId = Long.parseLong(scanner.nextLine().trim());

        Book book = em.find(Book.class, bookId);

        if (book == null) {
            System.out.println(" Book not found.");
            return;
        }

        if (!book.getStatus().equalsIgnoreCase("borrowed")) {
            System.out.println("ℹ Book is not currently borrowed.");
            return;
        }

        book.setStatus("available");
        book.setBorrowedBy(null);
        book.setDueDate(null);

        em.merge(book);

        System.out.printf(" Book '%s' has been checked in and is now available.\n", book.getTitle());
    }


    public void renewBook() {
        System.out.print("Enter Book ID to renew: ");
        Long bookId = Long.parseLong(scanner.nextLine().trim());

        Book book = em.find(Book.class, bookId);

        if (book == null) {
            System.out.println(" Book not found.");
            return;
        }

        if (!"borrowed".equalsIgnoreCase(book.getStatus())) {
            System.out.println(" Book is not currently borrowed.");
            return;
        }

        book.setDueDate(book.getDueDate().plusDays(14));
        em.merge(book);

        System.out.printf(" Book '%s' renewed. New due date: %s\n", book.getTitle(), book.getDueDate());
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
        Long borrowerId = Long.parseLong(scanner.nextLine().trim());

        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (borrower == null) {
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
