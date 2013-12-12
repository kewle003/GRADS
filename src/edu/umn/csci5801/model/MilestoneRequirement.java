package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

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
        List<String> errorMsgs = new ArrayList<String>();
        boolean milestoneFound = false;
        for ( MilestoneSet milestoneSet : studentRecord.getMilestonesSet() ) {
            if ( milestoneSet.getMilestone().equals(milestone) ) {
                milestoneFound = true;
                break;
            }
        }
        if ( !milestoneFound ) {
            errorMsgs.add(milestone.name()+" has not yet been met");
        }
        result.setErrorMsgs(errorMsgs);
        result.setPassed(milestoneFound);
        
        return result;
    }

}
