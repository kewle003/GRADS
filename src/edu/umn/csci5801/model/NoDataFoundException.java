package edu.umn.csci5801.model;

/**
 * This exception is thrown whenever a JSON file provided to GRADS
 * has no data in that JSON file.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class NoDataFoundException extends Exception {
    public NoDataFoundException() {
        super();
    }
    
    public NoDataFoundException(String message) {
        super(message);
    }
    
    public NoDataFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public NoDataFoundException(Throwable cause) {
        super(cause);
    }
}
