package com.example.library.facade;

import com.example.library.model.Book;
import com.example.library.model.HoldRequest;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LibraryFacade {
    private final BookService bookService;
    private final BookRepository bookRepository;

    public LibraryFacade(BookService bookService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        return bookService.addBook(book);
    }

    public List<Book> listBooks() {
        return bookService.listBooks();
    }

    public Optional<Book> findBook(Long id) { return bookService.findById(id); }

    public HoldRequest placeHold(Long bookId, Long borrowerId) { return bookService.placeHold(bookId, borrowerId); }

    public String issue(Long bookId, Long borrowerId, Long staffId) { return bookService.issueBook(bookId, borrowerId, staffId); }

    public String returnLoan(Long loanId, Long receiverId) { return bookService.returnBook(loanId, receiverId); }
}
