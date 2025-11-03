package com.example.library.model;

import java.util.ArrayList;

public class Book {
    private static int IDCounter = 0;
    private int id;
    private String title;
    private String subject;
    private String author;
    private boolean issuedStatus;
    private ArrayList<HoldRequest> holdRequests = new ArrayList<>();

    public Book(int id, String title, String subject, String author, boolean issuedStatus) {
        if (id == -1) this.id = ++IDCounter;
        else {
            this.id = id;
            if (id > IDCounter) IDCounter = id;
        }
        this.title = title;
        this.subject = subject;
        this.author = author;
        this.issuedStatus = issuedStatus;
    }

    public int getID() { return id; }
    public String getTitle() { return title; }
    public String getSubject() { return subject; }
    public String getAuthor() { return author; }
    public boolean getIssuedStatus() { return issuedStatus; }
    public void setIssuedStatus(boolean status) { this.issuedStatus = status; }

    public ArrayList<HoldRequest> getHoldRequests() { return holdRequests; }
    public void addHoldRequest(HoldRequest hr) { holdRequests.add(hr); }

    public void printInfo() {
        System.out.printf("%s\t\t%s\t\t%s", title, author, subject);
    }

    public static void setIDCount(int max) { IDCounter = max; }
}
