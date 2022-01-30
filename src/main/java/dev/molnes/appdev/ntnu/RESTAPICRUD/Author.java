package dev.molnes.appdev.ntnu.RESTAPICRUD;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

public class Author {

    @ApiModelProperty(notes = "The unique id of a author")
    private int id;
    @ApiModelProperty(notes = "The first name of a author")
    private String firstName;
    @ApiModelProperty(notes = "The last name of a author")
    private String lastName;
    @ApiModelProperty(notes = "The birth year of a author")
    private int birthYear;

    public Author(int id, String firstName, String lastName, int birthYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public boolean isValid() {
        return id > 0 && firstName != null && !firstName.equals("");
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }
}
