package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressSummaryBuilder {
    private HashMap<Degree,Program> programs;
    
    /**
     * Constructor initializing list of programs and their requirements
     */
    public ProgressSummaryBuilder() {
        this.programs = new HashMap<Degree,Program>();
        initializePrograms();
    }
    
    /**
     * Populates the ProgressSummary with studentRecord's basic information and traverses Requirements of the Program corresponding to the studentRecord's degreeSought to generate RequirementCheckResults 
     * @param studentRecord the StudentRecord from which the ProgressSummary is to be generated
     * @return the ProgressSummary
     */
    public ProgressSummary generateProgressSummary(StudentRecord studentRecord) {
        //If the current student does not have a declared degree return null
        if (studentRecord.getDegreeSought() == null) {
            return null;
        }
        
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
    
    /**
     * getter for programs
     * @return the list of all Programs
     */
    public List<Program> getPrograms() {
        List<Program> list = new ArrayList<Program>();
        list.addAll(this.programs.values());
        return list;
    }
    
    /**
     * Add a Program to programs
     * @param program the Program to be added
     */
    public void addProgram(Program program) {
        this.programs.put(program.getDegree(), program);
    }
    
    /**
     * Used to make OVERALL_GPA_*, IN_PROGRAM_GPA_*
     * @param minGPA minimum GPA required for graduation
     * @param name name of requirement
     * @param csgradonly if true, will only include courses CSCI 5000+
     * @return the Requirement
     */
    private static Requirement makeComprehensiveGPARequirement(final double minGPA, String name, final boolean csgradonly) {
        return new GPARequirement(name) {
            @Override // Refer to Requirement.metBy
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                List<String> errorMsgs = new ArrayList<String>();
                
                for (CourseTaken course : studentRecord.getCoursesTaken()) {
                    if (!csgradonly
                            || (course.getCourse().getId().matches("csci.*") && course
                                    .getCourse().getId()
                                    .matches(".*[5-8][0-9][0-9][0-9]"))) {
                        courses.add(course);
                    }
                }
                
                double calculatedGPA = calculateGPA(courses);
                details.setGPA((float) calculatedGPA);
                details.setCourses(courses);
                
                result.setDetails(details);
                boolean passed = calculatedGPA >= minGPA;
                result.setPassed(passed);
                if ( !passed ) {
                    errorMsgs.add("GPA is less than required GPA");
                }
                result.setErrorMsgs(errorMsgs);
                
                return result;
            }            
        };
    }

    /**
     * Used to make TOTAL_CREDITS and COURSE_CREDITS
     * @param minTotalCredits credits required (of any kind)
     * @param minCsciCredits credits required to be CSCI5000+ classes
     * @param includeThesis include thesis credits
     * @param mustBeAF require all csci courses to be taken A-F
     * @param name name of requirement
     * @return the Requirement
     */
    private static Requirement makeComprehensiveCreditRequirement(final int minTotalCredits, final int minCsciCredits, final boolean includeThesis, final boolean mustBeAF, String name) {
        return new CourseRequirement(name) {
            @Override // Refer to Requirement.metBy 
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> allCourses = new ArrayList<CourseTaken>();
                List<String> errorMsgs = new ArrayList<String>();
                
                int allCredits = 0;
                int csciCredits = 0;
                
                for (CourseTaken course : studentRecord.getCoursesTaken()) {
                    if ( includeThesis || !(course.getCourse().getId().equals("csci8888") || course.getCourse().getId().equals("8777")) ) {
                        allCourses.add(course);
                        int credit = Integer.parseInt(course.getCourse().getNumCredits());
                        allCredits += credit;
                        if (inCSDepartment(course.getCourse())
                                && isGraduateLevel(course.getCourse())
                                && (!mustBeAF || isAFGrade(course.getGrade()))) {
                            csciCredits += credit;
                        }
                    }
                }
                
                details.setCourses(allCourses);
                
                result.setDetails(details);
                if ( csciCredits < minCsciCredits ) {
                    errorMsgs.add("Not enough CSCI credits.");
                }
                if ( allCredits < minTotalCredits ) {
                    errorMsgs.add("Not enough total credits");
                }
                result.setPassed(errorMsgs.isEmpty());
                result.setErrorMsgs(errorMsgs);
                
                return result;
            }
        };
    }
    
    /**
     * Used to make BREADTH_REQUIREMENT_*
     * @param name the name of the Requirement
     * @param degree used to determine specifics of the requirement
     * @return the Requirement
     */
    private static Requirement makeBreadthRequirement(String name, final Degree degree) {
        return new GPARequirement(name) {
            private double PHD_BREADTH_GPA = 3.45;
            private double MS_BREADTH_GPA = 3.25;
            
            @Override // Refer to Requirement.metBy
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<String> errorMsgs = new ArrayList<String>();
                
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
                
                int minBreadthCourses;
                double minBreadthGPA;
                if ( degree == Degree.PHD ) {
                    minBreadthCourses = 5;
                    minBreadthGPA = PHD_BREADTH_GPA;
                } else {
                    minBreadthCourses = 3;
                    minBreadthGPA = MS_BREADTH_GPA;
                }
                
                if ( breadthCourses.size() < minBreadthCourses ) {
                    errorMsgs.add("Not enough courses to meet Breadth Requirement");
                }
                if ( breadthGPA < minBreadthGPA ) {
                    errorMsgs.add("Insufficient GPA to meet Breadth Requirement");
                }
                
                result.setPassed(errorMsgs.isEmpty());
                result.setErrorMsgs(errorMsgs);
                
                result.setDetails(details);
                return result;
            }
        };
    }
    
    /**
     * Initializes and thus defines the requirements for each program. In order to change graduation requirements, changes must be made here.
     */
    private void initializePrograms() {
        Program programMSA = new Program(Degree.MS_A);
        Program programMSB = new Program(Degree.MS_B);
        Program programMSC = new Program(Degree.MS_C);
        Program programPHD = new Program(Degree.PHD);

        // BREADTH REQUIREMENTS
        Requirement msBreadthRequirement = makeBreadthRequirement("BREADTH_REQUIREMENT_MS", Degree.MS_A);
        // note the above constructor does not differential between different MS Degrees
        programMSA.addRequirement(msBreadthRequirement);
        programMSB.addRequirement(msBreadthRequirement);
        programMSC.addRequirement(msBreadthRequirement);
        Requirement phdBreadthRequirement = makeBreadthRequirement("BREADTH_REQUIREMENT_PHD", Degree.PHD);
        programPHD.addRequirement(phdBreadthRequirement);
        
        // THESIS REQUIREMENTS
        CourseRequirement msThesisRequirement = new CourseRequirement("THESIS_MS") {
            @Override // Refer to Requirement.metBy
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
            @Override // Refer to Requirement.metBy
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
            @Override // Refer to Requirement.metBy
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
        
        // PHD OUT_OF_DEPARTMENT
        programPHD.addRequirement(new CourseRequirement("OUT_OF_DEPARTMENT") {
            @Override // Refer to Requirement.metBy
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
            @Override // Refer to Requirement.metBy
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
                result.setErrorMsgs(errMsg);
                return result;
            }
        });
        
        // MS Plan B PLAN_B_PROJECT
        programMSB.addRequirement(new CourseRequirement("COURSE_CREDITS") {
            @Override // Refer to Requirement.metBy
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                ArrayList<CourseTaken> courses = new ArrayList<CourseTaken>();
                List<String> errMsg = new ArrayList<String>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( isPassingGrade(course.getGrade()) && isAFGrade(course.getGrade()) && course.getCourse().getId().equals("csci8760") ) {
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
        
        // PHD and MS Plan TOTAL_CREDITS/COURSE_CREDITS
        Requirement phdTotalCreditRequirement = makeComprehensiveCreditRequirement(31, 16, false, false, "TOTAL_CREDITS");
        programPHD.addRequirement(phdTotalCreditRequirement);
        Requirement msaTotalCreditRequirement = makeComprehensiveCreditRequirement(31, 0, true, true, "TOTAL_CREDITS");
        programMSA.addRequirement(msaTotalCreditRequirement);
        Requirement msaCourseCreditRequirement = makeComprehensiveCreditRequirement(22, 16, false, true, "COURSE_CREDITS");
        programMSA.addRequirement(msaCourseCreditRequirement);
        Requirement msbcTotalCreditRequirement = makeComprehensiveCreditRequirement(31, 16, false, true, "TOTAL_CREDITS");
        programMSB.addRequirement(msbcTotalCreditRequirement);
        programMSC.addRequirement(msbcTotalCreditRequirement);
        
        // PHD_LEVEL_COURSES REQUIREMENTS
        CourseRequirement phdLevelCourses = new CourseRequirement("PHD_LEVEL_COURSES") {
            @Override // Refer to Requirement.metBy
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                final int REQUIRED_CREDITS = 3;
                List<String> errMsg = new ArrayList<String>();
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().startsWith("csci8") && isPassingGrade(course.getGrade()) && isAFGrade(course.getGrade()) && Integer.parseInt(course.getCourse().getNumCredits()) >= REQUIRED_CREDITS) {
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                if (!courses.isEmpty() ) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You must have at least one csci 8000 level course that has 3 credits");
                    result.setErrorMsgs(errMsg);
                }
                result.setDetails(details);
                return result;
            }            
        };
        programMSA.addRequirement(phdLevelCourses);
        programMSB.addRequirement(phdLevelCourses);
        
        CourseRequirement phdLevelCoursesPlanC = new CourseRequirement("PHD_LEVEL_COURSES_PLANC") {
            @Override // Refer to Requirement.metBy
            public RequirementCheckResult metBy(StudentRecord studentRecord) {
                final int REQUIRED_CREDITS = 3;
                List<String> errMsg = new ArrayList<String>();
                RequirementCheckResult result = new RequirementCheckResult(this.getName());
                CheckResultDetails details = new CheckResultDetails();
                List<CourseTaken> courses = new ArrayList<CourseTaken>();
                for (CourseTaken course: studentRecord.getCoursesTaken()) {
                    if ( course.getCourse().getId().startsWith("csci8") && isPassingGrade(course.getGrade()) && isAFGrade(course.getGrade()) && Integer.parseInt(course.getCourse().getNumCredits()) >= REQUIRED_CREDITS) {
                        courses.add(course);
                    }
                }
                details.setCourses(courses);
                if ( courses.size() >= 2 ) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You must have at least two csci 8000 level course that have 3 credits each");
                    result.setErrorMsgs(errMsg);
                }
                result.setDetails(details);
                return result;
            }            
        };
        programMSC.addRequirement(phdLevelCoursesPlanC);
        
        // OTHER GPA Requirements
        programPHD.addRequirement(makeComprehensiveGPARequirement(3.45, "OVERALL_GPA_PHD", false));
        programPHD.addRequirement(makeComprehensiveGPARequirement(3.45, "IN_PROGRAM_GPA_PHD", true));
        Requirement msOverallGPARequirement = makeComprehensiveGPARequirement(3.25, "OVERALL_GPA_MS", false);
        Requirement msInProgramGPARequirement = makeComprehensiveGPARequirement(3.25, "IN_PROGRAM_GPA_MS", true);
        programMSA.addRequirement(msOverallGPARequirement);
        programMSA.addRequirement(msInProgramGPARequirement);
        programMSB.addRequirement(msOverallGPARequirement);
        programMSB.addRequirement(msInProgramGPARequirement);
        programMSC.addRequirement(msOverallGPARequirement);
        programMSC.addRequirement(msInProgramGPARequirement);
        
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
        
        // Finally put programs into the programs HashMap
        programs.put(Degree.MS_A, programMSA);
        programs.put(Degree.MS_B, programMSB);
        programs.put(Degree.MS_C, programMSC);
        programs.put(Degree.PHD, programPHD);
    }
}
