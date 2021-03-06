package edu.umn.csci5801.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import edu.umn.csci5801.model.exception.InvalidCourseException;
import edu.umn.csci5801.model.exception.InvalidDataException;
import edu.umn.csci5801.model.exception.InvalidUserAccessException;
import edu.umn.csci5801.model.exception.InvalidUserException;


/**
 * This is the main class that implements GRADSIntf 
 * in order to handle requests from the interface.
 * 
 * @author mark
 *
 */
public class GRADS implements GRADSIntf {
    private HashMap<String, Student> students;
    private HashMap<String, StudentRecord> studentRecords;
    private HashMap<String, GPC> gpcs;
    private Person currentUser;
    private ProgressSummaryBuilder builder;
    
    private HashMap<String, Course> courses;
    private Database studentRecordDatabase;
    private Database userDatabase;
    private Database courseDatabase;
    private String userDatabaseFile;
    private String studentRecordFile;
    private String courseDatabaseFile;
    
    /**
     * Our GRADS constructor
     * 
     * @param studentRecordFile - The file directory where the student records JSON lies
     * @param courseDatabaseFile - The file directory where the courses JSON lies
     * @param userDatabaseFile - The file directory where the users JSON lies
     */
    public GRADS(String studentRecordFile, String courseDatabaseFile, String userDatabaseFile) {
        this.userDatabaseFile = userDatabaseFile;
        this.studentRecordFile = studentRecordFile;
        this.courseDatabaseFile = courseDatabaseFile;
        initializeGRADS();
    }
    
