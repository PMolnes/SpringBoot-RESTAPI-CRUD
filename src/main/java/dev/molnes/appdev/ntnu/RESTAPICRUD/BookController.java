package dev.molnes.appdev.ntnu.RESTAPICRUD;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;
import java.util.stream.Collectors;

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
        book1.addAuthor(11);
        Book book2 = new Book(2, "Animal Farm", 1945, 112);
        book2.addAuthor(10);
        Book book3 = new Book(3, "1984", 1949, 328);
        book3.addAuthor(10);
        books.add(book1);
        books.add(book2);
        books.add(book3);
    }

    /**
     * Gets all the books in the list of books. If there is an authorId and/or
     * minPages value present, it will also filter based on those values, and return
     * books matching the input.
     * @param authorId the authorId of the author you want to filter books by.
     * @param minPages the minimum amount of pages you want in the books that are
     *                 returned.
     * @return A list of the books matching the filter input. If there are not filtering
     *         input, it will return all books.
     */
    @GetMapping("")
    @ApiOperation(value = "Get all books, with or without search filters",
            notes = "Get all books in the collection, or provide parameters to limit the search to books matching the filter.",
            response = Book.class,
            responseContainer = "List")
    public List<Book> getAll(@ApiParam(value = "authorId of the author you want get books by")
            @RequestParam(value = "authorId", required = false) Integer authorId, @ApiParam(value = "Minimum number of pages in the books you want to return")
            @RequestParam(value = "minPages", required = false) Integer minPages) {
        return books.stream()
                .filter(book -> (authorId == null || book.hasAuthor(authorId)))
                .filter(book -> (minPages == null || book.getNumberOfPages() >= minPages))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Find book by id",
            notes = "Provide an id to look up specific contact from the list of books",
            response = Book.class)
    public ResponseEntity<Book> getBook(@ApiParam(value = "The id of the book you want to search for") @PathVariable Integer id) {
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
    @ApiOperation(value = "Add a book to the list of books",
            notes = "Provide a book object that you want to add to the List of books")
    public ResponseEntity<String> add(@RequestBody Book book) {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        if (book != null && book.isValid()) {
            Book existingBook = findBookById(book.getId());
            if (existingBook == null) {
                books.add(book);
                response = new ResponseEntity<>(HttpStatus.CREATED);
            }
        }
        return response;
    }

    @DeleteMapping("/{id}")
    @ApiIgnore
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

    @GetMapping("/count")
    public int getNumberOfBooks() {
        return books.size();
    }
}
