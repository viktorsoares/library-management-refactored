package com.example.library.infrastructure.strategy;

import com.example.library.domain.model.Book;
import com.example.library.domain.service.LibraryService;

import java.util.List;

public class TitleSearchStrategy implements SearchStrategy {
    private final LibraryService service;

    public TitleSearchStrategy(LibraryService service) {
        this.service = service;
    }

    @Override
    public List<Book> search(String query) {
        return service.searchByTitle(query);
    }
}
