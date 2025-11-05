package com.example.library.state;

import com.example.library.menu.LibrarianMenu;

public class LibrarianState implements UserState {
    private final LibrarianMenu librarianMenu;

    public LibrarianState(LibrarianMenu librarianMenu) {
        this.librarianMenu = librarianMenu;
    }

    @Override
    public void handle() {
        System.out.println(" Logged in as Librarian.");
        librarianMenu.runMenu();
    }
}
