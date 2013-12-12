package edu.umn.csci5801.model;

/**
 * 
 * @author mark
 *
 */
public class GPC extends Person {
    private String id;
    
    public GPC(String firstName, String lastName, String id) {
        super(firstName, lastName);
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
}
