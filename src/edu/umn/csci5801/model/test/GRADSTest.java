package edu.umn.csci5801.model.test;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.umn.csci5801.model.GRADS;
import edu.umn.csci5801.model.JSONHandler;
import edu.umn.csci5801.model.ProgressSummary;
import junit.framework.TestCase;

/**
 * System testing
 * @author mark
 *
 */
//TODO: Black box tests; Environment A, B, and C
//Create our own JSON files
//Create our own StudentRecords and ProgressSummaries for each person
//We also need a cleanUp that resets the JSON database
public class GRADSTest extends TestCase {
    private File tempUserData;
    private File tempStudentData;
    private GRADS g;
    private JSONHandler tempUserDatabase;
    private JSONHandler tempStudentDatabase;
    private ProgressSummary albertsStudentSummary;
    
    @Override
    public void setUp() {
        tempUserData = new File("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envUsers.txt");
        tempStudentData = new File("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envStudentRecords.txt");
        g = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/users.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/students.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/courses.txt");
    }
    
    /**
     * The purpose of the Test Case is to check that 
     * when a user requests for a student summary, 
     * the student summary will be retrieved.
     */
    @Test
    public void testStudentSummaryProvision() {
        try {
            g.setUser("0000002");
            //ProgressSummary summary = g.generateProgressSummary("0030000");
            //assertEquals(albertsStudentSummary, summary);
            g.setUser("0030000");
            //ProgressSummary summary = g.generateProgressSummary("0030000");
            //assertEquals(albertsStudentSummary, summary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * The purpose of the Test Case is to verify that 
     * when a student id is requested with a particular 
     * requirement area, their corresponding list of course 
     * requirements will be returned depending on that
     * particular student’s plan.
     */
    @Test
    public void testRequirementsDisplay() {
        
    }
    
    /**
     * The purpose of the Test Case is to check that we 
     * receive a list of courses completed by a student from the system.
     */
    @Test
    public void testCompletedCoursesDisplay() {
        
    }
    
    /**
     * The purpose of this test case is to check that the 
     * system will raise a warning when the calculated GPA 
     * of the student record is invalid (i.e. contains grades not on the A-F scale).
     */
    //TODO: There might be no need for this anymore since we have S,N,_ considered in calculation
    @Test
    public void testGPACalculationFaultyData() {
        
    }
    
    /**
     * The purpose of this test case is to check that 
     * the GPA that is calculated from data in the student 
     * record matches the GPA that shows up in the student summary.
     */
    @Test
    public void testGPACalculation() {
        
    }
    
    /**
     * This test makes sure that we retrieve the 
     * expected information for several student summaries.
     */
    @Test
    public void testRetrievingRelevantInformation() {
        
    }
    
    /**
     * The purpose of this test is to ensure 
     * that we can retrieve notes that are attached to student 
     * summaries by a GPC. 
     */
    @Test
    public void testNoteRetrieval() {
        
    }
    
    /**
     * This test will ensure that our criteria for 
     * determining that students are ready to graduate is correct.
     */
    @Test
    public void testAllRequirementsMetTest() {
        
    }
    
    /**
     * The purpose of the Test Case is to check that when a user 
     * submits hypothetical course completions for a student, a 
     * student summary with the change will be generated, but not 
     * persistently applied.
     */
    @Test
    public void testSubmittingHypotheticalCourseCompletions() {
        
    }
    
    /**
     * The purpose of the Test Case is to check that when a 
     * GPC makes a change to a student record the student 
     * record will reflect this change.
     */
    @Test
    public void testAmendingStudentRecord() {
        
    }
    
    /**
     * This test will verify that we can attach 
     * notes to student records.
     */
    @Test
    public void testAddingNotes() {
        
    }
    
    /**
     * The purpose of the Test Case is to 
     * check that a GPC can add a course with a
     * grade to a student record.
     */
    @Test
    public void testAddingCourses() {
        
    }
    
    /**
     * The purpose of the Test Case is to 
     * check that when a GPC marks a milestone 
     * on a given student record as completed 
     * that the student record will reflect this.
     */
    @Test
    public void testMarkingMileStones() {
        
    }
    
    /**
     * The purpose of the Test Case is to check when 
     * a GPC modifies a student record to add or remove 
     * a committee member, the student record will reflect this change.
     */
    @Test
    public void testModifyingCommitteeMembersAndAdvisors() {
        
    }
    
    /**
     * The purpose of this Test Case is to check when a 
     * GPC modifies a student record’s relevant data section, 
     * the change will be reflected on the student record.
     */
    @Test
    public void testModifyingRelevantDataSection() {
        
    }
    
    /**
     * The purpose of the Test Case is to verify that 
     * when a GPC through the interface makes a request 
     * to view the list of student ids.
     */
    @Test (expected = Exception.class)
    public void testListOfStudentsProvision() {
        try {
            //Kate Murry's id
            g.setUser("0000002");
            List<String> studentIds = g.getStudentIDs();
            for(String id : studentIds) {
                assertEquals(true, id instanceof String);
            }
            //Albert Einstein's id
            g.setUser("0030000");
            //throws InvalidUserAccessException
            List<String> studentIds2 = g.getStudentIDs();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
