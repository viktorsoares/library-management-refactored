package com.example.library.facade;

import com.example.library.model.Loan;
import com.example.library.model.Person;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

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
        List<Person> people = em.createQuery("SELECT p FROM Person p", Person.class).getResultList();

        System.out.println("\n--- Issued Books History by Person ---");
        for (Person person : people) {
            List<Loan> loans = person.getLoans();
            if (loans != null && !loans.isEmpty()) {
                System.out.printf("Borrower: %s (ID: %d)\n", person.getName(), person.getId());
                for (Loan loan : loans) {
                    System.out.printf("  Loan ID: %d | Book: %s | Issued: %s | Returned: %s\n",
                            loan.getId(),
                            loan.getBook() != null ? loan.getBook().getTitle() : "N/A",
                            loan.getIssueDate(),
                            loan.getReturnDate() != null ? loan.getReturnDate() : "--");
                }
                System.out.println();
            }
        }
    }

    public void viewAllBooks() {
        List<Person> people = em.createQuery("SELECT DISTINCT p FROM Person p JOIN p.loans l WHERE l.copyReturned = false", Person.class).getResultList();

        System.out.println("\n--- Books Currently on Loan ---");
        for (Person person : people) {
            for (Loan loan : person.getLoans()) {
                if (!loan.isCopyReturned()) {
                    System.out.printf("Book: %s | Borrower: %s | Due: %s\n",
                            loan.getBook() != null ? loan.getBook().getTitle() : "N/A",
                            person.getName(),
                            loan.getDueDate());
                }
            }
        }
    }

}
