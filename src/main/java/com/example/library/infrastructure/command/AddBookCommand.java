package com.example.library.infrastructure.command;

import com.example.library.application.facade.LibrarianFacade;

public class AddBookCommand implements Command {
    private final LibrarianFacade librarianFacade;

    public AddBookCommand(LibrarianFacade librarianFacade) {
        this.librarianFacade = librarianFacade;
    }

    @Override
    public void execute() {
        librarianFacade.addNewBook();
    }
}
