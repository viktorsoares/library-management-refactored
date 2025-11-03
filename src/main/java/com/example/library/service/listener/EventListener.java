package com.example.library.service.listener;

import com.example.library.service.events.BookIssuedEvent;

public interface EventListener {
    void onBookIssued(BookIssuedEvent event);
}
