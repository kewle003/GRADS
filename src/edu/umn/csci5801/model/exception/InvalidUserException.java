package edu.umn.csci5801.model.exception;

/**
 * Exception for when a user logs on to GRADS and their
 * corresponding userId is not in the JSON database.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidUserException extends Exception {
    
    public InvalidUserException() {
        super();
    }
    
    public InvalidUserException(String message) {
        super(message);
    }
    
    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidUserException(Throwable cause) {
        super(cause);
    }
}
