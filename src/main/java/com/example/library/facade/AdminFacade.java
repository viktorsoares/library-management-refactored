package com.example.library.facade;

import com.example.library.model.Loan;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.util.MessagePrinter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class AdminFacade {

    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    @PersistenceContext
    private EntityManager em;

    public AdminFacade(BookRepository bookRepository, LoanRepository loanRepository) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
    }

    public void viewIssuedBooksHistory() {
        List<Loan> loans = loanRepository.findAll();

        MessagePrinter.separator("Issued Books History by Person");

        if (CollectionUtils.isEmpty(loans)) {
            MessagePrinter.info("No issued books found.");
        } else {
            loans.forEach(loan -> {
                String borrower = loan.getBorrower() != null ? loan.getBorrower().getName() : "Unknown";
                System.out.printf("Loan ID: %d | Book: %s | Borrower: %s | Issued: %s | Returned: %s\n",
                        loan.getId(),
                        loan.getBook() != null ? loan.getBook().getTitle() : "N/A",
                        borrower,
                        loan.getIssueDate(),
                        loan.getReturnDate() != null ? loan.getReturnDate() : "--");
            });
        }
    }

    public void viewAllBooks() {
        List<Loan> activeLoans = loanRepository.findAll().stream()
                .filter(loan -> !loan.isCopyReturned())
                .toList();

        MessagePrinter.separator("Books Currently on Loan");

        if (CollectionUtils.isEmpty(activeLoans)) {
            MessagePrinter.info("No books currently on loan.");
        } else {
            activeLoans.forEach(loan -> {
                String borrower = loan.getBorrower() != null ? loan.getBorrower().getName() : "Unknown";
                System.out.printf("Book: %s | Borrower: %s | Due: %s\n",
                        loan.getBook() != null ? loan.getBook().getTitle() : "N/A",
                        borrower,
                        loan.getDueDate());
            });
        }
    }

}
