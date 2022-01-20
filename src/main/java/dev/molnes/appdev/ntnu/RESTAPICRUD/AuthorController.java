package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class AuthorController {

    private ArrayList<Author> authors;

    public AuthorController() {
        authors = new ArrayList<>();
        initializeData();
    }
    
    private void initializeData() {
        Author author1 = new Author(10, "George", "Orwell", 1903);
        Author author2 = new Author(11, "Jordan", "Peterson", 1962);
        Author author3 = new Author(12, "Joanne", "Rowling", 1965);
        authors.add(author1);
        authors.add(author2);
        authors.add(author3);
    }
}
