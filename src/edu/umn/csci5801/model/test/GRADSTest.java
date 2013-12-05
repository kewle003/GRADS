package edu.umn.csci5801.model.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import edu.umn.csci5801.model.Semester;
import edu.umn.csci5801.model.Student;
import edu.umn.csci5801.model.StudentRecord;
import edu.umn.csci5801.model.Term;
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
        tempStudentDatabase = new JSONHandler("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/tempStudents.txt");
        g = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envStudentRecords.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/courses.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envUsers.txt");
        tempg = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/tempStudents.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/courses.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envUsers.txt");
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
     */
    @Test
    public void testStudentTranscriptRetrieval() {
        try {
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

        } catch (Exception e) {
        }
    }
    
    /**
     * The purpose of this test is to verify that
     * when a student tries to retrieve another student's
     * transcript they will be blocked
     */
    @Test
    public void testStudentTranscriptRetrievalInvalidUserAccess() {
        try {
            
            //Verify Kurt can not retrieve Albert's record
            g.setUser("0040000");
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
     */
    @Test
    public void testStudentTranscriptRetrievalInvalidUser() {
        try {
            //Verify invalid user id given
            g.setUser("0000002");
            StudentRecord alberts = g.getTranscript("0000000");
            fail("Expected InvalidUserException");
        } catch (Exception e) {
            
        }
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
            ProgressSummary summary = g.generateProgressSummary("0030000");
            assertEquals(albertsProgressSummary.getStudent(), summary.getStudent());
            assertEquals(albertsProgressSummary.getDepartment(), summary.getDepartment());
            assertEquals(albertsProgressSummary.getDegreeSought(), summary.getDegreeSought());
            assertEquals(albertsProgressSummary.getAdvisors(), summary.getAdvisors());
            assertEquals(albertsProgressSummary.getCommittee(), summary.getCommittee());
            assertEquals(albertsProgressSummary.getNotes(), summary.getNotes());
            assertEquals(albertsProgressSummary.getTermBegan(), summary.getTermBegan());
            //assertEquals(albertsProgressSummary, summary);
            //g.setUser("0030000");
            //summary = g.generateProgressSummary("0030000");
            //assertEquals(albertsProgressSummary, summary);
        } catch (Exception e) {
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
     */
    @Test
    public void testAddingNotes() {
        try {
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
        } catch (Exception e) {
            fail("Was not expecting an exception");
        }

    }
    
    /**
     * The purpose of this test case is to verify
     * that when a student tries to add a note
     * to their record, they will be blocked
     */
    @Test
    public void testaddNoteInvalidUser() {
        try {
            //Set Albert Einstein
            g.setUser("0030000");
            
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
     */
    @Test
    public void testAddNoteInvalidUserId() {
        try {
            //Set Kate Murry
            tempg.setUser("0000002");
            
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
     */
    @Test
    public void testAddNoteNullId() {
        try {
            //Set Kate Murry
            tempg.setUser("0000002");
            
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
     */
    @Test
    public void testAddingCourses() {
        try {
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
        } catch (Exception e) {
            fail("Exception was not expected");
        }
    }
    
    /**
     * The purpose of this test is to verify
     * that when a GPC accidentally gives an invalid
     * credit to a class, GRADS will block this change
     * from happening
     */
    @Test
    public void testInvalidCourseCreditAdded() {
        try {
            //Set Kate Murry
            g.setUser("0000002");
            
            //Retrieve Albert's student transcript
            StudentRecord albert = g.getTranscript("0030000");
            
            //Modify Albert's courses to add a course
            Course newc = new Course("e-Public Health: Online Intervention Design", "csci5129", "2", null);
            albert.getCoursesTaken().add(new CourseTaken(newc, new Term(Semester.SPRING, new Integer(2010)), Grade.A));
            
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
     */
    @Test
    public void testMarkingMileStones() {
        try {
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
        } catch (Exception e) {
            fail("Exception not expected");
        }        
    }
    
    /**
     * The purpose of the Test Case is to check when 
     * a GPC modifies a student record to add or remove 
     * a committee member, the student record will reflect this change.
     */
    @Test
    public void testModifyingCommitteeMembersAndAdvisors() {
        try {
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
            assertEquals(new Professor("Lou", "Peralman", Department.COMPUTER_SCIENCE), donnie.getAdvisors().get(0));
            
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
        } catch (Exception e) {
            
        }
    }
    
    /**
     * The purpose of this Test Case is to check when a 
     * GPC modifies a student record’s relevant data section, 
     * the change will be reflected on the student record.
     */
    @Test 
    public void testModifyingRelevantDataSection() {
        try {
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
        } catch (Exception e) {
            fail("Exception was not expected");
        }
    }
    
    /**
     * The purpose of this test is to verify that when a GPC
     * tries to modify a studentId on their transcript an
     * InvalidUserException will be thrown
     */
    @Test
    public void testUpdateTranscriptStudentIdBlock() {
        try {
            //Set Kate Murry
            g.setUser("0000002");
            StudentRecord donnie = g.getTranscript("1000000");
            
            //Test Kate can not modify Donnie's studentId
            donnie.getStudent().setId("YOLO");
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
    public void testUpdateTranscriptInvalidId() {
        try {
            //Set Kate Murry
            g.setUser("0000002");
            StudentRecord donnie = g.getTranscript("1000000");
            
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
    public void testUpdateTranscriptNullId() {
        try {
            //Set Kate Murry
            g.setUser("0000002");
            StudentRecord donnie = g.getTranscript("1000000");
            
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
     */
    @Test
    public void testUpdateTranscriptInvalidAccess() {
        try {
            //Set Albert Einstein
            g.setUser("0030000");
            StudentRecord albert = g.getTranscript("0030000");
            
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
     */
    @Test
    public void testListOfStudentsProvision() {
        try {
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
        } catch (Exception e) {
            fail("Exception was not expected");
        }
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
    
    
}
