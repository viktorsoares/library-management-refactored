package com.example.library.web;

import com.example.library.facade.LibraryFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LibraryFacade libraryFacade;

    public LoanController(LibraryFacade libraryFacade) {
        this.libraryFacade = libraryFacade;
    }

}

