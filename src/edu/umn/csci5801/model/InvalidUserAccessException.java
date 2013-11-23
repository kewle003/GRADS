package edu.umn.csci5801.model;

/**
 * This exception is used for when a user is trying
 * to access data from GRADS and they do not
 * have the permission to do so.
 * @author mark
 *
 */
public class InvalidUserAccessException extends Exception {
    
    public InvalidUserAccessException() {
        super();
    }
    
    public InvalidUserAccessException(String message) {
        super(message);
    }
    
    public InvalidUserAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidUserAccessException(Throwable cause) {
        super(cause);
    }

}
