package com.example.library.model;

import java.util.ArrayList;

public class Borrower extends Person {
    private ArrayList<Loan> borrowedBooks = new ArrayList<>();
    private ArrayList<HoldRequest> holdRequests = new ArrayList<>();

    public Borrower(int id, String name, String address, int phone) {
        super(id, name, address, phone);
    }

    public void addBorrowedBook(Loan loan) { borrowedBooks.add(loan); }
    public ArrayList<Loan> getBorrowedBooks() { return borrowedBooks; }

    public void addHoldRequest(HoldRequest hr) { holdRequests.add(hr); }
    public void removeHoldRequest(HoldRequest hr) { holdRequests.remove(hr); }
    public ArrayList<HoldRequest> getHoldRequests() { return holdRequests; }
}
