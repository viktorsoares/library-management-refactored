package com.example.library.service;

import com.example.library.model.*;
import com.example.library.repository.*;
import com.example.library.service.events.BookHoldPlacedEvent;
import com.example.library.service.events.BookReturnedEvent;
import com.example.library.service.strategy.LoanPolicy;
import com.example.library.service.strategy.LoanPolicyResolver;
import org.springframework.context.ApplicationEventPublisher;
import com.example.library.service.events.BookIssuedEvent;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final BorrowerRepository borrowerRepo;
    private final HoldRequestRepository holdRepo;
    private final LoanRepository loanRepo;
    private final LoanPolicyResolver policyResolver;
    private final ApplicationEventPublisher eventPublisher;

    public BookService(BookRepository bookRepo, BorrowerRepository borrowerRepo,
                       HoldRequestRepository holdRepo, LoanRepository loanRepo,
                       LoanPolicyResolver policyResolver, ApplicationEventPublisher eventPublisher) {
        this.bookRepo = bookRepo;
        this.borrowerRepo = borrowerRepo;
        this.holdRepo = holdRepo;
        this.loanRepo = loanRepo;
        this.policyResolver = policyResolver;
        this.eventPublisher = eventPublisher;
    }

    public Book addBook(Book b) { return bookRepo.save(b); }

    public List<Book> listBooks() { return bookRepo.findAll(); }

    public Optional<Book> findById(Long id){ return bookRepo.findById(id); }

    public HoldRequest placeHold(Long bookId, Long borrowerId) {
        Book book = bookRepo.findById(bookId).orElseThrow();
        Borrower br = borrowerRepo.findById(borrowerId).orElseThrow();
        HoldRequest hr = new HoldRequest();
        hr.setBook(book);
        hr.setBorrower(br);
        hr.setRequestDate(Instant.now());
        HoldRequest saved = holdRepo.save(hr);
        // publish event
        eventPublisher.publishEvent(new BookHoldPlacedEvent(this, saved.getId(), book.getId(), br.getId()));
        return saved;
    }

    public String issueBook(Long bookId, Long borrowerId, Long staffId) {
        Book book = bookRepo.findById(bookId).orElseThrow();
        Borrower borrower = borrowerRepo.findById(borrowerId).orElseThrow();
        Borrower staff = borrowerRepo.findById(staffId).orElseThrow();

        // remove expired holds (example: 7 days expiry — adjust or pull from config)
        List<HoldRequest> holds = holdRepo.findByBookIdOrderByRequestDateAsc(bookId);
        Instant now = Instant.now();
        holds.stream()
                .filter(h -> h.getRequestDate().plusSeconds(7 * 24 * 3600).isBefore(now))
                .forEach(h -> holdRepo.delete(h));

        // If issued already
        if (book.isIssued()) {
            return "Book already issued";
        }

        // If there are holds, check if borrower has earliest
        if (!holds.isEmpty()) {
            HoldRequest earliest = holds.stream().min(Comparator.comparing(HoldRequest::getRequestDate)).get();
            if (!earliest.getBorrower().getId().equals(borrower.getId())) {
                return "Someone else has earlier hold";
            } else {
                // service hold request - remove it
                holdRepo.delete(earliest);
            }
        }

        // Check policy
        LoanPolicy policy = policyResolver.resolve(borrower.getRole());
        if (!policy.canBorrow(borrower, book)) return "Policy prevented borrowing";

        // create loan
        Loan loan = new Loan();
        loan.setBorrower(borrower);
        loan.setBook(book);
        loan.setIssuedDate(Instant.now());
        loan.setState(Loan.State.BORROWED);
        loanRepo.save(loan);

        book.setIssued(true);
        bookRepo.save(book);

        // publish borrow event
        eventPublisher.publishEvent(new BookIssuedEvent(this, loan.getId(), book.getId(), borrower.getId()));

        return "Issued successfully";
    }

    public String returnBook(Long loanId, Long receiverId) {
        Loan loan = loanRepo.findById(loanId).orElseThrow();
        Borrower receiver = borrowerRepo.findById(receiverId).orElseThrow();

        loan.getBook().setIssued(false);
        loan.setReturnedDate(Instant.now());
        loan.setReceiver(receiver);
        loan.setState(Loan.State.RETURNED);
        loanRepo.save(loan);
        bookRepo.save(loan.getBook());

        // fine calculation could go here
        loan.payFine();
        eventPublisher.publishEvent(new BookReturnedEvent(this, loan.getId(), loan.getBook().getId(), loan.getBorrower().getId()));

        return "Returned";
    }
}
