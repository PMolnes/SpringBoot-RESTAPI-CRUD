package dev.molnes.appdev.ntnu.RESTAPICRUD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Book {

    private int id;
    private String title;
    private int year;
    private int numberOfPages;
    private List<Integer> authors;

    public Book(int id, String title, int year, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
        authors = new ArrayList<>();
    }

    /**
     * Check if this object is a valid book.
     * @return true if id is greater than 1, false otherwise.
     */
    public boolean isValid() {
        return id > 0 && title != null && !title.equals("");
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setYear(int year) {
        this.year = year;
    }

    private void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public List<Integer> getAuthors() {
        return authors;
    }

    /**
     * Goes through the authors list to look for given author ID.
     * @param authorId to look for.
     * @return true if authorID is in the authors list,
     * false otherwise.
     */
    public boolean hasAuthor(int authorId) {
        boolean hasAuthor = false;
        Iterator<Integer> it = authors.iterator();
        while (!hasAuthor && it.hasNext()) {
            if (it.next() == authorId) {
                hasAuthor = true;
            }
        }
        return hasAuthor;
    }

    /**
     * Adds the authorId to the list of authors to a book object,
     * if the id is valid and isn't already exist in the list.
     * @param authorId to add to the list.
     */
    public void addAuthor(int authorId) {
        if (authorId > 0 && !hasAuthor(authorId)) {
            authors.add(authorId);
        }
    }


}
