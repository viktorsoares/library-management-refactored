package com.example.library.facade;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.HoldRequest;
import com.example.library.model.Person;
import com.example.library.service.LibraryService;
import com.example.library.strategy.BookOperationContext;
import com.example.library.strategy.CheckOutBookStrategy;
import com.example.library.strategy.RenewBookStrategy;
import com.example.library.strategy.ReturnBookStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ClerkFacade extends AbstractFacade {
    @PersistenceContext
    private EntityManager em;

    private final LibraryService service;

    public ClerkFacade(LibraryService service) {
        this.service = service;
    }

    public void searchBook() {
        String keyword = readRequired("search keyword (title or author)");

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
        Long bookId = readValidId("Book");
        Long borrowerId = readValidId("Borrower");
        if (bookId == null || borrowerId == null) return;

        Book book = em.find(Book.class, bookId);
        Borrower borrower = em.find(Borrower.class, borrowerId);

        if (book == null || borrower == null) {
            System.out.println(" Book or Borrower not found.");
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

        System.out.printf(" Hold placed successfully for book '%s' by borrower '%s'.\n",
                book.getTitle(), borrower.getName());
    }

    public void viewBorrowerInfo() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Borrower borrower = em.find(Borrower.class, borrowerId);
        if (borrower == null) {
            System.out.println(" Borrower not found.");
        } else {
            System.out.println("\n--- Borrower Information ---");
            borrower.printInfo();
        }
    }

    public void viewBorrowerFine() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Borrower borrower = em.find(Borrower.class, borrowerId);
        if (borrower == null) {
            System.out.println(" Borrower not found.");
        } else {
            System.out.printf(" Total Fine for %s: R$ %.2f\n", borrower.getName(), borrower.getTotalFine());
        }
    }

    public void viewHoldQueue() {
        Long bookId = readValidId("Book");
        if (bookId == null) return;

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
        String name = readRequired("name");
        String email = readRequired("email");
        String phone = readRequired("phone");

        Borrower borrower = new Borrower();
        borrower.setName(name);
        borrower.setEmail(email);
        borrower.setPhone(phone);
        borrower.setTotalFine(0.0);

        em.persist(borrower);

        System.out.printf(" Borrower '%s' added successfully with ID %d.\n", name, borrower.getId());
    }

    public void updateBorrower() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Optional<Person> opt = service.findPerson(borrowerId);
        if (opt.isEmpty() || !(opt.get() instanceof Borrower borrower)) {
            System.out.println(" Borrower not found.");
            return;
        }

        String name = readOptional("name");
        if (!name.isEmpty()) borrower.setName(name);

        String email = readOptional("email");
        if (!email.isEmpty()) borrower.setEmail(email);

        String phone = readOptional("phone");
        if (!phone.isEmpty()) borrower.setPhone(phone);

        em.merge(borrower);
        System.out.println(" Borrower information updated successfully.");
    }
}
