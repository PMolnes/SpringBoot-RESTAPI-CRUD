package dev.molnes.appdev.ntnu.RESTAPICRUD;

public class Book {

    private int id;
    private String title;
    private int year;
    private int numberOfPages;

    public Book(int id, String title, int year, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
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

    private int getId() {
        return id;
    }

    private String getTitle() {
        return title;
    }

    private int getYear() {
        return year;
    }

    private int getNumberOfPages() {
        return numberOfPages;
    }
}
