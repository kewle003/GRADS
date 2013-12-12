package edu.umn.csci5801.model.exception;

/**
 * This exception is thrown whenever an invalid
 * class is entered into GRADS.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidCourseException extends Exception {
    
    public InvalidCourseException(String message) {
        super(message);
    }
    
}
