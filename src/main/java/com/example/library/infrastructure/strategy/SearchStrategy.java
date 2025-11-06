package com.example.library.infrastructure.strategy;

import com.example.library.domain.model.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(String query);
}
