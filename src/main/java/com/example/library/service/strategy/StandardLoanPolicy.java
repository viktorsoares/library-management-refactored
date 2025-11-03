package com.example.library.service.strategy;

import com.example.library.model.Borrower;
import com.example.library.model.Book;

public class StandardLoanPolicy implements LoanPolicy {

    @Override
    public boolean canBorrow(Borrower borrower, Book book) {
        return borrower.getBorrowedBooks().size() < 5 && !book.getIssuedStatus();
    }

    @Override
    public int getLoanDuration() {
        return 14; // 14 days
    }
}
