package com.example.library.strategy;

import com.example.library.model.Book;
import com.example.library.service.LibraryService;

import java.util.List;

public class AuthorSearchStrategy implements SearchStrategy {
    private final LibraryService service;
    public AuthorSearchStrategy(LibraryService service){ this.service = service; }
    @Override public List<Book> search(String query){ return service.searchByAuthor(query); }
}
