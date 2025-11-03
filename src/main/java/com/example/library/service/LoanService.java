package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.Loan;
import com.example.library.service.events.BookIssuedEvent;
import com.example.library.service.events.BookReturnedEvent;
import com.example.library.service.strategy.LoanPolicy;
import com.example.library.service.strategy.LoanPolicyResolver;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final List<Loan> loans = new ArrayList<>();
    private long idCounter = 1;

    private final ApplicationEventPublisher publisher;
    private final LoanPolicyResolver policyResolver;
    private final BorrowerService borrowerService;

    public LoanService(ApplicationEventPublisher publisher, LoanPolicyResolver policyResolver, BorrowerService borrowerService) {
        this.publisher = publisher;
        this.policyResolver = policyResolver;
        this.borrowerService = borrowerService;
    }

    public String issueBook(Long bookId, Long borrowerId, Long staffId) {
        Borrower borrower = borrowerService.findById(borrowerId)
                .orElseThrow(() -> new RuntimeException("Borrower not found"));

        // Simulando busca de Book (você pode chamar BookService real)
        Book book = new Book();
        book.setId(bookId);
        book.setIssued(false);

        LoanPolicy policy = policyResolver.resolve(borrower.getRole());
        if (!policy.canBorrow(borrower, book)) {
            throw new RuntimeException("Borrower cannot borrow this book");
        }

        Loan loan = new Loan();
        loan.setId(idCounter++);
        loan.setBook(book);
        loan.setBorrower(borrower);
        loan.setStaffId(staffId);
        loan.setDueDate(LocalDate.now().plusDays(policy.maxDays(borrower)));
        loan.setReturned(false);

        loans.add(loan);

        // marcar livro como emitido
        book.setIssued(true);

        // disparar evento
        publisher.publishEvent(new BookIssuedEvent(this, loan.getId(), bookId, borrowerId));

        return "Book issued successfully (LoanID=" + loan.getId() + ")";
    }

    public String returnBook(Long loanId, Long receiverId) {
        Loan loan = loans.stream()
                .filter(l -> l.getId().equals(loanId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.isReturned()) return "Book already returned";

        loan.setReturned(true);

        // marcar livro como disponível
        loan.getBook().setIssued(false);

        // disparar evento
        publisher.publishEvent(new BookReturnedEvent(this, loanId, loan.getBook().getId(), loan.getBorrower().getId()));

        return "Book returned successfully";
    }

    public void renewLoan(Long loanId) {
        Loan loan = loans.stream()
                .filter(l -> l.getId().equals(loanId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Loan not found"));

        LoanPolicy policy = policyResolver.resolve(loan.getBorrower().getRole());
        loan.setDueDate(LocalDate.now().plusDays(policy.maxDays(loan.getBorrower())));
    }

    public double calculateFine(Long borrowerId) {
        double fine = 0.0;
        LocalDate today = LocalDate.now();
        for (Loan loan : loans) {
            if (loan.getBorrower().getId().equals(borrowerId) && !loan.isReturned()) {
                if (loan.getDueDate().isBefore(today)) {
                    fine += 1.0; // R$1 por dia atrasado
                }
            }
        }
        return fine;
    }
}
