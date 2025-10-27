package com.example.library.service.events;

import org.springframework.context.ApplicationEvent;

public class BookIssuedEvent extends ApplicationEvent {
    private final Long loanId;
    private final Long bookId;
    private final Long borrowerId;
    public BookIssuedEvent(Object source, Long loanId, Long bookId, Long borrowerId) {
        super(source);
        this.loanId = loanId; this.bookId = bookId; this.borrowerId = borrowerId;
    }
    public Long getLoanId(){return loanId;}
    public Long getBookId(){return bookId;}
    public Long getBorrowerId(){return borrowerId;}
}
