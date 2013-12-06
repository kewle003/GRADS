package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressSummaryBuilder {
    private List<Requirement> requirements;
    private HashMap<Degree,Program> programs;
    
    public ProgressSummaryBuilder() {
        this.requirements = new ArrayList<Requirement>();
        this.programs = new HashMap<Degree,Program>();
        
        initializePrograms();
    }
    
    public ProgressSummary generateProgressSummary(StudentRecord studentRecord) {
        ProgressSummary progressSummary = new ProgressSummary();
        progressSummary.setStudent(studentRecord.getStudent());
        progressSummary.setDepartment(studentRecord.getDepartment());
        progressSummary.setDegreeSought(studentRecord.getDegreeSought());
        progressSummary.setTermBegan(studentRecord.getTermBegan());
        progressSummary.setAdvisors(studentRecord.getAdvisors());
        progressSummary.setCommittee(studentRecord.getCommittee());
        progressSummary.setNotes(studentRecord.getNotes());
        
        List<Requirement> requirements = this.programs.get(studentRecord.getDegreeSought()).getRequirements();
        for (Requirement requirement: requirements) {
            progressSummary.addRequirementResult(requirement.metBy(studentRecord));
        }
        
        return progressSummary;
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
    
    private void initializePrograms() {
        Program programMSA = new Program(Degree.MS_A);
        Program programMSB = new Program(Degree.MS_B);
        Program programMSC = new Program(Degree.MS_C);
        Program programPHD = new Program(Degree.PHD);

        // BREADTH REQUIREMENTS
        GPARequirement msBreadthRequirement = new BreadthRequirement("BREADTH_REQUIREMENT_MS", Degree.MS_A);
        // note the above constructor does not differential between different MS Degrees
        programMSA.addRequirement(msBreadthRequirement);
        programMSB.addRequirement(msBreadthRequirement);
        programMSC.addRequirement(msBreadthRequirement);
        GPARequirement phdBreadthRequirement = new BreadthRequirement("BREADTH_REQUIREMENT_PHD", Degree.PHD);
        programMSC.addRequirement(phdBreadthRequirement);
    }
}
