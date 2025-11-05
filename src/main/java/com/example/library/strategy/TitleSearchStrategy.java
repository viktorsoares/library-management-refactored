package com.example.library.strategy;

import com.example.library.model.Book;
import com.example.library.service.LibraryService;

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
