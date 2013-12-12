package edu.umn.csci5801.model;

/**
 * Class for reading in User data from the JSON
 * 
 * @author mark
 * 
 */
public class User {
    private String firstName;
    private String lastName;
    private String id;

    public User() {
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getId() {
        return id;
    }
}
