package com.example.library.menu;

import java.util.Scanner;

public abstract class MenuTemplate {
    protected final Scanner scanner = new Scanner(System.in);

    public final void runMenu() {
        boolean active = true;
        while (active) {
            printHeader();
            printOptions();
            String choice = scanner.nextLine().trim();
            active = handleChoice(choice);
        }
    }

    protected abstract void printHeader();

    protected abstract void printOptions();

    protected abstract boolean handleChoice(String choice);
}
