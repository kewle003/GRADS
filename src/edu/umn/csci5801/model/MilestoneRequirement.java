package edu.umn.csci5801.model;

public class MilestoneRequirement extends Requirement {
    private Milestone milestone;
    
    public MilestoneRequirement(String name, Milestone milestone) {
        super(name);
        this.milestone = milestone;
    }
    
    public Milestone getMilestone() {
        return this.milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }
    
    @Override
    public RequirementCheckResult metBy(StudentRecord studentRecord) {
        RequirementCheckResult result = new RequirementCheckResult(this.getName());
        result.setPassed(studentRecord.getMilestonesSet().contains(milestone));
        return result;
    }

}
