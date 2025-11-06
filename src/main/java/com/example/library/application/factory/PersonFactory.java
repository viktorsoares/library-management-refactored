package com.example.library.application.factory;

import com.example.library.domain.model.Person;

import java.io.BufferedReader;
import java.util.Scanner;

public interface PersonFactory {
    Person create(Scanner scanner, BufferedReader reader);
}
