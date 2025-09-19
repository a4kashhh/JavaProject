package edu.ccrm.config;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory data storage for the CCRM application.
 * Implements the Singleton pattern to ensure a single shared instance
 * across the application. Stores student and course information using
 * thread-safe collections.
 */
public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    private DataStore(){}

    public static DataStore getInstance(){ return INSTANCE; }

    public Map<String, Student> getStudents(){ return students; }
    public Map<String, Course> getCourses(){ return courses; }

    // Initialize the system with some sample data for demonstration
    public void seedSample(){
        // Add some sample students
        Student student1 = new Student("1","Akash Kumar Pandey","akash@example.com");
        Student student2 = new Student("2","Aanchal Pandey","aanchal@example.com");
        students.put(student1.getId(), student1);
        students.put(student2.getId(), student2);
        
        // Add some sample courses
        Course dbmsCourse = new Course.Builder("CSE3001")
            .title("Database Management System")
            .credits(4)
            .instructor("Dr. Irfan Alam")
            .semester(Semester.FALL)
            .department("Computer Science")
            .build();
            
        Course javaCourse = new Course.Builder("CSE3002")
            .title("Programming in Java")
            .credits(3)
            .instructor("Dr. Sanat Jain")
            .semester(Semester.FALL)
            .department("Computer Science")
            .build();
            
        courses.put(dbmsCourse.getCode(), dbmsCourse);
        courses.put(javaCourse.getCode(), javaCourse);
    }
}
