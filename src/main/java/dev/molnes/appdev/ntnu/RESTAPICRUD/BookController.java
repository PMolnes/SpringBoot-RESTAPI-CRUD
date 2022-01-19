package dev.molnes.appdev.ntnu.RESTAPICRUD;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BookController {

    private ArrayList<Book> books;

    /**
     * Initializes the books ArrayList and
     * calls initializeData() to fill it.
     */
    public BookController() {
        this.books = new ArrayList<>();
        initializeData();
    }

    /**
     * Creates three separate book objects then
     * inserts them one by one into the books
     * ArrayList.
     */
    private void initializeData() {
        Book book1 = new Book(1, "12 Rules For Life", 2018, 448);
        Book book2 = new Book(2, "Animal Farm", 1945, 112);
        Book book3 = new Book(3, "1984", 1949, 328);
        books.add(book1);
        books.add(book2);
        books.add(book3);
    }


}
