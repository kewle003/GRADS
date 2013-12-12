package edu.umn.csci5801.model.test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.umn.csci5801.model.Course;
import edu.umn.csci5801.model.CourseArea;
import edu.umn.csci5801.model.CourseTaken;
import edu.umn.csci5801.model.Degree;
import edu.umn.csci5801.model.Department;
import edu.umn.csci5801.model.GRADS;
import edu.umn.csci5801.model.Grade;
import edu.umn.csci5801.model.Database;
import edu.umn.csci5801.model.Milestone;
import edu.umn.csci5801.model.MilestoneSet;
import edu.umn.csci5801.model.Professor;
import edu.umn.csci5801.model.ProgressSummary;
import edu.umn.csci5801.model.RequirementCheckResult;
import edu.umn.csci5801.model.Semester;
import edu.umn.csci5801.model.Student;
import edu.umn.csci5801.model.StudentRecord;
import edu.umn.csci5801.model.Term;
import junit.framework.TestCase;

/**
 * System testing
 * 
 * @author mark, patrick
 *
 */
public class GRADSTest extends TestCase {
    private GRADS g;
    private GRADS tempg;
    private List<StudentRecord> originalRecords;
    private Database tempStudentDatabase;
    private ProgressSummary albertsProgressSummary;
    private StudentRecord albertsStudentRecord;
    
    private static final String GRADS_DIR = "/Users/mark/Documents/workspace/GRADS_Materials"; // parent of src, does not end in "/" e.g. /root/files/GRADS
    
