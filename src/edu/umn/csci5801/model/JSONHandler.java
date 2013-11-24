package edu.umn.csci5801.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

/**
 * This will handle all database transactions that GRADS requests
 * @author mark
 * 
 */
public class JSONHandler {
    private String filename;

    /**
     * @param filename
     *            - the location of the JSON file we wish to read
     */
    public JSONHandler(String filename) {
        this.filename = filename;
    }

    /**
     * This will retrieve the list of all StudentRecord objects in the JSON
     * database. For now we do not allow MATH majors into our StudentRecords.
     * @return List of StudentRecord objects
     */
    public List<StudentRecord> readOutStudentRecords() throws Exception {
        List<StudentRecord> studentRecords = null;
        try {
            studentRecords = new Gson().fromJson(new FileReader(new File(
                    filename)), new TypeToken<List<StudentRecord>>() {
            }.getType());
            Iterator<StudentRecord> iterator = studentRecords.iterator();
            while (iterator.hasNext()) {
                StudentRecord r = iterator.next();
                if (r.getDepartment() != null) {
                    //Sanity check, we can not allow MATH majors in our student records
                    if(r.getDepartment().equals(Department.MATH)) {
                        throw new InvalidUserException("Math majors are not allowed in student records");
                    }
                }
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return studentRecords;
    }

    /**
     * This will retrieve a list of all Course objects in the JSON database
     * 
     * @return List of Course objects
     */
    public List<Course> readOutCourses() throws Exception {
        List<Course> listOfCourses = null;
        try {
            listOfCourses = new Gson().fromJson(new FileReader(new File(
                    filename)), new TypeToken<List<Course>>() {
            }.getType());
            if (listOfCourses == null) {
                throw new NoDataFoundException("No data found in JSON file " +filename);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return listOfCourses;
    }

    /**
     * This will retrieve a list of all User objects in the JSON database.
     * For now we only allow COMPUTER_SCIENCE users into GRADS.
     * @return - a List of Person objects that are either GPCs or Students
     */
    public List<Person> readOutUsers() throws Exception {
        List<UserData> listOfUsers = null;
        List<Person> listOfPeople = new ArrayList<Person>();
        try {
            listOfUsers = new Gson().fromJson(
                    new FileReader(new File(filename)),
                    new TypeToken<List<UserData>>() {
                    }.getType());
            if (listOfUsers != null) {
                Iterator<UserData> usersIterator = listOfUsers.iterator();
                while (usersIterator.hasNext()) {
                    UserData user = usersIterator.next();
                    if (user.getRole() == null) {
                        throw new InvalidUserRoleException("null user role");
                    }
                    if (user.getRole().equals(Role.STUDENT)) {
                        //Only allow COMPUTER_SCIENCE Student's into GRADS
                        if (user.getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                            Student student = new Student(
                                    user.getUser().getFirstName(), user.getUser()
                                            .getLastName(), user.getUser().getId());
                            listOfPeople.add(student);
                        }
                    } else if (user.getRole().equals(
                            Role.GRADUATE_PROGRAM_COORDINATOR)) {
                        //Only allow COMPUTER_SCIENCE GPC's into GRADS
                        if (user.getDepartment().equals(Department.COMPUTER_SCIENCE)) {
                            GPC gpc = new GPC(user.getUser().getFirstName(), user.getUser().getLastName(), user.getUser().getId());
                            listOfPeople.add(gpc);
                        }
                    } else {
                        throw new InvalidUserRoleException("invalid role");
                    }
                }
            } else {
                throw new NoDataFoundException("No data found in JSON file " +filename);
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return listOfPeople;
    }

    /**
     * This will update the student records in the JSON
     * 
     * @param records
     *            a list of StudentRecord objects
     */
    public void updateStudentRecords(List<StudentRecord> records) {
        String fileToWrite = new GsonBuilder().setPrettyPrinting().create()
                .toJson(records);
        try {
            FileWriter file = new FileWriter(filename);
            file.write(fileToWrite);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
