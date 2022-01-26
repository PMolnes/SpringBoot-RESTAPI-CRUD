package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    public ResponseEntity<Author> getAuthor(@PathVariable Integer id) {
        ResponseEntity<Author> response;
        Author author = findAuthorById(id);
        if (author != null) {
            response = new ResponseEntity<>(author, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PostMapping
    public ResponseEntity<String> addAuthor(@RequestBody Author author) {
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

    @DeleteMapping("/{id}")
    @ApiIgnore
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Author authorToDelete = findAuthorById(id);
        if (authorToDelete != null) {
            authors.remove(authorToDelete);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Author author) {
        ResponseEntity<String> response;
        String errorMessage = null;
        Author existingAuthor = findAuthorById(id);
        if (existingAuthor == null) {
            errorMessage = "No author with id " + id + " found.";
        }
        if (author == null || !author.isValid()) {
            errorMessage = "Wrong data in request body.";
        } else if (author.getId() != id) {
            errorMessage = "Author ID in the URL does not match the ID in JSON data.";
        }

        if (errorMessage == null) {
            authors.remove(existingAuthor);
            authors.add(author);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
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
