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
                List<String> errMsg = new ArrayList<String>();
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
                if (credits >= REQUIRED_THESIS_CREDITS) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("Not enough thesis credits, 10 credits required");
                    result.setErrorMsgs(errMsg);
                }
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
                List<String> errMsg = new ArrayList<String>();
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
                if (credits >= REQUIRED_THESIS_CREDITS) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("Not enough thesis credits, 24 credits required");
                    result.setErrorMsgs(errMsg);
                }
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
                List<String> errMsg = new ArrayList<String>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().equals("csci8970") && isPassingGrade(course.getGrade()) ) {
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                if (!courses.isEmpty()) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("csci8970 has not been taken or has a passing grade");
                    result.setErrorMsgs(errMsg);
                }
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
                List<String> errMsg = new ArrayList<String>();
                int credits = 0;
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) && !inCSDepartment(course.getCourse()) && isGraduateLevel(course.getCourse()) ) {
                        courses.add(course);
                        credits += Integer.parseInt(course.getCourse().getNumCredits());
                    }
                }
                details.setCourses(courses);
                result.setDetails(details);
                if (credits >= 6) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("Not enough out of department credits taken");
                    result.setErrorMsgs(errMsg);
                }
                return result;
            }
        });
        
     // PHD INTRO_TO_RESEARCH
        programPHD.addRequirement(new CourseRequirement("INTRO_TO_RESEARCH") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                ArrayList<CourseTaken> courses = new ArrayList<CourseTaken>();
                List<String> errMsg = new ArrayList<String>();
                int csci8001Count = 0;
                int csci8002Count = 0;
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) && course.getCourse().getId().equals("csci8001")) {
                        courses.add(course);
                        csci8001Count++;
                    } else if (isPassingGrade(course.getGrade()) &&  course.getCourse().getId().equals("csci8002")) {
                        courses.add(course);
                        csci8002Count++;
                    }
                }
                details.setCourses(courses);
                result.setDetails(details);
                //csci8001 AND csci8002 have been taken
                if (csci8001Count >= 1) {
                    if (csci8002Count >= 1) {
                        result.setPassed(true);
                    } else {
                        result.setPassed(false);
                        errMsg.add("csci8002 has not been taken");
                    }
                } else if (csci8002Count >= 1) {
                    result.setPassed(false);
                    errMsg.add("csci8001 has not been taken");
                } else {
                    result.setPassed(false);
                    errMsg.add("csci8001 and csci8002 have not been taken");
                }
                return result;
            }
        });
        
        // MS Plan A COURSE_CREDITS
        programMSA.addRequirement(new CourseRequirement("COURSE_CREDITS") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
            	final int REQUIRED_TOTAL_CREDITS = 22;
            	final int REQUIRED_CSCI_CREDITS = 16;
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                ArrayList<CourseTaken> courses = new ArrayList<CourseTaken>();
                List<String> errMsg = new ArrayList<String>();
                int credits = 0;
                int creditsCS = 0;
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) ) {
                        courses.add(course);
                        credits += Integer.parseInt(course.getCourse().getNumCredits());
                        if ( inCSDepartment(course.getCourse()) ) {
                            creditsCS += Integer.parseInt(course.getCourse().getNumCredits());
                        }
                    }
                }
                details.setCourses(courses);
                result.setDetails(details);
                if (credits >= REQUIRED_TOTAL_CREDITS) {
                    if (credits >= REQUIRED_CSCI_CREDITS) {
                        result.setPassed(true);
                    } else {
                        result.setPassed(false);
                        errMsg.add("You do not meed the required credits for CS classes");
                        result.setErrorMsgs(errMsg);
                    }
                } else if (credits >= REQUIRED_CSCI_CREDITS) {
                    result.setPassed(false);
                    errMsg.add("You do not meed the required total credits");
                    result.setErrorMsgs(errMsg);
                } else {
                    result.setPassed(false);
                    errMsg.add("You do not meed the credit requirements");
                    result.setErrorMsgs(errMsg);
                }
                result.setPassed(credits >= REQUIRED_TOTAL_CREDITS && creditsCS >= REQUIRED_CSCI_CREDITS);                
                return result;
            }
        });
        
     // MS Plan B PLAN_B_PROJECT
        programMSB.addRequirement(new CourseRequirement("COURSE_CREDITS") {
            @Override
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                ArrayList<CourseTaken> courses = new ArrayList<CourseTaken>();
                List<String> errMsg = new ArrayList<String>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) && course.getCourse().getId().equals("csci8760") ) {
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                result.setDetails(details);
                if (!courses.isEmpty()) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You have not taken csci8760");
                    result.setErrorMsgs(errMsg);
                }
                return result;
            }
        });
        
        // PHD and MS Plan TOTAL_CREDITS
        CourseRequirement totalCreditWOThesis = new TotalCreditRequirement("TOTAL_CREDITS", Degree.MS_A);
        programMSA.addRequirement(totalCreditWOThesis);
        CourseRequirement totalCreditWO16CS = new TotalCreditRequirement("TOTAL_CREDITS", Degree.PHD);
        programPHD.addRequirement(totalCreditWO16CS);
        programMSB.addRequirement(totalCreditWO16CS);
        programMSC.addRequirement(totalCreditWO16CS);
        
        // Finally put programs into the programs HashMap
        programs.put(Degree.MS_A, programMSA);
        programs.put(Degree.MS_B, programMSB);
        programs.put(Degree.MS_C, programMSC);
        programs.put(Degree.PHD, programPHD);
    }
}
