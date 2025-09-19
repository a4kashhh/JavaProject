package edu.ccrm.service;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;

import java.util.*;
import java.util.stream.Collectors;

public class CourseService {
    private final DataStore ds = DataStore.getInstance();

    public Course createCourse(Course course){ ds.getCourses().put(course.getCode(), course); return course; }
    public List<Course> listAll(){ return new ArrayList<>(ds.getCourses().values()); }
    public Optional<Course> find(String code){ return Optional.ofNullable(ds.getCourses().get(code)); }

    public List<Course> filterByInstructor(String instructor){
        return ds.getCourses().values().stream().filter(c -> c.getInstructor().equalsIgnoreCase(instructor)).collect(Collectors.toList());
    }
    public List<Course> filterByDepartment(String dept){
        return ds.getCourses().values().stream().filter(c -> c.getDepartment().equalsIgnoreCase(dept)).collect(Collectors.toList());
    }
    public List<Course> filterBySemester(Semester sem){
        return ds.getCourses().values().stream().filter(c -> c.getSemester()==sem).collect(Collectors.toList());
    }
}