    @Override
    public void setUp() {
        tempStudentDatabase = new Database(GRADS_DIR+"/src/resources/tempStudents.txt");
        g = new GRADS(GRADS_DIR+"/src/resources/envStudentRecords.txt", GRADS_DIR+"/src/resources/courses.txt", GRADS_DIR+"/src/resources/envUsers.txt");
        tempg = new GRADS(GRADS_DIR+"/src/resources/tempStudents.txt", GRADS_DIR+"/src/resources/courses.txt", GRADS_DIR+"/src/resources/envUsers.txt");
        try {
            originalRecords = tempStudentDatabase.readOutStudentRecords();
        } catch (Exception e) {
            fail("Exception was not expected");
        }
        albertsProgressSummary = new ProgressSummary();
        albertsStudentRecord = new StudentRecord();
        
        //Set up Albert's Student Record
        albertsStudentRecord.setStudent(new Student("Albert", "Einstein", "0030000"));
        albertsStudentRecord.setDepartment(Department.COMPUTER_SCIENCE);
        albertsStudentRecord.setDegreeSought(Degree.MS_B);
        albertsStudentRecord.setTermBegan(new Term(Semester.FALL, new Integer(2009)));
        List<Professor> albertsAdvisors = new ArrayList<Professor>();
        albertsAdvisors.add(new Professor("Hien", "Tran", Department.COMPUTER_SCIENCE));
        albertsStudentRecord.setAdvisors(albertsAdvisors);
        List<Professor> albertsCommittee = new ArrayList<Professor>();
        albertsCommittee.add(new Professor("Mark", "Kewley", Department.COMPUTER_SCIENCE));
        albertsStudentRecord.setCommittee(albertsCommittee);
        List<CourseTaken> albertsCourses = new ArrayList<CourseTaken>();
        Course c1 = new Course("Analysis of Numerical Algorithms", "csci5302", "3", CourseArea.THEORY_ALGORITHMS);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2008)), Grade.A));
        c1 = new Course("Operating Systems", "csci5103", "3", CourseArea.ARCHITECTURE_SYSTEMS_SOFTWARE);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2008)), Grade.A));
        c1 = new Course("Computer Science Colloquium", "csci8970", "1", null);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.S));
        c1 = new Course("Plan B Project", "csci8760", "3", null);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2009)), Grade.A));
        c1 = new Course("Advanced Operating Systems", "csci8101", "3", null);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.B));
        c1 = new Course("Understanding the Social Web", "csci8117", "3", null);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2011)), Grade.A));
        c1 = new Course("Software Engineering I", "csci5801", "3", CourseArea.ARCHITECTURE_SYSTEMS_SOFTWARE);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.A));
        c1 = new Course("Software Engineering II", "csci5802", "3", CourseArea.ARCHITECTURE_SYSTEMS_SOFTWARE);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2011)), Grade.A));
        c1 = new Course("Applied Fourier Analysis","math4248", "4", null );
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.A));
        c1 = new Course("Theory Of Probability And Statistics I", "math5651", "4", null);
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.A));
        albertsStudentRecord.setCoursesTaken(albertsCourses);
        List<MilestoneSet> albertsMilestones = new ArrayList<MilestoneSet>();
        albertsMilestones.add(new MilestoneSet(Milestone.DPF_SUBMITTED, new Term(Semester.SPRING, new Integer(2010))));
        albertsMilestones.add(new MilestoneSet(Milestone.DPF_APPROVED, new Term(Semester.FALL, new Integer(2010))));
        albertsMilestones.add(new MilestoneSet(Milestone.PROJECT_COMMITTEE_APPOINTED, new Term(Semester.FALL, new Integer(2010))));
        albertsStudentRecord.setMilestonesSet(albertsMilestones);
        List<String> albertsNotes = new ArrayList<String>(); 
        albertsNotes.add("note1");
        albertsStudentRecord.setNotes(albertsNotes);
        
        //Setup parts of Albert's Progress Summary
        albertsProgressSummary.setStudent(albertsStudentRecord.getStudent());
        albertsProgressSummary.setDepartment(albertsStudentRecord.getDepartment());
        albertsProgressSummary.setDegreeSought(albertsStudentRecord.getDegreeSought());
        albertsProgressSummary.setTermBegan(albertsStudentRecord.getTermBegan());
        albertsProgressSummary.setAdvisors(albertsStudentRecord.getAdvisors());
        albertsProgressSummary.setCommittee(albertsStudentRecord.getCommittee());
        albertsProgressSummary.setNotes(albertsStudentRecord.getNotes());
        
    }
    
    @Before
    public void beforeEachTest(){
    	tempStudentDatabase.updateStudentRecords(originalRecords);
    	
    }

    
    /**
     * The purpose of this test case is to verify
     * that when a user id that does not exist in
     * the database logs in, they will be blocked from GRADS
     */
    @Test
    public void testInvalidLogin() {
        try {
            //Verify invalid user logging in
            g.setUser("0000000");
            fail("Expected InvalidUserException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of this test case is to verify that
     * when a null user logs in, they will be blocked from GRADS
     */
    @Test
    public void testNullLogin() {
        try {
            //Verify null user trying to log in
            g.setUser(null);
            fail("Excepted InvalidDataException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of the Test Case is to verify
     * that when a student or gpc requests to view
     * a student transcript appropriate actions will
     * follow.
     * @throws Exception 
     */
    @Test
    public void testStudentTranscriptRetrieval() throws Exception {
        //Verify Kate Murry can retrieve Albert's transcript
        g.setUser("0000002");
        StudentRecord alberts = g.getTranscript("0030000");
        assertEquals(albertsStudentRecord.getStudent(), alberts.getStudent());
        assertEquals(albertsStudentRecord.getDepartment(), alberts.getDepartment());
        assertEquals(albertsStudentRecord.getDegreeSought(), alberts.getDegreeSought());
        assertEquals(albertsStudentRecord.getTermBegan(), alberts.getTermBegan());
        assertEquals(albertsStudentRecord.getAdvisors(), alberts.getAdvisors());
        assertEquals(albertsStudentRecord.getCommittee(), alberts.getCommittee());
        assertEquals(albertsStudentRecord.getMilestonesSet(), alberts.getMilestonesSet());
        assertEquals(albertsStudentRecord.getCoursesTaken(), alberts.getCoursesTaken());
        assertEquals(albertsStudentRecord.getNotes(), alberts.getNotes());
        assertEquals(albertsStudentRecord, alberts);
        
        //Verify Albert can retrieve his record
        g.setUser("0030000");
        alberts = g.getTranscript("0030000");
        assertEquals(albertsStudentRecord, alberts);
    }
    
    /**
     * The purpose of this test is to verify that
     * when a student tries to retrieve another student's
     * transcript they will be blocked
     * @throws Exception 
     */
    @Test
    public void testStudentTranscriptRetrievalInvalidUserAccess() throws Exception {
        //Verify Kurt can not retrieve Albert's record
        g.setUser("0040000");
        try {
            StudentRecord alberts = g.getTranscript("0030000");
            fail("Expected an InvalidUserAccessException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of this test is to verify that
     * when an invalid student id is given to retrieve
     * a transcript an exception will be thrown notifying
     * the user
     * @throws Exception 
     */
    @Test
    public void testStudentTranscriptRetrievalInvalidUser() throws Exception {
        //Verify invalid user id given
        g.setUser("0000002");
        try {
            //Attempt to retrieve Donnie's Transcript
            StudentRecord donnie = g.getTranscript("1000000");
            fail("Expected InvalidUserException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of the Test Case is to check that 
     * when a user requests for a student summary, 
     * the student summary will be retrieved.
     * @throws Exception 
     */
    @Test
    public void testStudentSummaryProvision() throws Exception {
        g.setUser("0000002");
        //Generate Albert's Progress Summary
        ProgressSummary summary = g.generateProgressSummary("0030000");

        assertEquals(albertsProgressSummary.getStudent(), summary.getStudent());
        assertEquals(albertsProgressSummary.getDepartment(), summary.getDepartment());
        assertEquals(albertsProgressSummary.getDegreeSought(), summary.getDegreeSought());
        assertEquals(albertsProgressSummary.getAdvisors(), summary.getAdvisors());
        assertEquals(albertsProgressSummary.getCommittee(), summary.getCommittee());
        assertEquals(albertsProgressSummary.getNotes(), summary.getNotes());
        assertEquals(albertsProgressSummary.getTermBegan(), summary.getTermBegan());
        
        //Verify Albert still has some requirements left
        assertNotSame(summary.getRequirementCheckResults().isEmpty(), true);

        //Make sure no exception is thrown
        try {
            g.setUser("0030000");
            summary = g.generateProgressSummary("0030000");
        } catch (Exception e) {
            fail("Was not expecting an exception");
        }
        
    }
    
    
    /**
     * The purpose of the Test Case is to check that we 
     * receive a list of courses completed by a student from the system.
     */
    @Test
    public void testCompletedCoursesDisplay() throws Exception {
        g.setUser("0000002");
        StudentRecord sr = g.getTranscript("0010000");
        
        printStudentRecord(sr);
        
        List<CourseTaken> courseList = sr.getCoursesTaken();
        List<String> courseNames = new ArrayList<String>();
        for(CourseTaken c : courseList){
        	courseNames.add(c.getCourse().getId());
        }
        
        assertTrue(courseNames.size() == 2);
        assertTrue(courseNames.contains("csci5103"));
        assertTrue(courseNames.contains("csci5115"));
    }
    

    /**
     * The purpose of this test case is to check that
     * when a student has not declared a major, an exception
     * will have been thrown indicating ProgressSummaryBuilder
     * failed
     * @throws Exception
     */
    public void testInvalidDegree() throws Exception {
        g.setUser("0000002");
        try {
            //Attempt to generate Degree Much's Progress Summary
            ProgressSummary ps = g.generateProgressSummary("1010000");
            fail("Expected InvalidDataException");
        } catch (Exception e) {
        }
    }
    
    /**
     * The purpose of this test is to ensure 
     * that we can retrieve notes that are attached to student 
     * summaries by a GPC. 
     */
    @Test
    public void testNoteRetrieval() throws Exception{
        g.setUser("0000002");
        
        ProgressSummary ps = g.generateProgressSummary("0000010");
        List<String> notes = ps.getNotes();
        assertTrue(notes.size() == 2);
        assertTrue(notes.contains("note1"));
        assertTrue(notes.contains("note2"));
    }
    
    /**
     * This test will ensure that our criteria for 
     * determining that students are ready to graduate is correct.
     */
    @Test
    public void testAllRequirementsMet() throws Exception{
        g.setUser("0000002");
        
        List<String> passingIds = new ArrayList<String>();
        
        passingIds.add("0000010");//Passing PhD
        passingIds.add("0000020");//MS-A
        passingIds.add("0000030");//MS-B
        passingIds.add("0000040");//MS-C
        
        for(String id : passingIds){
        	ProgressSummary ps = g.generateProgressSummary(id);
        	for(RequirementCheckResult req : ps.getRequirementCheckResults()){
        		if(req.getErrorMsgs() != null && !req.getErrorMsgs().isEmpty()){
        			fail(id +":"+ps.getStudent().getFirstName()+" "+ps.getStudent().getLastName() + " is not graduating, but should be. List: "+req.getErrorMsgs());
        			
        		}
        	}
        }
        List<String> failingIds = new ArrayList<String>();
        
        failingIds.add("0000100");
        failingIds.add("0004000");
        failingIds.add("0010000");
        failingIds.add("0100000");
        
        for(String id : failingIds){
        	ProgressSummary ps = g.generateProgressSummary(id);
        	boolean foundAProblem = false;
        	for(RequirementCheckResult req : ps.getRequirementCheckResults()){
        		if(req.getErrorMsgs() != null && !req.getErrorMsgs().isEmpty()){
        			foundAProblem = true;
        		}
        	}
        	if(!foundAProblem){
        		fail(id +":"+ps.getStudent().getFirstName()+" "+ps.getStudent().getLastName() + " is graduating, but shouldn't be.");
        	}
        }
        
    }
    
    /**
     * The purpose of the Test Case is to check that when a user 
     * submits hypothetical course completions for a student, a 
     * student summary with the change will be generated, but not 
     * persistently applied.
     */
    @Test
    public void testSubmittingHypotheticalCourseCompletions() throws Exception{
        tempg.setUser("0000002");
        
        List<CourseTaken> c = new ArrayList<CourseTaken>();
        c.add(new CourseTaken(new Course("Computer Science Colloquium", "csci8970", "1", CourseArea.NONE), new Term(Semester.FALL, 2010), Grade.S));
        
        /*
         * "name": "Computer Science Colloquium",
            "id": "csci8970", "courseArea": "NONE",
            "numCredits": "1"
         */
        
        //Add this to Missy Whatsit. She should have colloquial problems.
        ProgressSummary psSim, psBefore;
        
        ArrayList<String> psErrorsBefore = new ArrayList<String>();
        
        psBefore = tempg.generateProgressSummary("0000100");
        for(RequirementCheckResult req : psBefore.getRequirementCheckResults()){
    		if(req.getErrorMsgs() != null && !req.getErrorMsgs().isEmpty()){
    			//Accumulate all results from lists
    			psErrorsBefore.addAll(req.getErrorMsgs());
    		}
    	}
        
        assertTrue("psErrorsBefore does not contain colloquium error.", psErrorsBefore.contains("csci8970 has not been taken or has a passing grade"));
        
        psSim = tempg.simulateCourses("0000100", c);
        
        for(RequirementCheckResult req : psSim.getRequirementCheckResults()){
    		if(req.getErrorMsgs() != null && !req.getErrorMsgs().isEmpty()){
    			//Search all lists for colloquium error.
    			if(req.getErrorMsgs().contains("csci8970 has not been taken or has a passing grade")){
    				fail("The simulation did not produce a summary with a completed colloquium.");
    			}
    		}
    	}
        
        ProgressSummary psAfter;
        ArrayList<String> psErrorsAfter = new ArrayList<String>();
        
        psAfter = tempg.generateProgressSummary("0000100");
        for(RequirementCheckResult req : psAfter.getRequirementCheckResults()){
    		if(req.getErrorMsgs() != null && !req.getErrorMsgs().isEmpty()){
    			//Accumulate all results from lists
    			psErrorsAfter.addAll(req.getErrorMsgs());
    		}
    	}
        
        System.out.println(psBefore);
        
        assertEquals(psBefore.toString(), psAfter.toString());
        
        while(!psErrorsAfter.isEmpty()){
        	assertTrue("Unable to find error message: "+psErrorsAfter.get(0), psErrorsBefore.remove(psErrorsAfter.get(0)));
        	psErrorsAfter.remove(0);        	
        }
        
    }
    
    
    /**
     * This test will verify that we can attach 
     * notes to student records.
     * @throws Exception 
     */
    @Test
    public void testAddingNotes() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        
        //Add a note to Albert's transcript
        tempg.addNote("0030000", "Smart chap");
        
        //Verify it was modified
        StudentRecord albert = tempg.getTranscript("0030000");
        assertEquals(true, albert.getNotes().contains("Smart chap"));
        
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);
    }
    
    /**
     * The purpose of this test case is to verify
     * that when a student tries to add a note
     * to their record, they will be blocked
     * @throws Exception 
     */
    @Test
    public void testAddNoteInvalidUser() throws Exception {
        //Set Albert Einstein
        g.setUser("0030000");
        try {
            //Verify he is blocked from adding a note
            g.addNote("Set all class grades to A", "0030000");
            fail("Expected InvalidUserAccessException");
        } catch (Exception e) {
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC provides an invalid user to add notes too
     * GRADS will alert the GPC that this user does not
     * exist
     * @throws Exception 
     */
    @Test
    public void testAddNoteInvalidUserId() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        try {
            //Add a note to Donnie's transcript who is not in the CSCI department
            tempg.addNote("1000000", "He likes coffee");
            fail("Excepted InvalidUserException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC provides a null user GRADS will alert
     * the GPC the user is null
     * @throws Exception 
     */
    @Test
    public void testAddNoteNullId() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        try {
            //Add a note to invalid userId
            tempg.addNote(null, "He likes coffee");
            fail("Excepted InvalidDataException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of the Test Case is to 
     * check that a GPC can add a course with a
     * grade to a student record.
     * @throws Exception 
     */
    @Test
    public void testAddingCourses() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        
        //Retrieve Albert's student transcript
        StudentRecord albert = tempg.getTranscript("0030000");
        
        //Modify Albert's courses to add a course
        Course newc = new Course();
        newc.setName("e-Public Health: Online Intervention Design");
        newc.setId("csci5129");
        newc.setNumCredits("3");
        albert.getCoursesTaken().add(new CourseTaken(newc, new Term(Semester.SPRING, new Integer(2010)), Grade.A));
        try {
            tempg.updateTranscript("0030000", albert);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //Verify the change was made
        albert = tempg.getTranscript("0030000");
        assertEquals(true, albert.getCoursesTaken().contains(new CourseTaken(newc, new Term(Semester.SPRING, new Integer(2010)), Grade.A)));
        
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);
    }
    
    /**
     * The purpose of this test is to verify
     * that when a GPC accidentally gives an invalid
     * credit to a class, GRADS will block this change
     * from happening and notify the GPC
     * @throws Exception 
     */
    @Test
    public void testInvalidCourseCreditAdded() throws Exception {
        //Set Kate Murry
        g.setUser("0000002");
        
        //Retrieve Albert's student transcript
        StudentRecord albert = g.getTranscript("0030000");
        
        //Modify Albert's courses to add a course
        Course newc = new Course("e-Public Health: Online Intervention Design", "csci5129", "2", null);
        albert.getCoursesTaken().add(new CourseTaken(newc, new Term(Semester.SPRING, new Integer(2010)), Grade.A));
  
        try { 
            //Verify that the update is blocked
            g.updateTranscript("0030000", albert);
            fail("InvalidCourseException expected");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of the Test Case is to 
     * check that when a GPC marks a milestone 
     * on a given student record as completed 
     * that the student record will reflect this.
     * @throws Exception 
     */
    @Test
    public void testMarkingMileStones() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        MilestoneSet m = new MilestoneSet(Milestone.THESIS_APPROVED, new Term(Semester.SPRING, new Integer(2010)));
        
        //Modify Missy's transcript to add THESIS_APPROVED milestone
        StudentRecord missy = tempg.getTranscript("0000100");
        missy.getMilestonesSet().add(m);
        tempg.updateTranscript("0000100", missy);
        
        //Verify the update happened
        missy = tempg.getTranscript("0000100");
        assertEquals(true, missy.getMilestonesSet().contains(m));
        
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);      
    }
    
    /**
     * The purpose of the Test Case is to check when 
     * a GPC modifies a student record to add or remove 
     * a committee member, the student record will reflect this change.
     * @throws Exception 
     */
    @Test
    public void testModifyingCommitteeMembersAndAdvisors() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        
        //Retrieve Kurt Godel's transcript
        StudentRecord kurt = tempg.getTranscript("0040000");
     
        //Modify Kurt's Committee members
        kurt.getAdvisors().remove(new Professor("John", "Doe", Department.COMPUTER_SCIENCE));

        //Verify the change occurred
        tempg.updateTranscript("0040000", kurt);
        kurt = tempg.getTranscript("0040000");
        assertEquals(false, kurt.getAdvisors().contains(new Professor("John", "Doe", Department.COMPUTER_SCIENCE)));
        
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);
    }
    
    /**
     * The purpose of this Test Case is to check when a 
     * GPC modifies a student record’s relevant data section, 
     * the change will be reflected on the student record.
     * @throws Exception 
     */
    @Test 
    public void testModifyingRelevantDataSection() throws Exception {
        //Set Kate Murry
        tempg.setUser("0000002");
        //Retrieve Missy What transcript
        StudentRecord missy = tempg.getTranscript("0000100");
        
        //Modify relevant Student data
        missy.setTermBegan(new Term(Semester.FALL, new Integer(2013)));
        missy.setDegreeSought(Degree.MS_A);
        missy.setDepartment(Department.COMPUTER_SCIENCE);
        
        
        //Verify the changes occured
        tempg.updateTranscript("0000100", missy);
        missy = tempg.getTranscript("0000100");
        assertEquals(new Term(Semester.FALL, new Integer(2013)), missy.getTermBegan());
        assertEquals(Degree.MS_A, missy.getDegreeSought());
        assertEquals(Department.COMPUTER_SCIENCE, missy.getDepartment());
      
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);
    }
    
    /**
     * The purpose of this test is to verify that when a GPC
     * tries to modify a student id on the corresponding student
     * record an error message will be returned indicating the GPC
     * can not modify a student id
     * @throws Exception 
     */
    @Test
    public void testUpdateTranscriptStudentIdBlock() throws Exception {
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord missy = g.getTranscript("0000100");
        
        //Test Kate can not modify Missy's studentId
        missy.getStudent().setId("YOLO");
        try { 
            g.updateTranscript("0000100", missy);
            fail("Exepected InvalidUserException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC accidentally puts in another student id
     * when updating a student record an error message
     * will be returned notifying the GPC
     */
    @Test
    public void testUpdateTranscriptInvalidId() throws Exception{
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord missy = g.getTranscript("0000100");
        
        try {
            //Block Kate from replacing Albert's transcript with Missy's
            g.updateTranscript("0030000", missy);
            fail("Exepected InvalidDataException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC enters a null user id an error message will
     * be returned notifying the GPC
     */
    @Test
    public void testUpdateTranscriptNullId() throws Exception{
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord missy = g.getTranscript("0000100");
            
        try {
            
            //Block Kate from replacing Albert's transcript with Missy's
            g.updateTranscript(null, missy);
            fail("Exepected InvalidDataException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that
     * a student will not be allowed to modify their
     * student record
     * @throws Exception 
     */
    @Test
    public void testUpdateTranscriptInvalidAccess() throws Exception {
      //Set Albert Einstein
        g.setUser("0030000");
        StudentRecord albert = g.getTranscript("0030000");
        
        try {
            
            //Block Albert from modifying his transcript
            g.updateTranscript("0030000", albert);
            fail("Excpected InvalidUserAccessException");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of the Test Case is to verify that 
     * when a GPC through the interface makes a request 
     * to view the list of student ids in the databae 
     * will be returned
     * @throws Exception 
     */
    @Test
    public void testListOfStudentsProvision() throws Exception {
        //Set up the student Ids we expect
        List<String> ids = new ArrayList<String>();
        ids.add("0000010");
        ids.add("0000040");
        ids.add("0000100");
        ids.add("0000200");
        ids.add("0000400");
        ids.add("0010000");
        ids.add("0020000");
        ids.add("0030000");
        ids.add("0040000");
        ids.add("0100000");
        ids.add("0200000");
        ids.add("1000000");
        ids.add("4040001");
        ids.add("4040002");
        ids.add("4040003");
        ids.add("4040004");
        
        //Kate Murry's id
        g.setUser("0000002");
        List<String> studentIds = g.getStudentIDs();
        
        //Assert the ids are equal and size is the same
        Iterator<String> idIterator = ids.iterator();
        while(idIterator.hasNext()) {
            assertEquals(true, studentIds.contains(idIterator.next()));
        }
        assertEquals(ids.size(), studentIds.size());
    }
    
    /**
     * This test will verify that when a student tries
     * to retrieve a list of student id's an error message
     * will be returned notifying the student.
     */
    @Test
    public void testGetStudentIDsInvalidUserAccess() {
        try {
            //Albert Einstein's id
            g.setUser("0030000");
            
            //Block Albert from retrieving studentids
            List<String> studentIds = g.getStudentIDs();
            fail("Expected InvalidUserAccess Exception");
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of this test is to test the equals
     * and hashcode method that the TA's provided in each
     * class that are never used
     * @throws Exception
     */
    @Test
    public void testHashCodeEquals() throws Exception{
        tempg.setUser("0000002");
        StudentRecord sr = tempg.getTranscript("0000100");
        
        List<Field[]> fieldsList = new ArrayList<Field[]>();
        List<Object> buddyObjects = new ArrayList<Object>();
        List<String> classNames = new ArrayList<String>();
        fieldsList.add(sr.getClass().getDeclaredFields());
        buddyObjects.add(sr);
        classNames.add(sr.getClass().getName());
        List<Object> theGuts = new ArrayList<Object>();

        while(!fieldsList.isEmpty()){
            Field[] fields = fieldsList.get(0);
            fieldsList.remove(0);
            Object human = buddyObjects.get(0);
            buddyObjects.remove(0);
            
            for(Field f : fields){
                f.setAccessible(true);
                if(f.get(human) instanceof List){
                    List l = (List) f.get(human);
                    for(int i = 0; i<l.size(); i++){
                        Object stuffInList = l.get(i);
                        if(!(stuffInList instanceof List)){
                            theGuts.add(stuffInList);
                        }else{
                            l.addAll((List<Object>) stuffInList);
                        }
                    }
                }else{
                        theGuts.add(f.get(human));
                }
            }
            for(Object o : theGuts){
                if( o instanceof Integer || o instanceof String || o instanceof Double || o instanceof Float || o instanceof Character || o instanceof Boolean || o instanceof String){}
                else{
                    assertTrue(o.equals(o));
                    assertFalse(o.equals(null));
                    assertFalse(o.equals(new Object()));
                    if(!o.getClass().isEnum() && !o.getClass().isArray()){
                        assertFalse(o.equals(o.getClass().newInstance()));
                    }
                    o.hashCode();
                    if(!classNames.contains(o.getClass().getName())){
                        fieldsList.add(o.getClass().getDeclaredFields());
                        buddyObjects.add(o);
                        classNames.add(o.getClass().getName());
                    }
                }
            }
        }
    }
    
    
    
    //HELPERS
    
    private void printStudentRecord(StudentRecord sr){
    	StringBuilder sb = new StringBuilder();
    	sb.append("Student Record of: ");
    	sb.append(sr.getStudent().getFirstName()+" "+sr.getStudent().getLastName());
    	sb.append("\n\n");
    	sb.append("Department: "+sr.getDepartment().name());
    	sb.append("\n");
    	sb.append("Degree Sought: "+sr.getDegreeSought().name());
    	sb.append("\n");
    	sb.append("Enrolled: "+sr.getTermBegan().getSemester().name() + " "+sr.getTermBegan().getYear());
    	sb.append("\n");
    	sb.append("Advisors:");
    	for(Professor p : sr.getAdvisors()){
    		sb.append(" "); sb.append(p.getFirstName()); sb.append(" "); sb.append(p.getLastName()); sb.append(",");
    	}
    	sb.deleteCharAt(sb.length()-1); sb.append("\n");
    	sb.append("Committee:");
    	for(Professor p : sr.getCommittee()){
    		sb.append(" "); sb.append(p.getFirstName()); sb.append(" "); sb.append(p.getLastName()); sb.append(",");
    	}
    	sb.deleteCharAt(sb.length()-1); sb.append("\n");
    	sb.append("Courses:");
    	for(CourseTaken c : sr.getCoursesTaken()){
    		sb.append(" "); sb.append(c.getCourse().getId());
    	}
    	sb.append("\n");
    	sb.append("Milestones:");
    	for(MilestoneSet ms : sr.getMilestonesSet()){
    		sb.append(" "); sb.append(ms.getMilestone().name());
    	}
    	sb.append("\n");
    	sb.append("Notes:\n");
    	for(String n : sr.getNotes()){
    		sb.append("  "); sb.append(n); sb.append("\n");
    	}
    	sb.append("------ END ------");
    	
    	System.out.println(sb);
    }
    
}
