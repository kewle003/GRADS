package edu.umn.csci5801.model;

import java.util.ArrayList;

public class BreadthRequirement extends GPARequirement {

    private Degree degree;
    private static double PHD_BREADTH_GPA = 3.45;
    private static double MS_BREADTH_GPA = 3.25;
    
    /**
     * Construct a new instance, setting instance variables to parameters
     * @param name
     * @param degree
     */
    public BreadthRequirement(String name, Degree degree) {
        super(name);
        this.degree = degree;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RequirementCheckResult metBy(StudentRecord studentRecord) {
        RequirementCheckResult result = new RequirementCheckResult(this.getName());
        CheckResultDetails details = new CheckResultDetails();
        
        ArrayList<CourseTaken> theory = new ArrayList<CourseTaken>();
        ArrayList<CourseTaken> architecture = new ArrayList<CourseTaken>();
        ArrayList<CourseTaken> applications = new ArrayList<CourseTaken>();
        ArrayList<CourseTaken> breadthCourses = new ArrayList<CourseTaken>();
        
        for (CourseTaken course : studentRecord.getCoursesTaken() ) {
            if ( course.getGrade() != Grade.S && course.getGrade() != Grade.N && course.getGrade() != Grade._ ) {
                CourseArea area = course.getCourse().getCourseArea();
                switch (area) {
                case THEORY_ALGORITHMS:
                    theory.add(course);
                    break;
                case ARCHITECTURE_SYSTEMS_SOFTWARE:
                    architecture.add(course);
                    break;
                case APPLICATIONS:
                    applications.add(course);
                    break;
                default:
                    break;
                }
            }
        }
        
        if ( !theory.isEmpty() ) {
            breadthCourses.add(popBestCourseTaken(theory));
        }
        if ( !architecture.isEmpty() ) {
            breadthCourses.add(popBestCourseTaken(architecture));
        }
        if ( !applications.isEmpty() ) {
            breadthCourses.add(popBestCourseTaken(applications));
        }
        
        if ( degree == Degree.PHD ) {
            // merge all remaining breadth courses
            ArrayList<CourseTaken> remainingCourses = new ArrayList<CourseTaken>();
            remainingCourses.addAll(theory);
            remainingCourses.addAll(architecture);
            remainingCourses.addAll(applications);
            // get best two courses if possible
            for ( int i = 0 ;i < 2; i++ ) {
                if ( !remainingCourses.isEmpty() ) {
                    breadthCourses.add(popBestCourseTaken(remainingCourses));
                }
            }
        }
        
        double breadthGPA = calculateGPA(breadthCourses);
        details.setGPA((float)breadthGPA);
        details.setCourses(breadthCourses);
        
        if ( degree == Degree.PHD ) {
            result.setPassed(!( breadthCourses.size() < 5 || breadthGPA < PHD_BREADTH_GPA ));
        } else {
            result.setPassed(!( breadthCourses.size() < 3 || breadthGPA < MS_BREADTH_GPA ));
        }
        result.setDetails(details);
        return result;
    }

}
