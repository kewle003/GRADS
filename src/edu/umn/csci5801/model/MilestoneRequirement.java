package edu.umn.csci5801.model;

public class MilestoneRequirement extends Requirement {
    private Milestone milestone;
    
    /**
     * Construct a new instance, setting instance variables to parameters
     * @param name
     * @param milestone
     */
    public MilestoneRequirement(String name, Milestone milestone) {
        super(name);
        this.milestone = milestone;
    }
    
    /**
     * Getter for milestone
     * @return milestone
     */
    public Milestone getMilestone() {
        return this.milestone;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override // Refer to Requirement.metBy
    public RequirementCheckResult metBy(StudentRecord studentRecord) {
        RequirementCheckResult result = new RequirementCheckResult(this.getName());
        result.setPassed(studentRecord.getMilestonesSet().contains(milestone));
        return result;
    }

}
