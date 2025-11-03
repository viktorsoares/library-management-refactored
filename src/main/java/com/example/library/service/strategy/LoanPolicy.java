package com.example.library.service.strategy;

import com.example.library.model.Borrower;
import com.example.library.model.Book;

public interface LoanPolicy {
    boolean canBorrow(Borrower borrower, Book book);
    int getLoanDuration();
}
