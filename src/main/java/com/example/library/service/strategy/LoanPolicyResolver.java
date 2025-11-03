package com.example.library.service;

import com.example.library.model.Borrower;
import com.example.library.model.Book;
import com.example.library.service.strategy.LoanPolicy;
import com.example.library.service.strategy.StandardLoanPolicy;

public class LoanPolicyResolver {

    private LoanPolicy policy;

    public LoanPolicyResolver() {
        this.policy = new StandardLoanPolicy(); // default policy
    }

    public boolean canBorrow(Borrower borrower, Book book) {
        return policy.canBorrow(borrower, book);
    }

    public void setPolicy(LoanPolicy policy) {
        this.policy = policy;
    }

    public int getLoanDuration() {
        return policy.getLoanDuration();
    }
}
