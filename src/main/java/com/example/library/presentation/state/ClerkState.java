package com.example.library.presentation.state;

import com.example.library.presentation.menu.ClerkMenu;

public class ClerkState implements UserState {
    private final ClerkMenu clerkMenu;

    public ClerkState(ClerkMenu clerkMenu) {
        this.clerkMenu = clerkMenu;
    }

    @Override
    public void handle() {
        System.out.println(" Logged in as Clerk.");
        clerkMenu.runMenu();
    }
}
