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
    
    // This function initializes and thus defines the requirements for each program
    // In order to change graduation requirements, changes must be made here.
    private void initializePrograms() {
        Program programMSA = new Program(Degree.MS_A);
        Program programMSB = new Program(Degree.MS_B);
        Program programMSC = new Program(Degree.MS_C);
        Program programPHD = new Program(Degree.PHD);

        // BREADTH REQUIREMENTS
        GPARequirement msBreadthRequirement = new BreadthRequirement("BREADTH_REQUIREMENT_MS", Degree.MS_A);
        // note the above constructor does not differential between different MS Degrees
        programMSA.addRequirement(msBreadthRequirement);
        programMSB.addRequirement(msBreadthRequirement);
        programMSC.addRequirement(msBreadthRequirement);
        GPARequirement phdBreadthRequirement = new BreadthRequirement("BREADTH_REQUIREMENT_PHD", Degree.PHD);
        programPHD.addRequirement(phdBreadthRequirement);
        
        // THESIS REQUIREMENTS
        CourseRequirement msThesisRequirement = new CourseRequirement("THESIS_MS") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                int credits = 0;
                final int REQUIRED_THESIS_CREDITS = 10;
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().equals("csci8777") && isPassingGrade(course.getGrade()) ) {
                        credits += Integer.parseInt(course.getCourse().getNumCredits());
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                result.setPassed(credits >= REQUIRED_THESIS_CREDITS);
                result.setDetails(details);
                return result;
            }            
        };
        programMSA.addRequirement(msThesisRequirement);
        CourseRequirement phdThesisRequirement = new CourseRequirement("THESIS_PHD") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                int credits = 0;
                final int REQUIRED_THESIS_CREDITS = 24;
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().equals("csci8888") && isPassingGrade(course.getGrade()) ) {
                        credits += Integer.parseInt(course.getCourse().getNumCredits());
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                result.setPassed(credits >= REQUIRED_THESIS_CREDITS);
                result.setDetails(details);
                return result;
            }            
        };
        programPHD.addRequirement(phdThesisRequirement);
        
        // COLLOQUIUM
        CourseRequirement colloquiumRequirement = new CourseRequirement("COLLOQUIUM") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().equals("csci8970") && isPassingGrade(course.getGrade()) ) {
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                result.setPassed(!courses.isEmpty());
                result.setDetails(details);
                return result;
            }            
        };
        programMSA.addRequirement(colloquiumRequirement);
        programMSB.addRequirement(colloquiumRequirement);
        programMSC.addRequirement(colloquiumRequirement);
        programPHD.addRequirement(colloquiumRequirement);
        
        // MILESTONE REQUIREMENTS
        Milestone[] phdMilestones = {
                Milestone.PRELIM_COMMITTEE_APPOINTED,
                Milestone.WRITTEN_PE_SUBMITTED,
                Milestone.WRITTEN_PE_APPROVED,
                Milestone.ORAL_PE_PASSED,
                Milestone.DPF_SUBMITTED,
                Milestone.DPF_APPROVED,
                Milestone.THESIS_COMMITTEE_APPOINTED,
                Milestone.PROPOSAL_PASSED,
                Milestone.GRADUATION_PACKET_REQUESTED,
                Milestone.THESIS_SUBMITTED,
                Milestone.THESIS_APPROVED,
                Milestone.DEFENSE_PASSED
                };
        Milestone[] msaMilestones = {
                Milestone.DPF_SUBMITTED,
                Milestone.DPF_APPROVED,
                Milestone.THESIS_COMMITTEE_APPOINTED,
                Milestone.GRADUATION_PACKET_REQUESTED,
                Milestone.THESIS_SUBMITTED,
                Milestone.THESIS_APPROVED,
                Milestone.DEFENSE_PASSED
                };
        Milestone[] msbMilestones = {
                Milestone.DPF_SUBMITTED,
                Milestone.DPF_APPROVED,
                Milestone.PROJECT_COMMITTEE_APPOINTED,
                Milestone.GRADUATION_PACKET_REQUESTED,
                Milestone.DEFENSE_PASSED
                };
        Milestone[] mscMilestones = {
                Milestone.DPF_SUBMITTED,
                Milestone.DPF_APPROVED,
                Milestone.TRACKING_FORM_SUBMITTED,
                Milestone.TRACKING_FORM_APPROVED,
                Milestone.GRADUATION_PACKET_REQUESTED
                };
        for (Milestone milestone: msaMilestones) {
            programMSA.addRequirement(new MilestoneRequirement(milestone.name(), milestone));
        }
        for (Milestone milestone: msbMilestones) {
            programMSB.addRequirement(new MilestoneRequirement(milestone.name(), milestone));
        }
        for (Milestone milestone: mscMilestones) {
            programMSC.addRequirement(new MilestoneRequirement(milestone.name(), milestone));
        }
        for (Milestone milestone: phdMilestones) {
            programPHD.addRequirement(new MilestoneRequirement(milestone.name(), milestone));
        }
        
        // PHD OUT_OF_DEPARTMENT
        programPHD.addRequirement(new CourseRequirement("OUT_OF_DEPARTMENT") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                ArrayList<CourseTaken> courses = new ArrayList<CourseTaken>();
                int credits = 0;
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) && inCSDepartment(course.getCourse()) && isGraduateLevel(course.getCourse()) ) {
                        courses.add(course);
                        credits += Integer.parseInt(course.getCourse().getNumCredits());
                    }
                }
                details.setCourses(courses);
                result.setDetails(details);
                result.setPassed(credits >= 6);
                return result;
            }
        });
        
        // Finally put programs into the programs HashMap
        programs.put(Degree.MS_A, programMSA);
        programs.put(Degree.MS_B, programMSB);
        programs.put(Degree.MS_C, programMSC);
        programs.put(Degree.PHD, programPHD);
    }
}
