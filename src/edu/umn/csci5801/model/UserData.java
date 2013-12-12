package edu.umn.csci5801.model;

/**
 * Data for reading user JSONObjects from the JSON database
 * 
 * @author mark
 * 
 */
public class UserData {
    private User user;
    private Department department;
    private Role role;

    public UserData() {
    }

    public User getUser() {
        return user;
    }

    public Department getDepartment() {
        return department;
    }

    public Role getRole() {
        return role;
    }

}
