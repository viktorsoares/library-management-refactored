package com.example.library.menu;

import com.example.library.facade.ClerkFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClerkMenu extends MenuTemplate {
    private final ClerkFacade clerkFacade;

    @Autowired
    public ClerkMenu(ClerkFacade clerkFacade) {
        this.clerkFacade = clerkFacade;
    }

    @Override
    protected void printHeader() {
        System.out.println("""
            --------------------------------------------------------
            \tWelcome to Clerk's Portal
            --------------------------------------------------------""");
    }

    @Override
    protected void printOptions() {
        System.out.println("""
            1 - Search a Book
            2 - Place a Book on Hold
            3 - Check Personal Info of Borrower
            4 - Check Total Fine of Borrower
            5 - Check Hold Requests Queue of a Book
            6 - Check Out a Book
            7 - Check In a Book
            8 - Renew a Book
            9 - Add a New Borrower
            10 - Update Borrower Info
            11 - Logout
            --------------------------------------------------------
            Enter Choice: """);
    }

    @Override
    protected boolean handleChoice(String choice) {
        switch (choice) {
            case "1" -> clerkFacade.searchBook();
            case "2" -> clerkFacade.placeHold();
            case "3" -> clerkFacade.viewBorrowerInfo();
            case "4" -> clerkFacade.viewBorrowerFine();
            case "5" -> clerkFacade.viewHoldQueue();
            case "6" -> clerkFacade.checkOutBook();
            case "7" -> clerkFacade.checkInBook();
            case "8" -> clerkFacade.renewBook();
            case "9" -> clerkFacade.addBorrower();
            case "10" -> clerkFacade.updateBorrower();
            case "11" -> {
                System.out.println("🔒 Logging out of Clerk's Portal...");
                return false;
            }
            default -> System.out.println("Invalid choice. Try again.");
        }
        System.out.print("\nPress any key to continue...");
        scanner.nextLine();
        return true;
    }
}
