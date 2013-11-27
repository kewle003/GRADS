package edu.umn.csci5801.model.test;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.umn.csci5801.model.Course;
import edu.umn.csci5801.model.CourseTaken;
import edu.umn.csci5801.model.Degree;
import edu.umn.csci5801.model.Department;
import edu.umn.csci5801.model.GRADS;
import edu.umn.csci5801.model.Grade;
import edu.umn.csci5801.model.InvalidUserException;
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
//TODO: Black box tests; Environment A, B, and C
//Create our own JSON files
//Create our own StudentRecords and ProgressSummaries for each person
//We also need a cleanUp that resets the JSON database
public class GRADSTest extends TestCase {
    private GRADS g;
    private GRADS tempg;
    private List<StudentRecord> originalRecords;
    private JSONHandler tempStudentDatabase;
    private ProgressSummary albertsProgressSummary;
    private StudentRecord albertsStudentRecord;
    private ExpectedException exception = ExpectedException.none();
    
    @Override
    public void setUp() {
        tempStudentDatabase = new JSONHandler("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/tempStudents.txt");
        g = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envStudentRecords.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/courses.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envUsers.txt");
        tempg = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/src/resources/tempStudents.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/courses.txt", "/Users/mark/Documents/workspace/GRADS_Materials/src/resources/envUsers.txt");
        try {
            originalRecords = tempStudentDatabase.readOutStudentRecords();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            fail("Exception was not expected");
        }
        albertsProgressSummary = new ProgressSummary();
        albertsStudentRecord = new StudentRecord();
        //Setup albertsProgressSummary
        
        //Setup albertsStudentRecord, just follow the envStudentRecord.txt to set up your own
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
        Course c1 = new Course();
        c1.setName("Analysis of Numerical Algorithms");
        c1.setId("csci5302");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2008)), Grade.A));
        c1 = new Course();
        c1.setName("Operating Systems");
        c1.setId("csci5103");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2008)), Grade.A));
        c1 = new Course();
        c1.setName("Computer Science Colloquium");
        c1.setId("csci8970");
        c1.setNumCredits("1");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.S));
        c1 = new Course();
        c1.setName("Plan B Project");
        c1.setId("csci8760");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2009)), Grade.A));
        c1 = new Course();
        c1.setName("Advanced Operating Systems");
        c1.setId("csci8101");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.B));
        c1 = new Course();
        c1.setName("Understanding the Social Web");
        c1.setId("csci8117");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2011)), Grade.A));
        c1 = new Course();
        c1.setName("Software Engineering I");
        c1.setId("csci5801");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.A));
        c1 = new Course();
        c1.setName("Software Engineering II");
        c1.setId("csci5802");
        c1.setNumCredits("3");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.FALL, new Integer(2011)), Grade.A));
        c1 = new Course();
        c1.setName("Applied Fourier Analysis");
        c1.setId("math4248");
        c1.setNumCredits("4");
        albertsCourses.add(new CourseTaken(c1, new Term(Semester.SPRING, new Integer(2011)), Grade.A));
        c1 = new Course();
        c1.setName("Theory Of Probability And Statistics I");
        c1.setId("math5651");
        c1.setNumCredits("4");
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
            
            //Verify Kurt can not retrieve Albert's record
            g.setUser("0040000");
            alberts = g.getTranscript("0030000");
            fail("Expected an InvalidUserAccessException to be thrown");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
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
           // ProgressSummary summary = g.generateProgressSummary("0030000");
            //assertEquals(albertsProgressSummary, summary);
            g.setUser("0030000");
            //summary = g.generateProgressSummary("0030000");
            //assertEquals(albertsProgressSummary, summary);
        } catch (Exception e) {
            //e.printStackTrace();
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
        try {
            //Set Kate Murry
            tempg.setUser("0000002");
            
            //Reset the database
            tempStudentDatabase.updateStudentRecords(originalRecords);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            
            //Retrieve Donnie Wahlberg's transcript, he does not have a Notes section
            StudentRecord donnie = tempg.getTranscript("1000000");
            tempg.addNote("1000000", "He likes coffee");
            
            //Verify it was modified
            donnie = tempg.getTranscript("1000000");
            assertEquals("He likes coffee", donnie.getNotes().get(0));
            
            //Retrieve Albert's transcript, he does have a Notes section
            StudentRecord albert = tempg.getTranscript("0030000");
            tempg.addNote("0030000", "Smart chap");
            
            //Verify it was modified
            albert = tempg.getTranscript("0030000");
            assertEquals(true, albert.getNotes().contains("Smart chap"));
            
            //Reset the database
            tempStudentDatabase.updateStudentRecords(originalRecords);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            fail("Was not expecting an exception");
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
            
            //Reset the database
            tempStudentDatabase.updateStudentRecords(originalRecords);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            
            //Reset the database
            tempStudentDatabase.updateStudentRecords(originalRecords);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
            
            //Test Kate can not modify Donnie's studentId
            donnie.getStudent().setId("YOLO");
            tempg.updateTranscript("1000000", donnie);
            fail("Exepected InvalidUserException");
        } catch (Exception e) {
            // TODO Auto-generated catch block
        }
    }
    
    /**
     * The purpose of the Test Case is to verify that 
     * when a GPC through the interface makes a request 
     * to view the list of student ids.
     */
    @Test (expected = Exception.class)
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
            
            //Albert Einstein's id
            g.setUser("0030000");
            //throws InvalidUserAccessException
            List<String> studentIds2 = g.getStudentIDs();
            fail("Expected InvalidUserAccess Exception");
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    
    
}
