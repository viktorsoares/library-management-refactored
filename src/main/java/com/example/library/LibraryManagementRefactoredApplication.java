package com.example.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LibraryManagementRefactoredApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementRefactoredApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase() {
		return args -> {
			System.out.println("Library Management System Started...");
			// Aqui você pode popular livros e usuários iniciais
		};
	}
}
