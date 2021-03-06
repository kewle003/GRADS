package edu.umn.csci5801.model.exception;

/**
 * Exception for when the JSON database holds 
 * invalid user role data. This is used to let
 * the database manager know that his database is invalid.
 * @author mark
 *
 */
@SuppressWarnings("serial")
public class InvalidUserRoleException extends Exception {

    public InvalidUserRoleException(String message) {
        super(message);
    }

}
