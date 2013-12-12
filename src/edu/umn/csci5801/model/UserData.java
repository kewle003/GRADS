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

    public UserData(User user, Department department, Role role) {
        this.user = user;
        this.department = department;
        this.role = role;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Department getDepartment() {
        return department;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

}
