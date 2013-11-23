package edu.umn.csci5801.model;

public class InvalidUserRoleException extends Exception {
    
    public InvalidUserRoleException() {
        super();
    }
    
    public InvalidUserRoleException(String message) {
        super(message);
    }
    
    public InvalidUserRoleException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidUserRoleException(Throwable cause) {
        super(cause);
    }

}
