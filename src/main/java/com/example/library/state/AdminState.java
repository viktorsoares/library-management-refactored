package com.example.library.state;

import com.example.library.menu.AdminMenu;

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
