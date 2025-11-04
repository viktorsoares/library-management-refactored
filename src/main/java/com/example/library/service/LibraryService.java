package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.HoldRequest;
import com.example.library.model.Librarian;
import com.example.library.model.Loan;
import com.example.library.model.Person;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepo;
    private final PersonRepository personRepo;
    private final LoanRepository loanRepo;

    @PersistenceContext
    private EntityManager em;

    public LibraryService(BookRepository b, PersonRepository p, LoanRepository l) {
        this.bookRepo = b;
        this.personRepo = p;
        this.loanRepo = l;
    }

    public Book addBook(String title, String author, String subject, String publisher,
                        String ISBN, String edition, int year, double price, int pages) {
        Book book = new Book(title, author, subject, publisher, ISBN, edition, year, price, pages, "available");
        return bookRepo.save(book);
    }

    public Person addPerson(String name, String address, String phone, String role, String password) {
        Borrower borrower = new Borrower(name, address, phone, role, password);
        borrower.setPassword(password);
        return personRepo.save(borrower);
    }

    public Optional<Book> findBook(Long id) {
        return bookRepo.findById(id);
    }

    public Optional<Person> findPerson(Long id) {
        return personRepo.findById(id);
    }

    public List<Book> searchByTitle(String q) {
        return bookRepo.findByTitleContainingIgnoreCase(q);
    }

    public List<Book> searchByAuthor(String q) {
        return bookRepo.findByAuthorContainingIgnoreCase(q);
    }

    @Transactional
    public boolean loanBook(Long bookId, Long personId) {
        Optional<Book> optBook = bookRepo.findById(bookId);
        Optional<Person> optPerson = personRepo.findById(personId);

        if (optBook.isEmpty() || optPerson.isEmpty()) return false;

        Book book = optBook.get();
        Person person = optPerson.get();

        // Verifica se é um Borrower
        if (!(person instanceof Borrower borrower)) {
            System.out.println(" Person is not a borrower.");
            return false;
        }

        if (!"available".equalsIgnoreCase(book.getStatus())) return false;

        book.setStatus("borrowed");
        book.setBorrowedBy(borrower);
        book.setDueDate(LocalDate.now().plusWeeks(2));
        bookRepo.save(book);

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBorrower(borrower);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(book.getDueDate());
        loan.setFinePaid(false);
        loan.setCopyReturned(false);
        loan.setAmount(0.0);

        loanRepo.save(loan);
        return true;
    }


    @Transactional
    public boolean returnBook(Long bookId) {
        Optional<Book> optBook = bookRepo.findById(bookId);
        if (optBook.isEmpty()) return false;

        Book book = optBook.get();
        if (!"borrowed".equalsIgnoreCase(book.getStatus())) return false;

        List<Loan> activeLoans = loanRepo.findByBookId(bookId).stream()
                .filter(l -> l.getReturnDate() == null)
                .toList();

        if (activeLoans.isEmpty()) return false;

        Loan loan = activeLoans.get(0);
        loan.setReturnDate(LocalDate.now());
        loan.setCopyReturned(true);

        long overdueDays = book.getDueDate().until(LocalDate.now(), java.time.temporal.ChronoUnit.DAYS);
        if (overdueDays > 0) {
            double fine = overdueDays * 1.0;
            loan.setAmount(fine);
            loan.setFinePaid(false);

            if (book.getBorrowedBy() instanceof Borrower borrower) {
                borrower.setTotalFine(borrower.getTotalFine() + fine);
                personRepo.save(borrower);
            }
        } else {
            loan.setFinePaid(true);
        }

        loanRepo.save(loan);

        book.setStatus("available");
        book.setBorrowedBy(null);
        book.setDueDate(null);
        bookRepo.save(book);

        return true;
    }

    public List<Book> listBooks() {
        return bookRepo.findAll();
    }

    public List<Person> listPeople() {
        return personRepo.findAll();
    }

    public List<Loan> listLoans() {
        return loanRepo.findAll();
    }

    @Transactional
    public boolean addLibrarian(Librarian librarian) {
        List<Librarian> existing = em.createQuery("SELECT l FROM Librarian l", Librarian.class).getResultList();
        if (existing.isEmpty()) {
            em.persist(librarian);
            return true;
        } else {
            System.out.println(" A librarian already exists.");
            return false;
        }
    }

    public boolean hasExistingHold(Book book, Borrower borrower) {
        return book.getHoldRequests().stream()
                .anyMatch(hr -> hr.getBorrower().equals(borrower));
    }

    @Transactional
    public boolean removeFirstHoldRequest(Book book) {
        List<HoldRequest> queue = book.getHoldRequests();
        if (!queue.isEmpty()) {
            HoldRequest first = queue.get(0);
            em.remove(first);
            queue.remove(0);
            em.merge(book);
            return true;
        }
        return false;
    }

    @Transactional
    public void expireOldHoldRequests(Book book, int expiryDays) {
        LocalDate today = LocalDate.now();
        List<HoldRequest> expired = book.getHoldRequests().stream()
                .filter(hr -> hr.getRequestDate().plusDays(expiryDays).isBefore(today))
                .toList();

        for (HoldRequest hr : expired) {
            em.remove(hr);
            book.getHoldRequests().remove(hr);
        }

        em.merge(book);
    }

    public double calculateFine(Loan loan, int deadlineDays, double perDayFine) {
        if (loan.getReturnDate() == null || loan.isFinePaid()) return 0.0;

        long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        if (overdueDays > deadlineDays) {
            return (overdueDays - deadlineDays) * perDayFine;
        }
        return 0.0;
    }

    @Transactional
    public boolean renewLoan(Long loanId, int extraDays) {
        Optional<Loan> optLoan = loanRepo.findById(loanId);
        if (optLoan.isEmpty()) return false;

        Loan loan = optLoan.get();
        if (loan.getReturnDate() != null) return false;

        loan.setDueDate(loan.getDueDate().plusDays(extraDays));
        loanRepo.save(loan);
        return true;
    }

    @Transactional
    public boolean payFine(Long loanId) {
        Optional<Loan> optLoan = loanRepo.findById(loanId);
        if (optLoan.isEmpty()) return false;

        Loan loan = optLoan.get();
        loan.setFinePaid(true);
        loanRepo.save(loan);
        return true;
    }

    @Transactional
    public boolean renewBook(Long bookId, int extraDays) {
        Optional<Book> optBook = bookRepo.findById(bookId);
        if (optBook.isEmpty()) return false;

        Book book = optBook.get();
        if (!"borrowed".equalsIgnoreCase(book.getStatus())) return false;

        book.setDueDate(book.getDueDate().plusDays(extraDays));
        bookRepo.save(book);
        return true;
    }

    @Transactional
    public void deleteBook(Book book) {
        bookRepo.delete(book);
    }

    @Transactional
    public void updateBook(Book book) {
        bookRepo.save(book);
    }

    public Long validateLongInput(String input) {
        if (input.matches("\\d+")) {
            return Long.parseLong(input);
        } else {
            System.out.println(" Invalid input. Please enter a valid numeric ID.");
            return null;
        }
    }


}