package com.example.library.strategy;

import com.example.library.model.Book;
import java.util.List;

public interface SearchStrategy {
    List<Book> search(String query);
}
