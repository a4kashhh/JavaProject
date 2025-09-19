package edu.ccrm.io;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

/**
 * Service for importing and exporting data in CSV format.
 * Handles reading student and course data from CSV files and 
 * writing current system data to CSV files for backup or sharing.
 * Uses Java NIO and Streams for efficient file processing.
 */
public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();

    public void importStudents(Path csv) throws IOException {
        try(Stream<String> lines = Files.lines(csv)){
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for(String line: data){
                String[] parts = line.split(",");
                if(parts.length < 4) continue;
                
                String studentId = parts[0].trim();
                // parts[1] contains registration number - not currently used in Student model
                String fullName = parts[2].trim();
                String emailAddress = parts[3].trim();
                
                ds.getStudents().put(studentId, new Student(studentId, fullName, emailAddress));
            }
        }
    }

    public void importCourses(Path csv) throws IOException {
        try(Stream<String> lines = Files.lines(csv)){
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for(String line: data){
                String[] parts = line.split(",");
                if(parts.length < 6) continue;
                
                String courseCode = parts[0].trim();
                String courseTitle = parts[1].trim();
                int creditHours = Integer.parseInt(parts[2].trim());
                String instructorName = parts[3].trim();
                Semester semester = Semester.valueOf(parts[4].trim());
                String department = parts[5].trim();
                
                Course course = new Course.Builder(courseCode)
                    .title(courseTitle)
                    .credits(creditHours)
                    .instructor(instructorName)
                    .semester(semester)
                    .department(department)
                    .build();
                    
                ds.getCourses().put(courseCode, course);
            }
        }
    }

    public void exportAll(Path outDir) throws IOException {
        Files.createDirectories(outDir);
        Path s = outDir.resolve("students_export.csv");
        try(Stream<String> lines = ds.getStudents().values().stream().map(st -> String.join(",", st.getId(), st.getFullName(), st.getEmail(), String.valueOf(st.getStatus())))){
            Files.write(s, (Iterable<String>)lines::iterator);
        }
        Path c = outDir.resolve("courses_export.csv");
        try(Stream<String> lines = ds.getCourses().values().stream().map(co -> String.join(",", co.getCode(), co.getTitle(), String.valueOf(co.getCredits()), co.getInstructor(), co.getSemester().toString(), co.getDepartment()))){
            Files.write(c, (Iterable<String>)lines::iterator);
        }
    }
}
