package com.example.library.web;

import com.example.library.model.HoldRequest;
import com.example.library.repository.HoldRequestRepository;
import com.example.library.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class HoldController {

    private final HoldRequestRepository holdRepo;
    private final BookRepository bookRepo;

    public HoldController(HoldRequestRepository holdRepo, BookRepository bookRepo) {
        this.holdRepo = holdRepo;
        this.bookRepo = bookRepo;
    }

    @GetMapping("/{bookId}/holds")
    public ResponseEntity<List<HoldRequest>> listHolds(@PathVariable Long bookId) {
        if (!bookRepo.existsById(bookId)) return ResponseEntity.notFound().build();
        var holds = holdRepo.findByBookIdOrderByRequestDateAsc(bookId);
        return ResponseEntity.ok(holds);
    }
}
