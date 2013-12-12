package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

public class Program {
    private Degree degree;
    private List<Requirement> requirements;
    
    public Program(Degree degree) {
        this.degree = degree;
        this.requirements = new ArrayList<Requirement>();
    }
    
    public Degree getDegree() {
        return this.degree;
    }
    
    public List<Requirement> getRequirements() {
        return requirements;
    }
    
    public void addRequirement(Requirement requirement) {
        this.requirements.add(requirement);
    }
}
