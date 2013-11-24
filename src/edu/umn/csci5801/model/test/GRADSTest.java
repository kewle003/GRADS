package edu.umn.csci5801.model.test;

import org.junit.Test;

import edu.umn.csci5801.model.GRADS;
import junit.framework.TestCase;

/**
 * System testing
 * @author mark
 *
 */
//TODO: Black box tests; Environment A, B, and C
public class GRADSTest extends TestCase {
    private GRADS g;
    
    @Override
    public void setUp() {
        g = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/edu/umn/csci5801/model/test/envUsers.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/edu/umn/csci5801/model/test/envAstudentRecords.txt");
    }
    
    @Test
    public void testStudentSummaryProvision() {
        
    }
    
    @Test
    public void testRequirementsDisplay() {
        
    }
    
    @Test
    public void testCompletedCoursesDisplay() {
        
    }
    
    @Test
    public void testGPACalculationFaultyData() {
        
    }
    
    @Test
    public void testGPACalculation() {
        
    }
    
    @Test
    public void testRetrievingRelevantInformation() {
        
    }
    
    @Test
    public void testNoteRetrieval() {
        
    }
    
    @Test
    public void testAllRequirementsMetTest() {
        
    }
    
    @Test
    public void testSubmittingHypotheticalCourseCompletions() {
        
    }
    
    @Test
    public void testAmendingStudentRecord() {
        
    }
    
    @Test
    public void testAddingNotes() {
        
    }
    
    @Test
    public void testAddingCourses() {
        
    }
    
    @Test
    public void testMarkingMileStones() {
        
    }
    
    @Test
    public void testModifyingCommitteeMembersAndAdvisors() {
        
    }
    
    @Test
    public void testModifyingRelevantDataSection() {
        
    }
    
    @Test
    public void testListOfStudentsProvision() {
        
    }
    
    
}
