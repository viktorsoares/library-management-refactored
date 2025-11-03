package com.example.library.service.listener;

import com.example.library.service.events.BookIssuedEvent;

public class EmailNotifier implements EventListener {

    @Override
    public void onBookIssued(BookIssuedEvent event) {
        System.out.println("Email sent: Book '" + event.getBook().getTitle() + "' issued to " + event.getBorrower().getName());
    }
}
