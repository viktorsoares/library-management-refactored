package com.example.library.service.events;

import org.springframework.context.ApplicationEvent;

public class BookHoldPlacedEvent extends ApplicationEvent {
    private final Long holdId;
    private final Long bookId;
    private final Long borrowerId;

    public BookHoldPlacedEvent(Object source, Long holdId, Long bookId, Long borrowerId) {
        super(source);
        this.holdId = holdId;
        this.bookId = bookId;
        this.borrowerId = borrowerId;
    }

    public Long getHoldId() { return holdId; }
    public Long getBookId() { return bookId; }
    public Long getBorrowerId() { return borrowerId; }
}
