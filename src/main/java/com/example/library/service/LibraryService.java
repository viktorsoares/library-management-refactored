package com.example.library.service;

import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.Loan;
import com.example.library.model.Person;
import com.example.library.repository.BookRepository;
import com.example.library.repository.LoanRepository;
import com.example.library.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryService {

    private final BookRepository bookRepo;
    private final PersonRepository personRepo;
    private final LoanRepository loanRepo;

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

        // Verifica disponibilidade via campo 'status'
        if (!"available".equalsIgnoreCase(book.getStatus())) return false;

        // Atualiza status do livro
        book.setStatus("checked out");
        bookRepo.save(book);

        // Cria e salva o empréstimo
        Loan loan = new Loan();
        loan.setBook(book);
        loan.setBorrower(person);
        loan.setIssueDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2));
        loanRepo.save(loan);

        return true;
    }


    @Transactional
    public boolean returnBook(Long bookId) {
        var optB = bookRepo.findById(bookId);
        if (optB.isEmpty()) return false;
        Book b = optB.get();
        b.setStatus("avaliabo");
        bookRepo.save(b);
        // remove loan entries (simple approach)
        var loans = loanRepo.findByBookId(bookId);
        loanRepo.deleteAll(loans);
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
}
