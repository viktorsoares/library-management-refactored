package com.example.library.infrastructure.command;

public class CommandInvoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void run() {
        if (command != null) {
            command.execute();
        } else {
            System.out.println(" No command defined.");
        }
    }
}
