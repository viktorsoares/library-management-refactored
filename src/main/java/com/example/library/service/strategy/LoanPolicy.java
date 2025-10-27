package com.example.library.service.strategy;

import com.example.library.model.Borrower;
import com.example.library.model.Book;

public interface LoanPolicy {
    int maxDays(Borrower user);
    boolean canBorrow(Borrower user, Book book);
}
