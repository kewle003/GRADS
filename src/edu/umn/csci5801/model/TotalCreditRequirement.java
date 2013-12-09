package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.List;

public class TotalCreditRequirement extends CourseRequirement {

	private static final int TOTAL_CREDITS = 31;
	private static final int CSCI_CREDITS = 16;
	private Degree degree;

	public TotalCreditRequirement(String name, Degree degree) {
        super(name);
        this.degree = degree;
    }

	@Override
	 public RequirementCheckResult metBy(StudentRecord studentRecord) {
        RequirementCheckResult result = new RequirementCheckResult(this.getName());
        CheckResultDetails details = new CheckResultDetails();
        List<String> errMsg = new ArrayList<String>();
        
        List<CourseTaken> coursesPHD = new ArrayList<CourseTaken>();
        List<CourseTaken> coursesMSA = new ArrayList<CourseTaken>();
        List<CourseTaken> coursesMSB = new ArrayList<CourseTaken>();
        List<CourseTaken> coursesMSC = new ArrayList<CourseTaken>();
        
        int creditsTotalPHD = 0;
        int creditsTotalMSA = 0;
        int creditsTotalMSB = 0;
        int creditsTotalMSC = 0;
        
        int creditsThesisPHD = 0; // Exclude from total
        int creditsThesisMSA = 0; // Exclude from total
        
        int creditsCSCI_PHD = 0; // Exclude from total
        int creditsCSCI_MSB = 0; // Exclude from total
        int creditsCSCI_MSC = 0; // Exclude from total
        
        for (CourseTaken course : studentRecord.getCoursesTaken() ) {
            if  ( isPassingGrade(course.getGrade()) ) {//Count Total Credits
            	creditsTotalPHD += Integer.parseInt(course.getCourse().getNumCredits());
            	creditsTotalMSA += Integer.parseInt(course.getCourse().getNumCredits());
            	creditsTotalMSB += Integer.parseInt(course.getCourse().getNumCredits());
            	creditsTotalMSC += Integer.parseInt(course.getCourse().getNumCredits());
            	
            	coursesMSB.add(course); // Add all courses to MSB
            	coursesMSC.add(course); // Add all courses to MSC
            	
                if (inCSDepartment(course.getCourse())) {//Count CSCI Credits
                	creditsCSCI_PHD += Integer.parseInt(course.getCourse().getNumCredits());
                	creditsCSCI_MSB += Integer.parseInt(course.getCourse().getNumCredits());
                	creditsCSCI_MSC += Integer.parseInt(course.getCourse().getNumCredits());
                }
                if (course.getCourse().getId().equals("csci8888")){//Count Thesis Credit for PHD
                	creditsThesisPHD += Integer.parseInt(course.getCourse().getNumCredits());
                } else {
                	coursesPHD.add(course); // Exclude Thesis courses
                }
                if (course.getCourse().getId().equals("csci8777")){//Count Thesis Credit for MSA
                	creditsThesisMSA += Integer.parseInt(course.getCourse().getNumCredits());
                } else {
                	coursesMSA.add(course); // Exclude Thesis courses
                }
            }
        }      
        
        if ( degree == Degree.PHD ) {
        	details.setCourses(coursesPHD);
            result.setDetails(details);
            if (creditsTotalPHD - creditsThesisPHD >= TOTAL_CREDITS) {
                if (creditsCSCI_PHD >= CSCI_CREDITS) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You do not meed the required credits for CS classes");
                    result.setErrorMsgs(errMsg);
                }
            } else if (creditsCSCI_PHD >= CSCI_CREDITS) {
                result.setPassed(false);
                errMsg.add("You do not meed the required total credits excluding Thesis ones");
                result.setErrorMsgs(errMsg);
            } else {
                result.setPassed(false);
                errMsg.add("You do not meed the credit requirements");
                result.setErrorMsgs(errMsg);
            }
        } else if (degree == Degree.MS_A) {
        	details.setCourses(coursesMSA);
            result.setDetails(details);
            if (creditsTotalMSA - creditsThesisMSA >= TOTAL_CREDITS) {
                result.setPassed(true);
            } else {
                result.setPassed(false);
                errMsg.add("You do not meed the required total credits excluding Thesis ones");
                result.setErrorMsgs(errMsg);
            }
        } else if (degree == Degree.MS_B) {
        	if (creditsTotalMSB >= TOTAL_CREDITS) {
                if (creditsCSCI_MSB >= CSCI_CREDITS) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You do not meed the required credits for CS classes");
                    result.setErrorMsgs(errMsg);
                }
            } else if (creditsCSCI_MSB >= CSCI_CREDITS) {
                result.setPassed(false);
                errMsg.add("You do not meed the required total credits");
                result.setErrorMsgs(errMsg);
            } else {
                result.setPassed(false);
                errMsg.add("You do not meed the credit requirements");
                result.setErrorMsgs(errMsg);
            }
        } else if (degree == Degree.MS_C) {
        	if (creditsTotalMSC >= TOTAL_CREDITS) {
                if (creditsCSCI_MSC >= CSCI_CREDITS) {
                    result.setPassed(true);
                } else {
                    result.setPassed(false);
                    errMsg.add("You do not meed the required credits for CS classes");
                    result.setErrorMsgs(errMsg);
                }
            } else if (creditsCSCI_MSC >= CSCI_CREDITS) {
                result.setPassed(false);
                errMsg.add("You do not meed the required total");
                result.setErrorMsgs(errMsg);
            } else {
                result.setPassed(false);
                errMsg.add("You do not meed the credit requirements");
                result.setErrorMsgs(errMsg);
            }
        }
		return result;   
    }
}
