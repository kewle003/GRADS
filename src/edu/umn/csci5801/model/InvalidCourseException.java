package edu.umn.csci5801.model;

/**
 * This exception is thrown whenever an invalid
 * class is entered into GRADS.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidCourseException extends Exception {
    
    public InvalidCourseException() {
        super();
    }
    
    public InvalidCourseException(String message) {
        super(message);
    }
    
    public InvalidCourseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public InvalidCourseException(Throwable cause) {
        super(cause);
    }
}
