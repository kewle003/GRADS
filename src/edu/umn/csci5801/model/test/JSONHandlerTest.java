package edu.umn.csci5801.model.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.google.gson.Gson;

import edu.umn.csci5801.model.*;

public class JSONHandlerTest extends TestCase {
    private JSONHandler studentRecordJSON = new JSONHandler(
            "/Users/mark/Documents/workspace/GRADS_Materials/Data/students.txt");
    private List<StudentRecord> studentRecords;
    private JSONHandler userRecordJSON = new JSONHandler(
            "/Users/mark/Documents/workspace/GRADS_Materials/Data/users.txt");
    private List<Person> userRecords;
    
    @Override
    protected void setUp() {
        try {
            userRecords = userRecordJSON.readOutUsers();
            studentRecords = studentRecordJSON
                    .readOutStudentRecords();
        } catch (InvalidUserRoleException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testValidStudentRecords() {
        StudentRecord s1 = studentRecords.get(0);
        assertNotEquals(null, s1);

        StudentRecord s2 = studentRecords.get(1);
        assertNotEquals(null, s2);
    }

    @Test
    public void testValidStudents() {
        StudentRecord s1 = studentRecords.get(0);
        Student student = s1.getStudent();
        assertNotEquals(null, student);
        assertEquals("Luan", student.getFirstName());
        assertEquals("Nguyen", student.getLastName());
        assertEquals("nguy0621", student.getId());

        StudentRecord s2 = studentRecords.get(1);
        Student student2 = s2.getStudent();
        assertNotEquals(null, student2);
        assertEquals("gayxx067", student2.getId());
        assertEquals("Greg", student2.getFirstName());
        assertEquals("Gay", student2.getLastName());
        assertNotEquals(new Student("Greg", "Gay", "kewle003"), s2.getStudent());

    }

    @Test
    public void testValidDegree() {
        StudentRecord s1 = studentRecords.get(0);
        Degree degree = s1.getDegreeSought();
        assertEquals(Degree.PHD, degree);

        StudentRecord s2 = studentRecords.get(1);
        Degree degree2 = s2.getDegreeSought();
        assertEquals(null, degree2);
    }

    @Test
    public void testValidTerm() {
        StudentRecord s1 = studentRecords.get(0);
        Term term = s1.getTermBegan();
        assertNotEquals(null, term);
        assertEquals(Semester.SPRING, term.getSemester());
        assertEquals(new Integer(2008), term.getYear());

        StudentRecord s2 = studentRecords.get(1);
        Term term2 = s2.getTermBegan();
        assertEquals(null, term2);
    }

    @Test
    public void testValidDepartment() {
        StudentRecord s1 = studentRecords.get(0);
        Department department = s1.getDepartment();
        assertNotEquals(null, department);
        assertEquals(Department.COMPUTER_SCIENCE, department);

        StudentRecord s2 = studentRecords.get(1);
        Department department2 = s2.getDepartment();
        assertEquals(null, department2);
    }

    @Test
    public void testValidAdvisors() {
        StudentRecord s1 = studentRecords.get(0);
        assertEquals(new Professor("William", "Schuler",
                Department.COMPUTER_SCIENCE), s1.getAdvisors().get(0));

        StudentRecord s2 = studentRecords.get(1);
        assertEquals(new Professor("Mats", "Heimdahl",
                Department.COMPUTER_SCIENCE), s2.getAdvisors().get(0));
    }

    @Test
    public void testValidCommittee() {
        StudentRecord s1 = studentRecords.get(0);
        assertEquals(
                new Professor("Maria", "Gina", Department.COMPUTER_SCIENCE), s1
                        .getCommittee().get(0));
        assertEquals(new Professor("Daniel", "Boley",
                Department.COMPUTER_SCIENCE), s1.getCommittee().get(1));
        assertEquals(new Professor("Richard", "McGehee", Department.MATH), s1
                .getCommittee().get(2));
        assertNotEquals(new Professor("Richard", "McGehee",
                Department.COMPUTER_SCIENCE), s1.getCommittee().get(2));

        StudentRecord s2 = studentRecords.get(1);
        assertEquals(null, s2.getCommittee());
    }

    @Test
    public void testValidCoursesTaken() {
        StudentRecord s1 = studentRecords.get(0);
        assertEquals(new CourseTaken(new Course(
                "Advanced Algorithms and Data Structures", "csci5421", "3",
                null), new Term(Semester.SPRING, new Integer(2008)), Grade.A),
                s1.getCoursesTaken().get(0));
        assertEquals(new CourseTaken(new Course("Machine Learning", "csci5525",
                "3", null), new Term(Semester.SPRING, new Integer(2008)),
                Grade.A), s1.getCoursesTaken().get(1));
        assertEquals(new CourseTaken(new Course("Operating System", "csci5103",
                "3", null), new Term(Semester.SUMMER, new Integer(2008)),
                Grade.B), s1.getCoursesTaken().get(2));
        assertNotEquals(new CourseTaken(new Course(
                "Advanced Algorithms and Data Structures", "csci5421", "3",
                null), new Term(Semester.SPRING, new Integer(2008)), Grade.B),
                s1.getCoursesTaken().get(2));
        assertNotEquals(null, s1.getCoursesTaken().get(3));

        StudentRecord s2 = studentRecords.get(1);
        assertEquals(null, s2.getCoursesTaken());
    }

    @Test
    public void testValidMilestonesSet() {
        StudentRecord s1 = studentRecords.get(0);
        assertEquals(new MilestoneSet(Milestone.DEFENSE_PASSED, new Term(
                Semester.FALL, new Integer(2014))), s1.getMilestonesSet()
                .get(0));

        StudentRecord s2 = studentRecords.get(1);
        assertEquals(null, s2.getMilestonesSet());
    }

    @Test
    public void testValidNotes() {
        StudentRecord s1 = studentRecords.get(0);
        assertEquals("note1", s1.getNotes().get(0));
        assertEquals("note2", s1.getNotes().get(1));

        StudentRecord s2 = studentRecords.get(1);
        assertEquals("note1", s2.getNotes().get(0));
        assertEquals("note2", s2.getNotes().get(1));
        assertNotEquals(null, s2.getNotes().get(2));
        assertNotEquals("YOLOWSWAG", s2.getNotes().get(2));
    }

    @Test
    public void testValidAddStudent() {
        JSONHandler tempHandle = new JSONHandler("tempStudents.txt");
        List<Professor> advisors = new ArrayList<Professor>();
        List<Professor> committee = new ArrayList<Professor>();
        List<CourseTaken> courses = new ArrayList<CourseTaken>();
        List<MilestoneSet> milestones = new ArrayList<MilestoneSet>();
        List<String> notes = new ArrayList<String>();
        Student newStudent = new Student("Mark", "Kewley", "kewle003");
        Professor professor1 = new Professor("Patrick", "Severin",
                Department.COMPUTER_SCIENCE);
        Professor professor2 = new Professor("Kevin", "Vriatyosin",
                Department.MATH);
        Professor committeeMem = new Professor("Max", "Tran",
                Department.COMPUTER_SCIENCE);
        CourseTaken course1 = new CourseTaken(new Course(
                "Advanced Algorithms and Data Structures", "csci5421", "3",
                null), new Term(Semester.SUMMER, new Integer(2008)), Grade.C);
        MilestoneSet milestone = new MilestoneSet(Milestone.ORAL_PE_PASSED,
                new Term(Semester.FALL, new Integer(2009)));
        String note1 = new String("YOLOWSWAG!!");

        advisors.add(professor1);
        advisors.add(professor2);
        committee.add(committeeMem);
        courses.add(course1);
        milestones.add(milestone);
        notes.add(note1);

        StudentRecord record = new StudentRecord();
        record.setAdvisors(advisors);
        record.setCommittee(committee);
        record.setCoursesTaken(courses);
        record.setDegreeSought(Degree.PHD);
        record.setDepartment(Department.MATH);
        record.setMilestonesSet(milestones);
        record.setNotes(notes);
        record.setStudent(newStudent);
        record.setTermBegan(new Term(Semester.SPRING, new Integer(2004)));

        studentRecords.add(record);
        tempHandle.updateStudentRecords(studentRecords);

        List<StudentRecord> updatedRecords = new ArrayList<StudentRecord>();
        try {
            updatedRecords = tempHandle.readOutStudentRecords();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        StudentRecord newestRecord = updatedRecords.get(2);
        assertNotEquals(null, newestRecord);
        assertEquals(newStudent, newestRecord.getStudent());
        assertEquals(professor1, newestRecord.getAdvisors().get(0));
        assertEquals(professor2, newestRecord.getAdvisors().get(1));
        assertEquals(committeeMem, newestRecord.getCommittee().get(0));
        assertEquals(course1, newestRecord.getCoursesTaken().get(0));
        assertEquals(milestone, newestRecord.getMilestonesSet().get(0));
        assertEquals(Degree.PHD, newestRecord.getDegreeSought());
        assertEquals(Department.MATH, newestRecord.getDepartment());
        assertEquals(note1, newestRecord.getNotes().get(0));

        // Could also do just this:
        assertEquals(record, newestRecord);
    }

    @Test
    public void testValidUpdateStudentRecord() {
        JSONHandler tempHandle = new JSONHandler("tempStudents.txt");
        List<StudentRecord> tempStudentRecord = new ArrayList<StudentRecord>();
        try {
            tempStudentRecord = tempHandle
                    .readOutStudentRecords();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Student tempStudent = tempStudentRecord.get(0).getStudent();
        Student updateStudent = new Student("Mark", "Kewley", "Kewle003");
        tempStudentRecord.get(0).setStudent(updateStudent);
        tempHandle.updateStudentRecords(tempStudentRecord);

        try {
            tempStudentRecord = tempHandle.readOutStudentRecords();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertEquals(updateStudent, tempStudentRecord.get(0).getStudent());

        tempStudentRecord.get(0).setStudent(tempStudent);
        tempHandle.updateStudentRecords(tempStudentRecord);
    }
    
    @Test
    public void testValidUsers() {
        Person u1 = userRecords.get(0);
        assertEquals(true, u1 instanceof Student);

        Person u2 = userRecords.get(1);
        assertEquals(true, u2 instanceof Student);

        Person u3 = userRecords.get(2);
        assertNotEquals(true, u3 instanceof Student);

        Person u4 = userRecords.get(3);
        assertEquals(true, u4 instanceof GPC);

    }

}
