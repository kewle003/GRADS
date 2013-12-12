package edu.umn.csci5801.model.exception;

/**
 * This exception is thrown whenever a JSON file provided to GRADS
 * has no data in that JSON file.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class NoDataFoundException extends Exception {
    
    public NoDataFoundException(String message) {
        super(message);
    }
}
