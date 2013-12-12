package edu.umn.csci5801.model.exception;

/**
 * This exception is thrown whenever a user provides
 * invalid data to GRADS
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidDataException extends Exception {
    
    public InvalidDataException(String message) {
        super(message);
    }

}
