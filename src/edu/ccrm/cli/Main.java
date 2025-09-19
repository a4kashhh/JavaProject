package edu.ccrm.cli;

import edu.ccrm.config.DataStore;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.domain.*;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStore ds = DataStore.getInstance();
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final ImportExportService ioService = new ImportExportService();

    public static void main(String[] args) {
        ds.seedSample();
        System.out.println("CCRM v1.0");
        boolean running = true;
        while(running){
            printMenu();
            String choice = scanner.nextLine().trim();
            switch(choice){
                case "1" -> students();
                case "2" -> courses();
                case "3" -> enroll();
                case "4" -> importExport();
                case "5" -> backup();
                case "6" -> running = false;
                default -> System.out.println("Invalid option");
            }
        }
    }

    private static void printMenu(){
        System.out.println("\n1. Students  2. Courses  3. Enroll  4. Import/Export  5. Backup  6. Exit");
        System.out.print("Choice: ");
    }

    private static void students(){
        System.out.println("Students:");
        studentService.listAll().forEach(System.out::println);
        System.out.print("Add new? (y/n): ");
        if(scanner.nextLine().trim().equalsIgnoreCase("y")){
            System.out.print("ID: "); String id = scanner.nextLine().trim();
            System.out.print("Name: "); String name = scanner.nextLine().trim();
            System.out.print("Email: "); String email = scanner.nextLine().trim();
            studentService.createStudent(id, name, email);
            System.out.println("Added");
        }
    }

    private static void courses(){
        System.out.println("Courses:");
        courseService.listAll().forEach(System.out::println);
        System.out.print("Add new? (y/n): ");
        if(scanner.nextLine().trim().equalsIgnoreCase("y")){
            System.out.print("Code: "); String code = scanner.nextLine().trim();
            System.out.print("Title: "); String title = scanner.nextLine().trim();
            System.out.print("Credits: "); int credits = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Instructor: "); String instructor = scanner.nextLine().trim();
            System.out.print("Department: "); String dept = scanner.nextLine().trim();
            Course c = new Course.Builder(code).title(title).credits(credits)
                .instructor(instructor).department(dept).build();
            courseService.createCourse(c);
            System.out.println("Added");
        }
    }

    private static void enroll(){
        System.out.print("Student ID: "); String sid = scanner.nextLine().trim();
        System.out.print("Course code: "); String code = scanner.nextLine().trim();
        var course = courseService.find(code);
        if(course.isEmpty()){ 
            System.out.println("Course not found"); 
            return; 
        }
        try {
            studentService.enroll(sid, course.get());
            System.out.println("Enrolled");
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void importExport(){
        System.out.println("1. Import Students  2. Import Courses  3. Export");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();
        try{
            switch(opt){
                case "1" -> {
                    System.out.print("File: "); String path = scanner.nextLine().trim();
                    ioService.importStudents(Paths.get(path));
                    System.out.println("Imported");
                }
                case "2" -> {
                    System.out.print("File: "); String path = scanner.nextLine().trim();
                    ioService.importCourses(Paths.get(path));
                    System.out.println("Imported");
                }
                case "3" -> {
                    System.out.print("Directory: "); String dir = scanner.nextLine().trim();
                    ioService.exportAll(Paths.get(dir));
                    System.out.println("Exported");
                }
            }
        } catch(Exception e){ 
            System.out.println("Error: " + e.getMessage()); 
        }
    }

    private static void backup(){
        try{
            System.out.print("Source: "); String src = scanner.nextLine().trim();
            System.out.print("Backup dir: "); String dest = scanner.nextLine().trim();
            BackupService bs = new BackupService(Paths.get(dest));
            var backup = bs.backupDirectory(Paths.get(src));
            System.out.println("Backup: " + backup);
            System.out.println("Size: " + bs.computeBackupSize(backup) + " bytes");
        } catch(Exception e){ 
            System.out.println("Error: " + e.getMessage()); 
        }
    }
}
