package com.example.library.service.listener;

import com.example.library.service.events.BookIssuedEvent;
import com.example.library.service.events.BookReturnedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {
    @EventListener
    public void onBookIssued(BookIssuedEvent ev) {
        // Here you would call NotificationPort (Adapter) — for demo, we log
        System.out.println("Event: book issued -> loanId=" + ev.getLoanId());
    }

    @EventListener
    public void onBookReturned(BookReturnedEvent ev) {
        System.out.println("Event: book returned -> loanId=" + ev.getLoanId());
    }
}
