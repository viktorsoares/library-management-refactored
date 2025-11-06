package com.example.library.presentation.menu;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MenuLauncher implements CommandLineRunner {

    private final MainMenu mainMenu;

    public MenuLauncher(MainMenu mainMenu) {
        this.mainMenu = mainMenu;
    }

    @Override
    public void run(String... args) {
        mainMenu.show();
    }
}
