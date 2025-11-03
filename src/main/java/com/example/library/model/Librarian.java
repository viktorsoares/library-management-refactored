package com.example.library.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Librarian extends Staff {
    public int officeNo;
    @Getter
    private static Librarian instance;

    public Librarian(int id, String name, String address, int phone, double salary, int officeNo) {
        super(id, name, address, phone, salary);
        this.officeNo = officeNo;
    }

    public static boolean addLibrarian(Librarian lib) {
        if (instance == null) {
            instance = lib;
            return true;
        }
        return false;
    }

}
