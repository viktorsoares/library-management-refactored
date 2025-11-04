package com.example.library.facade;

import com.example.library.factory.EntityFactory;
import com.example.library.model.Book;
import com.example.library.model.Loan;
import com.example.library.model.Person;
import com.example.library.service.LibraryService;
import com.example.library.strategy.AuthorSearchStrategy;
import com.example.library.strategy.SearchStrategy;
import com.example.library.strategy.TitleSearchStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LibraryFacade {
    private final LibraryService service;
    private final EntityFactory factory;

    public LibraryFacade(LibraryService service) {
        this.service = service;
        this.factory = new EntityFactory(service);
    }

    public Book addBook(String title, String author, String subject, String publisher,
                        String ISBN, String edition, int year, double price, int pages) {
        return factory.createAndSaveBook(title, author, subject, publisher, ISBN, edition, year, price, pages);
    }

    public Person addPerson(String name, String address, String phone, String role, String password) {
        return factory.createAndSavePerson(name, address, phone, role, password);
    }

    public Optional<Book> findBook(Long id) {
        return service.findBook(id);
    }

    public Optional<Person> findPerson(Long id) {
        return service.findPerson(id);
    }

    public List<Book> searchByTitle(String q) {
        return service.searchByTitle(q);
    }

    public List<Book> searchByAuthor(String q) {
        return service.searchByAuthor(q);
    }

    public boolean loan(Long bookId, Long personId) {
        return service.loanBook(bookId, personId);
    }

    public boolean returnBook(Long bookId) {
        return service.returnBook(bookId);
    }

    public List<Book> listBooks() {
        return service.listBooks();
    }

    public List<Person> listPeople() {
        return service.listPeople();
    }

    public List<Loan> listLoans() {
        return service.listLoans();
    }

    // Strategy factory
    public SearchStrategy getTitleStrategy() {
        return new TitleSearchStrategy(service);
    }

    public SearchStrategy getAuthorStrategy() {
        return new AuthorSearchStrategy(service);
    }


}
