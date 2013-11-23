package edu.umn.csci5801.model;

public abstract class Requirement {
    private String name;
    
    // TODO constructor
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) { // TODO fix diagram to reflect this parameter
        this.name = name;
    }
    
    public abstract RequirementCheckResult metBy(StudentRecord studentRecord);
}
