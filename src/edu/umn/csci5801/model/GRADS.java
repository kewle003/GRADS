package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * This is the main class that implements GRADSIntf 
 * in order to handle requests from the interface.
 * @author mark
 *
 */
//TODO: WE CANNOT ALLOW MATH GPCs TO ACCESS CS STUFF
//FIX: I skipped adding MATH gpc's  and users when reading in Users from the database
public class GRADS implements GRADSIntf {
    private HashMap<String, Student> students;
    private HashMap<String, StudentRecord> studentRecords;
    private HashMap<String, GPC> gpcs;
    private Person currentUser;
    private ProgressSummaryBuilder builder;
    
    //TODO: Add this to our design document
    private HashMap<String, Course> courses;
    private JSONHandler studentRecordDatabase;
    private JSONHandler userDatabase;
    private JSONHandler courseDatabase;
    private String studentRecordFile;
    private String userDatabaseFile;
    private String courseDatabaseFile;
    
    /**
     * Method used to initialize GRADS
     */
    public void initialize() {
        //Initialize all of GRADS data
        List<Person> users = new ArrayList<Person>();
        List<StudentRecord> records = new ArrayList<StudentRecord>();
        List<Course> coursesInDatabase = new ArrayList<Course>();
        this.studentRecordDatabase = new JSONHandler(studentRecordFile);
        this.userDatabase = new JSONHandler(userDatabaseFile);
        this.courseDatabase = new JSONHandler(courseDatabaseFile);
        this.students = new HashMap<String, Student>();
        this.studentRecords = new HashMap<String, StudentRecord>();
        this.gpcs = new HashMap<String, GPC>();
        this.courses = new HashMap<String, Course>();
        //TODO: KEVIN add this in when ready
        this.builder = null;
        
        //Read the data from our JSON database
        try {
            users = userDatabase.readOutUsers();
            records = studentRecordDatabase.readOutStudentRecords();
            coursesInDatabase = courseDatabase.readOutCourses();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
       
        //Populate GRADS users HashMap (students/gpcs)
        if (users != null) {
            Iterator<Person> userIterator = users.iterator();
            while (userIterator.hasNext()) {
                Person p = userIterator.next();
                if (p instanceof Student) {
                    Student student = (Student) p;
                    //Store student id as key and Student as value
                    students.put(student.getId(), student);
                } else {
                    GPC gpc = (GPC) p;
                    //Store GPC id as key and GPC as value
                    gpcs.put(gpc.getId(), gpc);
                }
            }
        }
        
        //Populate our Course HashMap
        Iterator<Course> courseIterator = coursesInDatabase.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next();
            courses.put(course.getId(), course);
        }
        
        //Populate the StudentRecord HashMap
        Iterator<StudentRecord> recordIterator = records.iterator();
        while (recordIterator.hasNext()) {
            StudentRecord record = recordIterator.next();
            studentRecords.put(record.getStudent().getId(), record);
        }
    }
    
    /**
     * Our GRADS constructor
     * @param userDatabaseFile - The file directory where the users JSON lies
     * @param studentRecordFile - The file directory where the student records JSON lies
     * @param courseDatabaseFile - The file directory where the courses JSON lies
     */
    public GRADS(String userDatabaseFile, String studentRecordFile, String courseDatabaseFile) {
        this.userDatabaseFile = userDatabaseFile;
        this.studentRecordFile = studentRecordFile;
        this.courseDatabaseFile = courseDatabaseFile;
        initialize();
    }

    @Override
    public void setUser(String userId) throws Exception {
        //Verify if the user is a student or gpc, if not throw an exception
        if (userId != null) {
            if (students.containsKey(userId)) {
                currentUser = students.get(userId);
            } else if (gpcs.containsKey(userId)) {
                currentUser = gpcs.get(userId);
            } else {
                throw new InvalidUserException("UserId: " +userId+ " does not exist in the database or is not allowed to access GRADS currently");
            }
        } else {
            throw new InvalidDataException("null userId given!");
        }
    }

