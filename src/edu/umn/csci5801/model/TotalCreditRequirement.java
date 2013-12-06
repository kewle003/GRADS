package edu.umn.csci5801.model;

public class TotalCreditRequirement extends CourseRequirement {

	private Degree degree;

	public TotalCreditRequirement(String name, Degree degree) {
        super(name);
        this.degree = degree;
    }

	@Override
	public RequirementCheckResult metBy(StudentRecord studentRecord) {
		// TODO Auto-generated method stub
		return null;
	}

}
