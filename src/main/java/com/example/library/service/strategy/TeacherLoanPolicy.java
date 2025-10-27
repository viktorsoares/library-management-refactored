// src/main/java/com/example/library/service/strategy/TeacherLoanPolicy.java
package com.example.library.service.strategy;

import com.example.library.model.Borrower;
import com.example.library.model.Book;
import org.springframework.stereotype.Component;

@Component("TEACHER")
public class TeacherLoanPolicy implements LoanPolicy {
    @Override
    public int maxDays(Borrower user) { return 30; }
    @Override
    public boolean canBorrow(Borrower user, Book book) { return true; }
}
