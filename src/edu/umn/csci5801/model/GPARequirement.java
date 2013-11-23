package edu.umn.csci5801.model;

import java.util.Iterator;
import java.util.List;


public class GPARequirement extends Requirement {
    //Overall GPA, Breadth GPA, In-program GPA
    private String name;
    private CourseArea area;

    public GPARequirement(String name, CourseArea area) {
        super(name);
        this.area = area;
    }
    
    //TODO: Add in System Crash handling
    public double calculateGPA(List<CourseTaken> courses) {
        double gradeSum = 0;
        int creditSum = 0;
        if (courses == null) {
            return 0;
        } else {
            Iterator<CourseTaken> courseIterator = courses.iterator();
            while (courseIterator.hasNext()) {
                CourseTaken course = courseIterator.next();
                gradeSum += course.getGrade().numericValue();
                creditSum += (int) Integer.parseInt(course.getCourse().getNumCredits());
            }
        }
        return (gradeSum/creditSum);
    }

    @Override
    public RequirementCheckResult metBy(StudentRecord studentRecord) {
        RequirementCheckResult requirement = new RequirementCheckResult(name);
        
        return null;
    }

}
