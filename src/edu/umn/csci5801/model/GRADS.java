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
public class GRADS implements GRADSIntf {
    private HashMap<String, Student> students;
    private HashMap<String, StudentRecord> studentRecords;
    private HashMap<String, GPC> gpcs;
    private Person currentUser;
    private ProgressSummaryBuilder builder;
    private JSONHandler studentRecordDatabase;
    private JSONHandler userDatabase;
    private String studentRecordFile;
    private String userDatabaseFile;
    
    /**
     * Method used to initialize GRADS
     */
    public void initialize() {
        List<Person> users = null;
        List<StudentRecord> records = null;
        this.studentRecordDatabase = new JSONHandler(studentRecordFile);
        this.userDatabase = new JSONHandler(userDatabaseFile);
        this.students = new HashMap<String, Student>();
        this.studentRecords = new HashMap<String, StudentRecord>();
        this.gpcs = new HashMap<String, GPC>();
        //TODO: KEVIN add this in when ready
        this.builder = null;
        
        try {
            users = userDatabase.readOutUsers();
            //TODO: MARK - add exception for studentRecords?
            records = studentRecordDatabase.readOutStudentRecords();
        } catch (Exception e) {
            e.printStackTrace();
        }
       
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
        
        Iterator<StudentRecord> recordIterator = records.iterator();
        while (recordIterator.hasNext()) {
            StudentRecord record = recordIterator.next();
            studentRecords.put(record.getStudent().getId(), record);
        }
    }
    
    public GRADS(String userDatabaseFile, String studentRecordFile) {
        this.userDatabaseFile = userDatabaseFile;
        this.studentRecordFile = studentRecordFile;
        initialize();
    }

    @Override
    public void setUser(String userId) throws Exception {
        if (students.containsKey(userId)) {
            currentUser = students.get(userId);
        } else if (gpcs.containsKey(userId)) {
            currentUser = gpcs.get(userId);
        } else {
            throw new InvalidUserException("User id " +userId+ " does not exist in our database");
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
            throw new InvalidUserAccessException("You do not have permission to do this");
        }

    }

    @Override
    public StudentRecord getTranscript(String userId) throws Exception {
        if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
            return studentRecords.get(userId);
        } else {
            throw new InvalidUserAccessException("You do not have permission to access student records");
        }
    }

    @Override
    public void updateTranscript(String userId, StudentRecord transcript)
            throws Exception {
        if (userId != null) {
            if (isGPC()) {
                if (studentRecords.containsKey(userId)) {
                    studentRecords.put(userId, transcript);
                    updateDatabase();
                } else {
                    throw new InvalidUserException("User " +userId+ " does not exist in our database");
                }
            } else {
                throw new InvalidUserAccessException("You do not have permission to do this");
            }
        }

    }

    @Override
    public void addNote(String userId, String note) throws Exception {
        if (userId != null) {
            if (isGPC()) {
                if (studentRecords.containsKey(userId)) {
                    studentRecords.get(userId).getNotes().add(note);
                    updateDatabase();
                } else {
                    throw new InvalidUserException("User " +userId+ " does not exist in the database");
                }
            } else {
                throw new InvalidUserAccessException("You do not have permission to add a note to " +userId+ "'s student record");
            }
        } else {
            throw new InvalidDataException("null userId given!");
        }
    }

    @Override
    public ProgressSummary generateProgressSummary(String userId)
            throws Exception {
        if (userId != null) {
            StudentRecord recordToProcess = null;
            //If the user is GPC good, otherwise we just need to check that a student is accessing HIS student record
            if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
                recordToProcess = studentRecords.get(userId);
                if (recordToProcess == null) {
                    throw new InvalidUserException("User " +userId+ " does not have a student record");
                }
                ProgressSummary summary = builder.generateProgressSummary(recordToProcess);
                return summary;
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
                    StudentRecord recordCopy = studentRecords.get(userId);
                    //TODO: Are we assuming that the new list passed in does not contain existing courses?
                    //If so then keep this code
                    List<CourseTaken> coursesTakenSoFar = recordCopy.getCoursesTaken();
                    Iterator<CourseTaken> newCoursesIterator = courses.iterator();
                    while (newCoursesIterator.hasNext()) {
                        coursesTakenSoFar.add(newCoursesIterator.next());
                    }
                
                    recordCopy.setCoursesTaken(coursesTakenSoFar);
                    summary = builder.generateProgressSummary(recordCopy);
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
}
