package com.example.library.service.strategy;

import com.example.library.model.Borrower;
import com.example.library.model.Book;
import org.springframework.stereotype.Component;

@Component("STUDENT")
public class StudentLoanPolicy implements LoanPolicy {
    @Override
    public int maxDays(Borrower user) { return 14; }
    @Override
    public boolean canBorrow(Borrower user, Book book) {
        return !book.isIssued(); // simple rule: can't borrow if already issued
    }
}