    @Override
    public String getUser() {
        if (currentUser instanceof GPC) {
            GPC g = (GPC) currentUser;
            return g.getId();
        } else {
            Student s = (Student) currentUser;
            return s.getId();
        }
    }

    @Override
    public List<String> getStudentIDs() throws Exception {
        if (isGPC()) {
            List<String> studentIds = new ArrayList<String>();
            studentIds.addAll(students.keySet());
            return studentIds;
        } else {
            throw new InvalidUserAccessException("You do not have permission to retrieve studentIds");
        }

    }

    //TODO: Should just return a copy so user does not
    //directly affect the underlying StudentRecord
    @Override
    public StudentRecord getTranscript(String userId) throws Exception {
        if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
            if (studentRecords.containsKey(userId)) {
                return studentRecords.get(userId);
            } else {
                throw new InvalidUserException("UserId: " +userId+ " does not exist in our database");
            }
        } else {
            throw new InvalidUserAccessException("You do not have permission to access " +userId+ "'s transcript");
        }
    }

    //TODO: We have to block a GPC from entering in invalid Grades, Milestones, Courses, etc...
    //UPDATE: Greg says we only need to worry about blocking a GPC from changing studentIds for now
    //FIX1: Disallowed the GPC from accidentally overwriting the wrong transcript
    @Override
    public void updateTranscript(String userId, StudentRecord transcript)
            throws Exception {
        if (isGPC()) {
            if (userId != null) {
                if (studentRecords.containsKey(userId)) {
                    StudentRecord oldRecord = studentRecords.get(userId);
                    //System.out.println("StudentId " +oldRecord.getStudent().getId());
                    //Sanity check, we can not modify a studentId
                    //TODO: Read getTranscript() todo
                    if (oldRecord.getStudent().getId().equals(transcript.getStudent().getId())) {
                        validateCourses(transcript.getCoursesTaken());
                        studentRecords.put(userId, transcript);
                        updateDatabase();
                    } else {
                        throw new InvalidDataException("Wrong studentId given!");
                    }
                } else {
                    throw new InvalidUserException("UserId: " +userId+ " does not exist in the database");
                }
            } else {
                throw new InvalidDataException("null userId given!");
            }
         } else {
             throw new InvalidUserAccessException("You do not have permission to update " +userId+ "'s transcript");
         }

    }

    @Override
    public void addNote(String userId, String note) throws Exception {
        if (isGPC()) {
            if (userId != null) {
                if (studentRecords.containsKey(userId)) {
                    studentRecords.get(userId).getNotes().add(note);
                    updateDatabase();
                } else {
                    throw new InvalidUserException("UserId: " +userId+ " does not exist in the database");
                }
            } else {
                throw new InvalidDataException("null userId given!");
            }
        } else {
            throw new InvalidUserAccessException("You do not have permission to add a note to " +userId+ "'s transcript");
        }
    }

    @Override
    public ProgressSummary generateProgressSummary(String userId)
            throws Exception {
        if (userId != null) {
            //If the user is GPC good, otherwise we just need to check that a student is accessing his/her's student record
            if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
                if (studentRecords.containsKey(userId)) {
                    ProgressSummary summary = builder.generateProgressSummary(studentRecords.get(userId));
                    return summary;
                } else {
                    throw new InvalidUserException("UserId: " +userId+ " does not exist in the database");
                }
            } else {
                throw new InvalidUserAccessException("You do not have permission to generate " +userId+"'s progress summary");
            }
        } else {
            throw new InvalidDataException("null userId given!");
        }
    }

    @Override
    public ProgressSummary simulateCourses(String userId,
            List<CourseTaken> courses) throws Exception {
        if (userId != null && courses != null) {
            ProgressSummary summary = null;
        
            if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
                if (studentRecords.containsKey(userId)) {
                    //TODO: This directly affects the StudentRecord of the student
                    //We need a way of getting a copy (I tried clone(), maybe another way?)
                    //We could just save his courses then undo our changes after generating the summary
                    StudentRecord recordCopy = studentRecords.get(userId);
                    //We are assuming we are adding the courses to the student's existing courses
                    List<CourseTaken> originalCourses = recordCopy.getCoursesTaken(); 
                    Iterator<CourseTaken> newCoursesIterator = courses.iterator();
                    while (newCoursesIterator.hasNext()) {
                        recordCopy.getCoursesTaken().add(newCoursesIterator.next());
                    }
                    validateCourses(recordCopy.getCoursesTaken());
                    summary = builder.generateProgressSummary(recordCopy);
                    recordCopy.setCoursesTaken(originalCourses);
                    //Could do something like this: recordCopy.setCoursesTaken(originalCourses);
                    return summary;
                } else {
                    throw new InvalidUserException("User " +userId+ " does not exist in our database");
                }
            } else {
                throw new InvalidUserAccessException("You do not have permission to do this");
            }
        } else {
            throw new InvalidDataException("null userId or courses given!");
        }
    }
    
    /**
     * Method used to update the students.txt JSON database
     */
    private void updateDatabase() {
        List<StudentRecord> newStudentRecords = new ArrayList<StudentRecord>();
        newStudentRecords.addAll(studentRecords.values());
        studentRecordDatabase.updateStudentRecords(newStudentRecords);
    }
    
    /**
     * Method used to check whether or not the userId corresponds
     * to a GPC
     * @return
     */
    private boolean isGPC() {
        return (currentUser instanceof GPC);
    }
    
    /**
     * Method used to determine whether or not the userId has access to
     * the StudentRecord of the Student whose id is the studentId
     * @param userId - current user
     * @param studentId - id requested
     * @return true if the userId is equal to the studentId, false otherwise
     */
    private boolean hasAccessToStudentRecord(String userId, String studentId) {
        return userId.equals(studentId);
    }
    
    /**
     * Method used to determine if the list of courses provided
     * actually exist in the database
     * @param listOfCourses - the List<Course> to be verified by GRADS
     * @throws Exception - InvalidCourseException
     */
    //TODO: Add this to our design
    private void validateCourses(List<CourseTaken> listOfCourses) throws Exception {
        Iterator<CourseTaken> courseIterator = listOfCourses.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next().getCourse();
            if (courses.containsKey(course.getId())) {
                if (!course.equals(courses.get(course.getId()))) {
                    throw new InvalidCourseException("CourseId: " +course.getId()+ " has invalid data");
                } 
            } else {
                throw new InvalidCourseException("CourseId: " +course.getId()+ " does not exist in the database");
            }
        }
    }
    
    //TODO: How do we just return a copy of StudentRecord?
    //REMOVE WHEN FINISHED
    public static void main(String[] args) {
        GRADS g = new GRADS("/Users/mark/Documents/workspace/GRADS_Materials/Data/users.txt", "/Users/mark/Documents/workspace/GRADS_Materials/Data/students.txt", "/Users/mark/Documents/workspace/GRADS_Materials/Data/courses.txt");
        try {
            g.setUser("tolas9999");
           // g.addNote("kewle003", "Meet me tonight at 3'oclock");
            StudentRecord r = g.getTranscript("nguy0621");
            //g.validateCourses(r.getCoursesTaken());
            //r.getCommittee().clear();
            Course invalidCourse = new Course();
            invalidCourse.setId("csci9000");
            invalidCourse.setName("Google studies");
            invalidCourse.setNumCredits("3");
            r.getCoursesTaken().add(new CourseTaken(invalidCourse, new Term(Semester.FALL, new Integer(2008)), Grade.C));
            g.updateTranscript("nguy0621", r);
            //StudentRecord r = g.getTranscript("nguy0621");
           // r.getStudent().setId("gayxx070");
           // g.updateTranscript("gayxx067", r);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
