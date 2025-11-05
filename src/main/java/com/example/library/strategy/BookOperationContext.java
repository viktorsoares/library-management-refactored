package com.example.library.strategy;

public class BookOperationContext {
    private BookOperationStrategy strategy;

    public void setStrategy(BookOperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        if (strategy != null) {
            strategy.execute();
        } else {
            System.out.println("⚠ No strategy defined.");
        }
    }
}
