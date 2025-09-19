package edu.ccrm.io;

import edu.ccrm.config.DataStore;
import edu.ccrm.domain.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class ImportExportService {
    private final DataStore ds = DataStore.getInstance();

    public void importStudents(Path csv) throws IOException {
        try(Stream<String> lines = Files.lines(csv)){
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for(String line: data){
                String[] parts = line.split(",");
                if(parts.length < 4) continue;
                
                String id = parts[0].trim();
                String name = parts[2].trim();
                String email = parts[3].trim();
                
                ds.getStudents().put(id, new Student(id, name, email));
            }
        }
    }

    public void importCourses(Path csv) throws IOException {
        try(Stream<String> lines = Files.lines(csv)){
            List<String> data = lines.skip(1).collect(Collectors.toList());
            for(String line: data){
                String[] parts = line.split(",");
                if(parts.length < 6) continue;
                
                String code = parts[0].trim();
                String title = parts[1].trim();
                int credits = Integer.parseInt(parts[2].trim());
                String instructor = parts[3].trim();
                Semester sem = Semester.valueOf(parts[4].trim());
                String dept = parts[5].trim();
                
                Course c = new Course.Builder(code)
                    .title(title)
                    .credits(credits)
                    .instructor(instructor)
                    .semester(sem)
                    .department(dept)
                    .build();
                    
                ds.getCourses().put(code, c);
            }
        }
    }

    public void exportAll(Path outDir) throws IOException {
        Files.createDirectories(outDir);
        Path studentsFile = outDir.resolve("students_export.csv");
        try(Stream<String> lines = ds.getStudents().values().stream()
            .map(s -> String.join(",", s.getId(), s.getFullName(), s.getEmail(), String.valueOf(s.getStatus())))){
            Files.write(studentsFile, (Iterable<String>)lines::iterator);
        }
        Path coursesFile = outDir.resolve("courses_export.csv");
        try(Stream<String> lines = ds.getCourses().values().stream()
            .map(c -> String.join(",", c.getCode(), c.getTitle(), String.valueOf(c.getCredits()), 
                c.getInstructor(), c.getSemester().toString(), c.getDepartment()))){
            Files.write(coursesFile, (Iterable<String>)lines::iterator);
        }
    }
}
