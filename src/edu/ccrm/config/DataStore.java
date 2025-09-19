package edu.ccrm.config;

import edu.ccrm.domain.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DataStore {
    private static final DataStore INSTANCE = new DataStore();

    private final Map<String, Student> students = new ConcurrentHashMap<>();
    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    private DataStore(){}

    public static DataStore getInstance(){ return INSTANCE; }

    public Map<String, Student> getStudents(){ return students; }
    public Map<String, Course> getCourses(){ return courses; }

    public void seedSample(){
        Student s1 = new Student("1","Akash Kumar Pandey","akash@example.com");
        Student s2 = new Student("2","Aanchal Pandey","aanchal@example.com");
        students.put(s1.getId(), s1);
        students.put(s2.getId(), s2);
        
        Course c1 = new Course.Builder("CSE3001")
            .title("Database Management System")
            .credits(4)
            .instructor("Dr. Irfan Alam")
            .semester(Semester.FALL)
            .department("Computer Science")
            .build();
            
        Course c2 = new Course.Builder("CSE3002")
            .title("Programming in Java")
            .credits(3)
            .instructor("Dr. Sanat Jain")
            .semester(Semester.FALL)
            .department("Computer Science")
            .build();
            
        courses.put(c1.getCode(), c1);
        courses.put(c2.getCode(), c2);
    }
}
