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
 * 
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
     * database
     * 
     * @return List of StudentRecord objects
     */
    public List<StudentRecord> readOutStudentRecords() {
        List<StudentRecord> studentRecords = null;
        try {
            studentRecords = new Gson().fromJson(new FileReader(new File(
                    filename)), new TypeToken<List<StudentRecord>>() {
            }.getType());
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return studentRecords;
    }

    /**
     * This will retrieve a list of all Course objects in the JSON database
     * 
     * @return List of Course objects
     */
    public List<Course> readOutCourses() {
        List<Course> listOfCourses = null;
        try {
            listOfCourses = new Gson().fromJson(new FileReader(new File(
                    filename)), new TypeToken<List<Course>>() {
            }.getType());
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return listOfCourses;
    }

    // TODO: Add exception handling
    /**
     * This will retrieve a list of all User objects in the JSON database
     * 
     * @return
     */
    public List<Person> readOutUsers() throws Exception {
        List<UserData> listOfUsers = null;
        List<Person> listOfPeople = new ArrayList<Person>();
        try {
            listOfUsers = new Gson().fromJson(
                    new FileReader(new File(filename)),
                    new TypeToken<List<UserData>>() {
                    }.getType());
            Iterator<UserData> usersIterator = listOfUsers.iterator();
            while (usersIterator.hasNext()) {
                UserData user = usersIterator.next();
                if (user.getRole().equals(Role.STUDENT)) {
                    Student student = new Student(
                            user.getUser().getFirstName(), user.getUser()
                                    .getLastName(), user.getUser().getId());
                    listOfPeople.add(student);
                } else if (user.getRole().equals(
                        Role.GRADUATE_PROGRAM_COORDINATOR)) {
                    // TODO: Need GPC
                } else {
                    // throw Exception;
                }
            }
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
