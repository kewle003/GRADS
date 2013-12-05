package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class GPARequirement extends Requirement {
    
    public GPARequirement(String name) {
        super(name);
    }

    // TODO add to design
    // returns CourseTaken with highest grade
    public static CourseTaken getBestCourseTaken(List<CourseTaken> courses) {
        CourseTaken best = null;
        for (CourseTaken course: courses) {
            if ( best == null || course.getGrade().numericValue() > best.getGrade().numericValue() ) {
                best = course;
            }
        }
        return best;
    }
    
    //TODO: Add in System Crash handling
    public static double calculateGPA(List<CourseTaken> courses) {
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

}
