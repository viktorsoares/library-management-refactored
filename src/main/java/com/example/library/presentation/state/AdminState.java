package com.example.library.presentation.state;

import com.example.library.presentation.menu.AdminMenu;

public class AdminState implements UserState {
    private final AdminMenu adminMenu;

    public AdminState(AdminMenu adminMenu) {
        this.adminMenu = adminMenu;
    }

    @Override
    public void handle() {
        System.out.println(" Access granted to Admin Portal.");
        adminMenu.runMenu();
    }
}
