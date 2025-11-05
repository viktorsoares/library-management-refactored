package com.example.library.menu;

import com.example.library.command.AddBookCommand;
import com.example.library.command.CommandInvoker;
import com.example.library.command.SearchBookCommand;
import com.example.library.facade.ClerkFacade;
import com.example.library.facade.LibrarianFacade;
import com.example.library.util.MessagePrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LibrarianMenu extends MenuTemplate {
    private final ClerkFacade clerkFacade;
    private final LibrarianFacade librarianFacade;
    private final CommandInvoker invoker = new CommandInvoker();

    @Autowired
    public LibrarianMenu(ClerkFacade clerkFacade, LibrarianFacade librarianFacade) {
        this.clerkFacade = clerkFacade;
        this.librarianFacade = librarianFacade;
    }

    @Override
    protected void printHeader() {
        MessagePrinter.header("Welcome to Librarian's Portal");
        System.out.println("Following Functionalities are available:\n");
    }

    @Override
    protected void printOptions() {
        System.out.println("""
                1 - Search a Book
                2 - Place a Book on hold
                3 - Check Personal Info of Borrower
                4 - Check Total Fine of Borrower
                5 - Check Hold Requests Queue of a Book
                6 - Check out a Book
                7 - Check in a Book
                8 - Renew a Book
                9 - Add a new Borrower
                10 - Update a Borrower's Info
                11 - Add new Book
                12 - Remove a Book
                13 - Change a Book's Info
                14 - Check Personal Info of Clerk
                15 - Logout
                --------------------------------------------------------
                Enter Choice: """);
    }

    @Override
    protected boolean handleChoice(String choice) {
        switch (choice) {
            case "1" -> {
                invoker.setCommand(new SearchBookCommand(clerkFacade));
                invoker.run();
            }
            case "2" -> clerkFacade.placeHold();
            case "3" -> clerkFacade.viewBorrowerInfo();
            case "4" -> clerkFacade.viewBorrowerFine();
            case "5" -> clerkFacade.viewHoldQueue();
            case "6" -> librarianFacade.checkOutBook();
            case "7" -> librarianFacade.checkInBook();
            case "8" -> librarianFacade.renewBook();
            case "9" -> clerkFacade.addBorrower();
            case "10" -> clerkFacade.updateBorrower();
            case "11" -> {
                invoker.setCommand(new AddBookCommand(librarianFacade));
                invoker.run();
            }
            case "12" -> librarianFacade.removeBook();
            case "13" -> librarianFacade.updateBookInfo();
            case "14" -> librarianFacade.viewClerkInfo();
            case "15" -> {
                MessagePrinter.info("Logging out of Librarian's Portal...");
                return false;
            }
            default -> MessagePrinter.warning("Invalid choice. Try again.");
        }
        MessagePrinter.pressAnyKey();
        scanner.nextLine();
        return true;
    }
}
