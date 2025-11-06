package com.example.library.infrastructure.command;

import com.example.library.application.facade.ClerkFacade;

public class SearchBookCommand implements Command {
    private final ClerkFacade clerkFacade;

    public SearchBookCommand(ClerkFacade clerkFacade) {
        this.clerkFacade = clerkFacade;
    }

    @Override
    public void execute() {
        clerkFacade.searchBook();
    }
}
