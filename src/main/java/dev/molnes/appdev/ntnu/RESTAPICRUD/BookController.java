package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private List<Book> books;

    /**
     * Initializes the books ArrayList and
     * calls initializeData() to fill it.
     */
    public BookController() {
        initializeData();
    }

    /**
     * Creates three separate book objects then
     * inserts them one by one into the books
     * ArrayList.
     */
    private void initializeData() {
        books = new ArrayList<>();
        Book book1 = new Book(1, "12 Rules For Life", 2018, 448);
        Book book2 = new Book(2, "Animal Farm", 1945, 112);
        Book book3 = new Book(3, "1984", 1949, 328);
        books.add(book1);
        books.add(book2);
        books.add(book3);
    }

    @GetMapping("")
    public List<Book> getAll() {
        return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Integer id) {
        ResponseEntity<Book> response;
        Book book = findBookById(id);
        if (book != null) {
            response = new ResponseEntity<>(book, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    private Book findBookById(Integer id) {
        Book foundBook = null;
        Iterator<Book> it = books.iterator();

        while (it.hasNext() && foundBook == null) {
            Book currentBook = it.next();
            if (currentBook.getId() == id) {
                foundBook = currentBook;
            }
        }
        return foundBook;
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody Book book) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (book != null && book.isValid()) {
            Book existingBook = findBookById(book.getId());
            if (existingBook == null) {
                books.add(book);
                response = new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return response;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        ResponseEntity<String> response;
        Book book = findBookById(id);
        if (book != null) {
            books.remove(book);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Book book) {
        ResponseEntity<String> response;
        String errorMessage = null;
        Book existingBook = findBookById(id);
        if (existingBook == null) {
            errorMessage = "No book with id " + id + " found.";
        }
        if (book == null || !book.isValid()) {
            errorMessage = "Wrong data in request body.";
        } else if (book.getId() != id) {
            errorMessage = "Book ID in the URL does not match the ID in JSON data.";
        }

        if (errorMessage == null) {
            books.remove(existingBook);
            books.add(book);
            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
