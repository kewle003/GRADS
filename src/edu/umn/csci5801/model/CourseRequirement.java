package edu.umn.csci5801.model;

public abstract class CourseRequirement extends Requirement {

    public CourseRequirement(String name) {
        super(name);
    }

    public static boolean isPassingGrade(Grade grade) {
        return (grade==Grade.A) || (grade==Grade.B) || (grade==Grade.C) || (grade==Grade.S);
    }
}