    /**
     * Method used to initialize GRADS
     * 
     */
    public void initializeGRADS() {
        //Initialize all of GRADS data
        List<Person> users = new ArrayList<Person>();
        List<StudentRecord> records = new ArrayList<StudentRecord>();
        List<Course> coursesInDatabase = new ArrayList<Course>();
        this.studentRecordDatabase = new Database(studentRecordFile);
        this.userDatabase = new Database(userDatabaseFile);
        this.courseDatabase = new Database(courseDatabaseFile);
        this.students = new HashMap<String, Student>();
        this.studentRecords = new HashMap<String, StudentRecord>();
        this.gpcs = new HashMap<String, GPC>();
        this.courses = new HashMap<String, Course>();
        this.builder = new ProgressSummaryBuilder();

        
        //Read the data from our JSON database
        //If there is no data in the files, the methods will throw an exception
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
        
        //Set up our StudentRecord to have default values if needed
        setStudentRecordDefaults(records);
        
        //Populate the StudentRecord HashMap
        Iterator<StudentRecord> recordIterator = records.iterator();
        while (recordIterator.hasNext()) {
            StudentRecord record = recordIterator.next();
            studentRecords.put(record.getStudent().getId(), record);
        }
        
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

    @Override
    public StudentRecord getTranscript(String userId) throws Exception {
        if (isGPC() || hasAccessToStudentRecord(this.getUser(), userId)) {
            if (studentRecords.containsKey(userId)) {
                if (studentRecords.get(userId).getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                    StudentRecord record = studentRecords.get(userId);
                    StudentRecord recordToReturn = record.clone();
                    return recordToReturn;
                } else {
                    throw new InvalidUserAccessException("You do not have permission to access a student outside your deparment");
                }
            } else {
                throw new InvalidUserException("UserId: " +userId+ " does not exist in our database");
            }
        } else {
            throw new InvalidUserAccessException("You do not have permission to access " +userId+ "'s transcript");
        }
    }

    @Override
    public void updateTranscript(String userId, StudentRecord transcript)
            throws Exception {
        if (isGPC()) {
            if (userId != null && transcript != null) {
                if (transcript.getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                    if (studentRecords.containsKey(transcript.getStudent().getId())) {
                        if (transcript.getStudent().getId().equals(userId)) {
                            validateCSCourses(transcript.getCoursesTaken());
                            studentRecords.put(userId, transcript);
                            updateDatabase();
                        } else {
                            throw new InvalidDataException("UserId: " +userId+ " does not match the transcript provided");
                        }
                    } else {
                        throw new InvalidUserException("UserId: " +userId+ " does not exist in the database or you can not change studentId");
                    }
                } else {
                    throw new InvalidUserAccessException("You do not have permission to access a student outside your deparment");
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
                    if (studentRecords.get(userId).getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                        studentRecords.get(userId).getNotes().add(note);
                        updateDatabase();
                    } else {
                        throw new InvalidUserAccessException("You do not have permission to access a student outside your deparment");
                    }
                } else {
                    throw new InvalidUserException("UserId: " +userId+ " does not exist in the database or is not in your department");
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
                    if (studentRecords.get(userId).getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                        ProgressSummary summary = builder.generateProgressSummary(studentRecords.get(userId));
                        if (summary == null) {
                            throw new InvalidDataException("Progress Summary could not be generated");
                        }
                        return summary;
                    } else {
                        throw new InvalidUserAccessException("You do not have permission to access a student outside your deparment");
                    }
                } else {
                    throw new InvalidUserException("UserId: " +userId+ " does not have a student record");
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
                    if (!studentRecords.get(userId).getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                        throw new InvalidUserAccessException("You do not have permission to access a student outside your deparment");
                    }

                    StudentRecord recordCopy = getTranscript(userId);
                    List<CourseTaken> originalCourses = recordCopy.getCoursesTaken();              
                    Iterator<CourseTaken> newCoursesIterator = courses.iterator();
                    //Add the new courses to the list of already completed courses
                    while (newCoursesIterator.hasNext()) {
                        recordCopy.getCoursesTaken().add(newCoursesIterator.next());
                    }
                    
                    summary = builder.generateProgressSummary(recordCopy);
                    //Reset the changes to the StudentRecord
                    recordCopy.setCoursesTaken(originalCourses);
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
        //Clean up anything we modified for GRADS
        cleanForDatabaseUpdate(newStudentRecords);
        studentRecordDatabase.updateStudentRecords(newStudentRecords);
        //Reset the defaults
        setStudentRecordDefaults(newStudentRecords);
    }
    
    /**
     * Method used to cleanup a student record that was modified
     * by GRADS for implementation purposes.
     * @param records - a list of StudentRecord objects
     */
    private void cleanForDatabaseUpdate(List<StudentRecord> records) {
        for (StudentRecord record : records) {
            for (CourseTaken course : record.getCoursesTaken()) {
                //Reset courseAreas
                if (course.getCourse() != null) {
                    course.getCourse().setCourseArea(null);
                }
            }
            
            //Cleanup any lists that are empty
            if (record.getAdvisors().isEmpty()) {
                record.setAdvisors(null);
            }
            
            if (record.getCommittee().isEmpty()) {
                record.setCommittee(null);
            }
            
            if (record.getCoursesTaken().isEmpty()) {
                record.setCoursesTaken(null);
            }
            
            if (record.getMilestonesSet().isEmpty()) {
                record.setMilestonesSet(null);
            }
            
            if (record.getNotes().isEmpty()) {
                record.setNotes(null);
            }
            
            if (record.getDepartment().equals(Department.NONE)) {
                record.setDepartment(null);
            }
        }
    }
    
    /**
     * Method used to set default values to null values found
     * in the StudentRecord as well as set CourseAreas to each course
     * in a student record
     * @param records - a list of StudentRecord objects
     */
    private void setStudentRecordDefaults(List<StudentRecord> records) {
        for (StudentRecord record : records) {
            if (record.getCoursesTaken() == null) {
                record.setCoursesTaken(new ArrayList<CourseTaken>());
            } else {
                for (CourseTaken course : record.getCoursesTaken()) {
                    if (courses.containsKey(course.getCourse().getId())) {
                            course.getCourse().setCourseArea(courses.get(course.getCourse().getId()).getCourseArea());
                    }
                }
            }
            
            //Assign default values to each list
            if (record.getAdvisors() == null) {
                record.setAdvisors(new ArrayList<Professor>());
            }
            
            if (record.getCommittee() == null) {
                record.setCommittee(new ArrayList<Professor>());
            }
            
            if (record.getMilestonesSet() == null) {
                record.setMilestonesSet(new ArrayList<MilestoneSet>());
            }
            
            if (record.getNotes() == null) {
                record.setNotes(new ArrayList<String>());
            }
            
            if (record.getDepartment() == null) {
                record.setDepartment(Department.NONE);
            }
        }
    }
    
    /**
     * Method used to check whether or not the userId corresponds
     * to a GPC
     * 
     * @return true if the currentUser is a GPC, false otherwise
     */
    private boolean isGPC() {
        return (currentUser instanceof GPC);
    }
    
    /**
     * Method used to determine whether or not the userId has access to
     * the StudentRecord of the Student whose id is the studentId
     * 
     * @param userId - current user
     * @param studentId - id requested
     * @return true if the userId is equal to the studentId, false otherwise
     */
    private boolean hasAccessToStudentRecord(String userId, String studentId) {
        return userId.equals(studentId);
    }
    
    /**
     * Method used to determine if the list of courses provided
     * actually exist in the database for Computer Science courses only.
     * 
     * GREG - said that we do not need to worry about course validation for this assignment
     * 
     * @param listOfCourses - the List<Course> to be verified by GRADS
     * @throws Exception - InvalidCourseException
     */
    private void validateCSCourses(List<CourseTaken> listOfCourses) throws Exception {
        Iterator<CourseTaken> courseIterator = listOfCourses.iterator();
        while (courseIterator.hasNext()) {
            Course course = courseIterator.next().getCourse();
            if (course.getId().matches("csci.*")) {
                if (courses.containsKey(course.getId())) {
                	Course courseInDatabase = courses.get(course.getId());
                    if (!course.equals(courseInDatabase)) {
                        throw new InvalidCourseException("CourseId: " +course.getId()+ " has invalid data");
                    }
                } else {
                    throw new InvalidCourseException("CourseId: " +course.getId()+ " does not exist in the database");
                }
            }
        }
    }
}
