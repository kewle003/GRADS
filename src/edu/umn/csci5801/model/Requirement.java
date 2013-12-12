package edu.umn.csci5801.model;

public abstract class Requirement {
    private String name;
    
    /**
     * Construct a new instance, setting the name
     * @param name
     */
    public Requirement(String name) {
        this.name = name;
    }
    
    /**
     * Getter for name
     * @return name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Generate a RequirementCheckResult based on studentRecord
     * @param studentRecord
     * @return the RequirementCheckResult
     */
    public abstract RequirementCheckResult metBy(StudentRecord studentRecord);
}
