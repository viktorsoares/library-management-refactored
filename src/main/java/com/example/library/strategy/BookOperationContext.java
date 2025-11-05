package com.example.library.strategy;

import com.example.library.util.MessagePrinter;

public class BookOperationContext {
    private BookOperationStrategy strategy;

    public void setStrategy(BookOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        if (strategy != null) {
            strategy.execute();
        } else {
            MessagePrinter.warning("No strategy defined.");
        }
    }
}