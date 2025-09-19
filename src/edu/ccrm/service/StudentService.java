package edu.ccrm.service;

import edu.ccrm.domain.Student;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Course;
import edu.ccrm.config.DataStore;
import edu.ccrm.util.CCRMExceptions.*;
import java.util.*;
import java.util.stream.*;

public class StudentService {
    private final DataStore ds = DataStore.getInstance();

    public List<Student> listAll(){
        return new ArrayList<>(ds.getStudents().values());
    }
    public Student createStudent(String id, String name, String email){
        Student s = new Student(id, name, email);
        ds.getStudents().put(id, s);
        return s;
    }
    public Optional<Student> findById(String id){ return Optional.ofNullable(ds.getStudents().get(id)); }

    public void enroll(String studentId, Course c) throws DuplicateEnrollmentException {
        Student s = ds.getStudents().get(studentId);
        if(s == null) throw new IllegalArgumentException("No such student");
        for(Enrollment e: s.getEnrollments()){
            if(e.getCourseCode().equals(c.getCode())) throw new DuplicateEnrollmentException("Already enrolled: " + c.getCode());
        }
        s.addEnrollment(new Enrollment(c.getCode(), c.getCredits()));
    }
}
