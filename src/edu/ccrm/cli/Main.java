package edu.ccrm.cli;

import edu.ccrm.config.DataStore;
import edu.ccrm.service.*;
import edu.ccrm.io.*;
import edu.ccrm.domain.*;
import java.nio.file.Paths;
import java.util.*;

/**
 * Main CLI for CCRM application.
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DataStore ds = DataStore.getInstance();
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final ImportExportService ioService = new ImportExportService();

    public static void main(String[] args) {
        // Initialize with sample data
        ds.seedSample();
        
        System.out.println("=== Campus Course & Records Manager ===");
        
        boolean continueRunning = true;
        while(continueRunning){
            showMainMenu();
            String userChoice = scanner.nextLine().trim();
            
            switch(userChoice){
                case "1" -> handleStudentManagement();
                case "2" -> handleCourseManagement();
                case "3" -> processEnrollment();
                case "4" -> handleImportExport();
                case "5" -> performBackup();
                case "6" -> { 
                    displayExitMessage(); 
                    continueRunning = false; 
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Application terminated.");
    }

    private static void showMainMenu(){
        System.out.println("\n=== Main Menu ===");
        System.out.println("1) Manage Students");
        System.out.println("2) Manage Courses"); 
        System.out.println("3) Enroll Student");
        System.out.println("4) Import/Export");
        System.out.println("5) Backup");
        System.out.println("6) Exit");
        System.out.print("\nSelect option: ");
    }

    private static void handleStudentManagement(){
        System.out.println("\n=== Student Management ===");
        System.out.println("Current students in the system:");
        
        List<Student> allStudents = studentService.listAll();
        if(allStudents.isEmpty()) {
            System.out.println("No students found in the system.");
        } else {
            allStudents.forEach(student -> System.out.println("  • " + student));
        }
        
        System.out.print("\nWould you like to add a new student? (y/n): ");
        String response = scanner.nextLine().trim();
        
        if(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
            System.out.println("\nEnter student details:");
            System.out.print("Student ID: "); 
            String studentId = scanner.nextLine().trim();
            System.out.print("Full Name: "); 
            String studentName = scanner.nextLine().trim();
            System.out.print("Email Address: "); 
            String emailAddress = scanner.nextLine().trim();
            
            try {
                studentService.createStudent(studentId, studentName, emailAddress);
                System.out.println("✓ Student successfully added to the system!");
            } catch (Exception e) {
                System.out.println("✗ Error adding student: " + e.getMessage());
            }
        }
    }

    private static void handleCourseManagement(){
        System.out.println("\n=== Course Management ===");
        System.out.println("Available courses:");
        
        List<Course> allCourses = courseService.listAll();
        if(allCourses.isEmpty()) {
            System.out.println("No courses found in the system.");
        } else {
            allCourses.forEach(course -> System.out.println("  • " + course));
        }
        
        System.out.print("\nWould you like to add a new course? (y/n): ");
        String response = scanner.nextLine().trim();
        
        if(response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")){
            System.out.println("\nEnter course details:");
            System.out.print("Course Code: "); 
            String courseCode = scanner.nextLine().trim();
            System.out.print("Course Title: "); 
            String courseTitle = scanner.nextLine().trim();
            System.out.print("Credit Hours: "); 
            int creditHours = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Instructor Name: "); 
            String instructorName = scanner.nextLine().trim();
            System.out.print("Department: "); 
            String department = scanner.nextLine().trim();
            
            try {
                Course newCourse = new Course.Builder(courseCode)
                    .title(courseTitle)
                    .credits(creditHours)
                    .instructor(instructorName)
                    .department(department)
                    .build();
                courseService.createCourse(newCourse);
                System.out.println("✓ Course successfully added to the system!");
            } catch (Exception e) {
                System.out.println("✗ Error adding course: " + e.getMessage());
            }
        }
    }

    private static void processEnrollment(){
        System.out.println("\n=== Student Enrollment ===");
        System.out.print("Enter student ID: "); 
        String studentId = scanner.nextLine().trim();
        System.out.print("Enter course code: "); 
        String courseCode = scanner.nextLine().trim();
        
        Optional<Course> courseFound = courseService.find(courseCode);
        if(courseFound.isEmpty()){ 
            System.out.println("✗ Sorry, no course found with code: " + courseCode); 
            return; 
        }
        
        try {
            studentService.enroll(studentId, courseFound.get());
            System.out.println("✓ Student successfully enrolled in the course!");
        } catch(Exception e){
            System.out.println("✗ Enrollment failed: " + e.getMessage());
        }
    }

    private static void handleImportExport(){
        System.out.println("\n=== Data Import/Export ===");
        System.out.println("1) Import Students from CSV");
        System.out.println("2) Import Courses from CSV");
        System.out.println("3) Export All Data to CSV");
        System.out.print("Please select an option (1-3): ");
        
        String option = scanner.nextLine().trim();
        try{
            if(option.equals("1")){
                System.out.print("Enter path to students CSV file: "); 
                String filePath = scanner.nextLine().trim();
                ioService.importStudents(Paths.get(filePath));
                System.out.println("✓ Students imported successfully!");
            } else if(option.equals("2")){
                System.out.print("Enter path to courses CSV file: "); 
                String filePath = scanner.nextLine().trim();
                ioService.importCourses(Paths.get(filePath));
                System.out.println("✓ Courses imported successfully!");
            } else if(option.equals("3")){
                System.out.print("Enter output directory path: "); 
                String outputDir = scanner.nextLine().trim();
                ioService.exportAll(Paths.get(outputDir));
                System.out.println("✓ Data exported successfully!");
            } else {
                System.out.println("Invalid option selected.");
            }
        } catch(Exception e){ 
            System.out.println("✗ Operation failed: " + e.getMessage()); 
        }
    }

    private static void performBackup(){
        System.out.println("\n=== System Backup ===");
        try{
            System.out.print("Enter source directory to backup (e.g., exports): "); 
            String sourceDirectory = scanner.nextLine().trim();
            System.out.print("Enter backup destination directory (e.g., backups): "); 
            String backupBaseDir = scanner.nextLine().trim();
            
            BackupService backupService = new BackupService(Paths.get(backupBaseDir));
            var backupLocation = backupService.backupDirectory(Paths.get(sourceDirectory));
            
            System.out.println("✓ Backup completed successfully!");
            System.out.println("Backup saved to: " + backupLocation);
            
            long backupSize = backupService.computeBackupSize(backupLocation);
            System.out.println("Total backup size: " + formatFileSize(backupSize));
            
        } catch(Exception e){ 
            System.out.println("✗ Backup operation failed: " + e.getMessage()); 
        }
    }

    private static String formatFileSize(long bytes) {
        if (bytes < 1024) return bytes + " bytes";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024.0));
    }

    private static void displayExitMessage(){
        System.out.println("\n=== System Information ===");
        System.out.println("This application was built using Java SE (Standard Edition).");
        System.out.println("Java SE provides the core functionality needed for desktop applications like this one.");
        System.out.println("For more technical details, please refer to the README documentation.");
    }
}
