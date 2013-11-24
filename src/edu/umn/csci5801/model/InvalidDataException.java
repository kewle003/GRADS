package edu.umn.csci5801.model;

/**
 * This exception is thrown whenever a user provides
 * invalid data to GRADS
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidDataException extends Exception {
    
    public InvalidDataException() {
        super();
    }
    
    public InvalidDataException(String message) {
        super(message);
    }
    
    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidDataException(Throwable cause) {
        super(cause);
    }
}
