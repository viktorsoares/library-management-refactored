package com.example.library.factory;

import com.example.library.model.Person;

import java.io.BufferedReader;
import java.util.Scanner;

public interface PersonFactory {
    Person create(Scanner scanner, BufferedReader reader);
}
