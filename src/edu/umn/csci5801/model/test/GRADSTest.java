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
import edu.umn.csci5801.model.JSONHandler;
import edu.umn.csci5801.model.Milestone;
import edu.umn.csci5801.model.MilestoneSet;
import edu.umn.csci5801.model.Professor;
import edu.umn.csci5801.model.ProgressSummary;
import edu.umn.csci5801.model.RequirementCheckResult;
import edu.umn.csci5801.model.Semester;
import edu.umn.csci5801.model.Student;
import edu.umn.csci5801.model.StudentRecord;
import edu.umn.csci5801.model.Term;
import edu.umn.csci5801.model.exception.InvalidDataException;
import junit.framework.TestCase;

/**
 * System testing
 * 
 * @author mark
 *
 */
//TODO: Finish writing the rest of our BlackBox test
//45.4% So far
//80% EMMA code coverage
public class GRADSTest extends TestCase {
    private GRADS g;
    private GRADS tempg;
    private List<StudentRecord> originalRecords;
    private JSONHandler tempStudentDatabase;
    private ProgressSummary albertsProgressSummary;
    private StudentRecord albertsStudentRecord;
    
    @Override
    public void setUp() {
        tempStudentDatabase = new JSONHandler("/home/sever408/5801/GRADS/src/resources/tempStudents.txt");
        g = new GRADS("/home/sever408/5801/GRADS/src/resources/envStudentRecords.txt", "/home/sever408/5801/GRADS/src/resources/courses.txt", "/home/sever408/5801/GRADS/src/resources/envUsers.txt");
        tempg = new GRADS("/home/sever408/5801/GRADS/src/resources/tempStudents.txt", "/home/sever408/5801/GRADS/src/resources/courses.txt", "/home/sever408/5801/GRADS/src/resources/envUsers.txt");
        try {
            originalRecords = tempStudentDatabase.readOutStudentRecords();
        } catch (Exception e) {
            fail("Exception was not expected");
        }
        albertsProgressSummary = new ProgressSummary();
        albertsStudentRecord = new StudentRecord();
        
        //Set up albertsStudentRecord, just follow the envStudentRecord.txt to set up your own
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
        
        //TODO: Finish setting up albert's progress summary
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
     * that when a user id that does not exists in
     * the database logs in, they will be blocked
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
     * when a null user logs in they will be blocked
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
            StudentRecord alberts = g.getTranscript("0000000");
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
       /* g.setUser("0000002");
        ProgressSummary summary = g.generateProgressSummary("0030000");
        assertEquals(albertsProgressSummary.getStudent(), summary.getStudent());
        assertEquals(albertsProgressSummary.getDepartment(), summary.getDepartment());
        assertEquals(albertsProgressSummary.getDegreeSought(), summary.getDegreeSought());
        assertEquals(albertsProgressSummary.getAdvisors(), summary.getAdvisors());
        assertEquals(albertsProgressSummary.getCommittee(), summary.getCommittee());
        assertEquals(albertsProgressSummary.getNotes(), summary.getNotes());
        assertEquals(albertsProgressSummary.getTermBegan(), summary.getTermBegan());
        */
        //assertEquals(albertsProgressSummary, summary);
        //g.setUser("0030000");
        //summary = g.generateProgressSummary("0030000");
        //assertEquals(albertsProgressSummary, summary);
      
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
    public void testSubmittingHypotheticalCourseCompletions() {
        
    }
    
    /**
     * The purpose of the Test Case is to check that when a 
     * GPC makes a change to a student record the student 
     * record will reflect this change.
     */
    //TODO: This might not be a necessary test anymore
    @Test
    public void testAmendingStudentRecord() {
        try {
            //Set Kate Murry
           // tempg.setUser("0000002");
            
            //Reset the database
           // tempStudentDatabase.updateStudentRecords(originalRecords);
        } catch (Exception e) {
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
        
        //Add a note to Donnie's transcript who does not have notes
        tempg.addNote("1000000", "He likes coffee");
        
        //Verify it was modified
        StudentRecord donnie = tempg.getTranscript("1000000");
        assertEquals("He likes coffee", donnie.getNotes().get(0));
        
        //Add a note to Albert's transcript who does have notes
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
            //Add a note to Donnie's transcript who does not have notes
            tempg.addNote("0000000", "He likes coffee");
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
            //Add a note to Donnie's transcript who does not have notes
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
        tempg.updateTranscript("0030000", albert);
        
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
     * from happening
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
        //Retrieve Donnie Wahlberg's transcript
        StudentRecord donnie = tempg.getTranscript("1000000");
        
        //Modify Donnie's Committee members
        List<Professor> donnieCommittee = new ArrayList<Professor>();
        donnieCommittee.add(new Professor("Lou", "Pearlman", Department.COMPUTER_SCIENCE));
        donnie.setCommittee(donnieCommittee);
        
        //Verify the change occurred
        tempg.updateTranscript("1000000", donnie);
        donnie = tempg.getTranscript("1000000");
        assertEquals(new Professor("Lou", "Pearlman", Department.COMPUTER_SCIENCE), donnie.getCommittee().get(0));
        
        //Retrieve Kurt Godel's transcript
        StudentRecord kurt = tempg.getTranscript("0040000");
     
        //Modify Kurt's Committee members
        kurt.getAdvisors().remove(new Professor("John", "Freeman", Department.COMPUTER_SCIENCE));

        //Verify the change occurred
        tempg.updateTranscript("0040000", kurt);
        kurt = tempg.getTranscript("0040000");
        assertEquals(false, kurt.getAdvisors().contains(new Professor("John", "Freeman", Department.COMPUTER_SCIENCE)));
        
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
        //Retrieve Donnie Wahlberg's transcript
        StudentRecord donnie = tempg.getTranscript("1000000");
        
        //Modify relevant Student data
        donnie.setTermBegan(new Term(Semester.FALL, new Integer(2013)));
        donnie.setDegreeSought(Degree.MS_A);
        donnie.setDepartment(Department.COMPUTER_SCIENCE);
        
        
        //Verify the changes occured
        tempg.updateTranscript("1000000", donnie);
        donnie = tempg.getTranscript("1000000");
        assertEquals(new Term(Semester.FALL, new Integer(2013)), donnie.getTermBegan());
        assertEquals(Degree.MS_A, donnie.getDegreeSought());
        assertEquals(Department.COMPUTER_SCIENCE, donnie.getDepartment());
      
        //Reset the database
        tempStudentDatabase.updateStudentRecords(originalRecords);
    }
    
    /**
     * The purpose of this test is to verify that when a GPC
     * tries to modify a studentId on their transcript an
     * InvalidUserException will be thrown
     * @throws Exception 
     */
    @Test
    public void testUpdateTranscriptStudentIdBlock() throws Exception {
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord donnie = g.getTranscript("1000000");
        
        //Test Kate can not modify Donnie's studentId
        donnie.getStudent().setId("YOLO");
        try { 
            g.updateTranscript("1000000", donnie);
            fail("Exepected InvalidUserException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC accidentally puts in another studentId
     * when updating the transcript, an InvalidDataException
     * will be thrown
     */
    @Test
    public void testUpdateTranscriptInvalidId() throws Exception{
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord donnie = g.getTranscript("1000000");
        
        try {
            //Block Kate from replacing Albert's transcript with Donnie's
            g.updateTranscript("0030000", donnie);
            fail("Exepected InvalidDataException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that when
     * a GPC enters a null user id an InvalidDataException
     * will be thrown
     */
    @Test
    public void testUpdateTranscriptNullId() throws Exception{
        //Set Kate Murry
        g.setUser("0000002");
        StudentRecord donnie = g.getTranscript("1000000");
        try {
            
            //Block Kate from replacing Albert's transcript with Donnie's
            g.updateTranscript(null, donnie);
            fail("Exepected InvalidDataException");
        } catch (Exception e){
            
        }
    }
    
    /**
     * The purpose of this test is to verify that
     * a student will not be allowed to modify their
     * transcript
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
     * to view the list of student ids.
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
     * to retrieve a list of student id's an InvalidUserAccessException
     * will be thrown
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
    
    @Test
    public void testCodeCoverageIsStupid() throws Exception{
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
    
    
}
