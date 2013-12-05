package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressSummaryBuilder {
    private List<Requirement> requirements;
    private HashMap<Degree,Program> programs;
    
    public ProgressSummaryBuilder() {
        this.requirements = new ArrayList<Requirement>();
        this.programs = new HashMap<Degree,Program>();
        
        initializePrograms();
    }
    
    public ProgressSummary generateProgressSummary(StudentRecord studentRecord) {
        ProgressSummary progressSummary = new ProgressSummary();
        progressSummary.setStudent(studentRecord.getStudent());
        progressSummary.setDepartment(studentRecord.getDepartment());
        progressSummary.setDegreeSought(studentRecord.getDegreeSought());
        progressSummary.setTermBegan(studentRecord.getTermBegan());
        progressSummary.setAdvisors(studentRecord.getAdvisors());
        progressSummary.setCommittee(studentRecord.getCommittee());
        progressSummary.setNotes(studentRecord.getNotes());
        
        List<Requirement> requirements = this.programs.get(studentRecord.getDegreeSought()).getRequirements();
        for (Requirement requirement: requirements) {
            progressSummary.addRequirementResult(requirement.metBy(studentRecord));
        }
        
        return progressSummary;
    }
    
    public List<Program> getPrograms() {
        List<Program> list = new ArrayList<Program>();
        list.addAll(this.programs.values());
        return list;
    }
    
    public void addProgram(Program program) {
        this.programs.put(program.getDegree(), program);
    }
    
    public List<Requirement> getRequirements() {
        return this.requirements;
    }
    
    public void addRequirement(Requirement requirement) {
        this.requirements.add(requirement);
    }
    
    private void initializePrograms() {
        Program programMSA = new Program(Degree.MS_A);
        Program programMSB = new Program(Degree.MS_B);
        Program programMSC = new Program(Degree.MS_C);
        Program programPHD = new Program(Degree.PHD);
        
        GPARequirement msBreadthRequirement = new GPARequirement("BREADTH_REQUIREMENT_MS") {
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
                    breadthCourses.add(getBestCourseTaken(theory));
                }
                if ( !architecture.isEmpty() ) {
                    breadthCourses.add(getBestCourseTaken(architecture));
                }
                if ( !applications.isEmpty() ) {
                    breadthCourses.add(getBestCourseTaken(applications));
                }
                double breadthGPA = calculateGPA(breadthCourses);
                details.setGPA((float)breadthGPA);
                details.setCourses(breadthCourses);
                if ( breadthCourses.size() < 3 || breadthGPA < 3.25 ) {
                    result.setPassed(false);
                } else {
                    result.setPassed(true);
                }
                result.setDetails(details);
                return result;
            }
        };
        programMSA.addRequirement(msBreadthRequirement);
        programMSB.addRequirement(msBreadthRequirement);
        programMSC.addRequirement(msBreadthRequirement);
    }
}
