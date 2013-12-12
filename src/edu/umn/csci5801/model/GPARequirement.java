package edu.umn.csci5801.model;

import java.util.Iterator;
import java.util.List;


public abstract class GPARequirement extends Requirement {
    
    /**
     * Construct a new instance, setting the name
     * @param name
     */
    public GPARequirement(String name) {
        super(name);
    }

    // TODO add to design
    // returns CourseTaken with highest grade
    /**
     * Find the CourseTaken in courses with the highest grade, return and remove it
     * @param courses
     * @return the CourseTaken with the highest grade
     */
    public static CourseTaken popBestCourseTaken(List<CourseTaken> courses) {
        CourseTaken best = null;
        for (CourseTaken course: courses) {
            if ( best == null || course.getGrade().numericValue() > best.getGrade().numericValue() ) {
                best = course;
            }
        }
        courses.remove(best);
        return best;
    }
    
    //TODO: Add in System Crash handling
    /**
     * Calculates the GPA (Grade Point Average) of courses on a 4.0 scale
     * @param courses
     * @return the GPA
     */
    public static double calculateGPA(List<CourseTaken> courses) {
        double gradeSum = 0;
        int creditSum = 0;
        if (courses == null) {
            return 0;
        } else {
            Iterator<CourseTaken> courseIterator = courses.iterator();
            while (courseIterator.hasNext()) {
                CourseTaken course = courseIterator.next();
                
                if (course.getGrade().equals(Grade.S) || course.getGrade().equals(Grade.N) || course.getGrade().equals(Grade._)) {
                	continue;
                } else {                
                	gradeSum += (course.getGrade().numericValue() * ((int) Integer.parseInt(course.getCourse().getNumCredits())));
                	creditSum += (int) Integer.parseInt(course.getCourse().getNumCredits());
                }
            }
        }
        return (gradeSum/creditSum);
    }

}
