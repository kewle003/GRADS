package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressSummaryBuilder {
    private List<Requirement> requirements;
    private HashMap<Degree,Program> programs;
    private List<Course> courses; // TODO add to Design doc
    
    public ProgressSummaryBuilder(List<Course> courses) {
        this.courses = courses;
        this.requirements = new ArrayList<Requirement>();
        this.programs = new HashMap<Degree,Program>();
    }
    
    public ProgressSummary generateProgressSummary(StudentRecord studentRecord) {
        return null;
    }
    
    public List<Program> getPrograms() {
        List<Program> list = new ArrayList<Program>();
        list.addAll(this.programs.values());
        return list;
    }
    
    public void addProgram(Program program) {
        this.programs.put(program.getDegree(), program);
    }
    
    public List<Requirement> getRequirements() {
        return this.requirements;
    }
    
    public void addRequirement(Requirement requirement) {
        this.requirements.add(requirement);
    }
}
