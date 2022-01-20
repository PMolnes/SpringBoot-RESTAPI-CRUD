package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/authors")
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

    @GetMapping("")
    public List<Author> getAll() {
        return authors;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(Integer id) {
        ResponseEntity<Author> response;
        Author author = findAuthorById(id);

        if (author != null) {
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping
    public ResponseEntity<String> addAuthor(Author author) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (author != null && author.isValid()) {
            Author existingAuthor = findAuthorById(author.getId());
            if (existingAuthor == null) {
                authors.add(author);
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    private Author findAuthorById(int id) {
        Author foundAuthor = null;
        Iterator<Author> it = authors.iterator();

        while (it.hasNext() && foundAuthor == null) {
            Author currentAuthor = it.next();
            if (currentAuthor.getId() == id) {
                foundAuthor = currentAuthor;
            }
        }
        return foundAuthor;
    }
}
