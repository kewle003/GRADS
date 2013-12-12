package edu.umn.csci5801.model;

public abstract class CourseRequirement extends Requirement {

    /**
     * Construct a new instance, setting the name
     * @param name
     */
    public CourseRequirement(String name) {
        super(name);
    }

    /**
     * A passing grade is one of the following: A,B,C,S
     * @param grade
     * @return whether or not grade is a passing Grade
     */
    public static boolean isPassingGrade(Grade grade) {
        return (grade==Grade.A) || (grade==Grade.B) || (grade==Grade.C) || (grade==Grade.S);
    }

    /**
     * An A-F grade is one of the following: A,B,C,D,F
     * @param grade
     * @return whether or not the grade is A-F
     */
    public static boolean isAFGrade(Grade grade) {
        return (grade==Grade.A) || (grade==Grade.B) || (grade==Grade.C) || (grade==Grade.D) || (grade==Grade.F);
    }
    
    /**
     * A CS course's id starts with "csci"
     * @param course
     * @return whether or not a course is a CS course
     */
    public static boolean inCSDepartment(Course course) {
        return course.getId().matches("csci.*");
    }
    
    /**
     * A graduate level course's id number (last four digits) is in range [5000,8999] (There are no 9000-level courses)
     * @param course
     * @return whether or not a course is graduate level course
     */
    public static boolean isGraduateLevel(Course course) {
        return course.getId().matches(".*[5-8][0-9][0-9][0-9]");
    }
}
