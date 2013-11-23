package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GPARequirement extends Requirement {
    //Overall GPA, Breadth GPA, In-program GPA
    private String name;
    private Program program;

    public GPARequirement(String name, Program program) {
        super(name);
        this.program = program;
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
        List<CourseTaken> courses = studentRecord.getCoursesTaken();
        //Do some logic to fetch courses
        if (name.equals("Breadth-GPA")) {
            //Loop through courses extracting only BREADTH
            if(program.equals(Degree.PHD)) {
                if (3.2 <= calculateGPA(courses)) {
                    requirement.setPassed(true);
                } else {
                    requirement.setPassed(false);
                    List<String> errMessage = new ArrayList<String>();
                    errMessage.add("Breadth-GPA is not high enough");
                    requirement.setErrorMsgs(errMessage);
                }
            } else {
                if (3.0 <= calculateGPA(courses)) {
                    requirement.setPassed(true);
                } else {
                    requirement.setPassed(false);
                }
            }
        } else if (name.equals("In-Program-GPA")) {
            //Loop through courses extracting only IN-PROGRAM-GPA
            if (3.0 <= calculateGPA(courses)) {
                requirement.setPassed(true);
            } else {
                requirement.setPassed(false);
            }
        } else if (name.equals("Overall-GPA")) {
            if (3.0 <= calculateGPA(courses)) {
                requirement.setPassed(true);
            } else {
                requirement.setPassed(false);
            }
        }
        return requirement;
    }

}
