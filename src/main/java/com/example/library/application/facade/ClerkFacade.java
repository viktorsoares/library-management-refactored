package com.example.library.application.facade;

import com.example.library.domain.model.Book;
import com.example.library.domain.model.Borrower;
import com.example.library.domain.model.HoldRequest;
import com.example.library.domain.model.Person;
import com.example.library.domain.service.LibraryService;
import com.example.library.infrastructure.strategy.BookOperationContext;
import com.example.library.infrastructure.strategy.CheckOutBookStrategy;
import com.example.library.infrastructure.strategy.RenewBookStrategy;
import com.example.library.infrastructure.strategy.ReturnBookStrategy;
import com.example.library.infrastructure.util.MessagePrinter;
import com.example.library.infrastructure.util.ValueFormatter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
            MessagePrinter.warning("No books found matching: " + keyword);
        } else {
            MessagePrinter.separator("Search Results");
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
            MessagePrinter.error("Book or Borrower not found.");
            return;
        }

        service.expireOldHoldRequests(book, 3);

        if (service.hasExistingHold(book, borrower)) {
            MessagePrinter.info("Borrower already has a hold request for this book.");
            return;
        }

        HoldRequest hold = new HoldRequest();
        hold.setBook(book);
        hold.setBorrower(borrower);
        hold.setRequestDate(LocalDate.now());

        em.persist(hold);

        MessagePrinter.success(String.format("Hold placed successfully for book '%s' by borrower '%s'.",
                book.getTitle(), borrower.getName()));
    }

    public void viewBorrowerInfo() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Borrower borrower = em.find(Borrower.class, borrowerId);
        if (borrower == null) {
            MessagePrinter.error("Borrower not found.");
        } else {
            MessagePrinter.separator("Borrower Information");
            borrower.printInfo();
        }
    }

    public void viewBorrowerFine() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Borrower borrower = em.find(Borrower.class, borrowerId);
        if (borrower == null) {
            MessagePrinter.error("Borrower not found.");
            return;
        }

        double fine = borrower.getTotalFine() != null ? borrower.getTotalFine() : 0.0;
        MessagePrinter.info(String.format("Total Fine for %s: %s",
                borrower.getName(), ValueFormatter.formatCurrency(fine)));
    }

    public void viewHoldQueue() {
        Long bookId = readValidId("Book");
        if (bookId == null) return;

        Book book = em.find(Book.class, bookId);
        if (book == null) {
            MessagePrinter.error("Book not found.");
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
            MessagePrinter.info("No hold requests for this book.");
        } else {
            MessagePrinter.separator("Hold Queue");
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

    @Transactional
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

        MessagePrinter.success(String.format("Borrower '%s' added successfully with ID %d.", name, borrower.getId()));
    }

    @Transactional
    public void updateBorrower() {
        Long borrowerId = readValidId("Borrower");
        if (borrowerId == null) return;

        Optional<Person> opt = service.findPerson(borrowerId);
        if (opt.isEmpty() || !(opt.get() instanceof Borrower borrower)) {
            MessagePrinter.error("Borrower not found.");
            return;
        }

        String name = readOptional("name");
        if (!name.isEmpty()) borrower.setName(name);

        String email = readOptional("email");
        if (!email.isEmpty()) borrower.setEmail(email);

        String phone = readOptional("phone");
        if (!phone.isEmpty()) borrower.setPhone(phone);

        em.merge(borrower);
        em.flush();
        MessagePrinter.success("Borrower information updated successfully.");
    }
}
