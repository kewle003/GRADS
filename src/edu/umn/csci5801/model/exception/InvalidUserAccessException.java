package edu.umn.csci5801.model.exception;

/**
 * This exception is used for when a user is trying
 * to access data from GRADS and they do not
 * have the permission to do so.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidUserAccessException extends Exception {
    
    public InvalidUserAccessException(String message) {
        super(message);
    }

}
