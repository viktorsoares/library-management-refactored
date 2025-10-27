// src/main/java/com/example/library/web/BookController.java
package com.example.library.web;

import com.example.library.facade.LibraryFacade;
import com.example.library.factory.EntityFactory;
import com.example.library.model.Book;
import com.example.library.model.Borrower;
import com.example.library.model.HoldRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final LibraryFacade facade;

    public BookController(LibraryFacade facade) { this.facade = facade; }

    @PostMapping
    public ResponseEntity<Book> add(@RequestBody Book dto) {
        return ResponseEntity.ok(facade.addBook(dto));
    }

    @GetMapping
    public ResponseEntity<List<Book>> list() { return ResponseEntity.ok(facade.listBooks()); }

    @PostMapping("/{bookId}/hold/{borrowerId}")
    public ResponseEntity<HoldRequest> placeHold(@PathVariable Long bookId, @PathVariable Long borrowerId) {
        return ResponseEntity.ok(facade.placeHold(bookId, borrowerId));
    }

    @PostMapping("/{bookId}/issue")
    public ResponseEntity<String> issue(@PathVariable Long bookId,
                                        @RequestParam Long borrowerId,
                                        @RequestParam Long staffId) {
        return ResponseEntity.ok(facade.issue(bookId, borrowerId, staffId));
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnLoan(@RequestParam Long loanId, @RequestParam Long receiverId) {
        return ResponseEntity.ok(facade.returnLoan(loanId, receiverId));
    }
}