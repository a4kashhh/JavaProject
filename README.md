# Campus Course & Records Manager (CCRM)

This is a comprehensive Java SE console application designed for managing student enrollment and course information in an academic setting. The project demonstrates various object-oriented programming concepts and design patterns commonly used in enterprise applications.

## Features
- **Student Management**: Add, view, and manage student records
- **Course Management**: Create and maintain course information
- **Enrollment System**: Enroll students in courses with validation
- **Data Import/Export**: CSV-based data import and export functionality
- **Backup System**: Automated backup of system data
- **Interactive CLI**: User-friendly command-line interface

## Quick Start Guide

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Terminal or command prompt access

### Running the Application

1. **Navigate to the project directory:**
   ```bash
   cd ~/Downloads/CCRM_complete
   ```

2. **Make the compilation script executable and compile:**
   ```bash
   chmod +x compile.sh
   ./compile.sh
   ```

3. **Run the application:**
   ```bash
   java -cp out edu.ccrm.cli.Main
   ```

## Project Structure

```
├── src/edu/ccrm/           # Main source code directory
│   ├── cli/                # Command-line interface components
│   ├── config/             # Configuration and data storage
│   ├── domain/             # Core business entities (Student, Course, etc.)
│   ├── io/                 # Import/export and backup services
│   ├── service/            # Business logic services
│   └── util/               # Utility classes and exceptions
├── sample-data/            # Sample CSV files for testing
├── .vscode/                # VS Code configuration (if using VS Code)
└── compile.sh              # Build script for easy compilation
```

## Sample Data
The `sample-data/` directory contains example CSV files:
- `students.csv` - Sample student records
- `courses.csv` - Sample course information

These files can be used to test the import functionality of the application.

## Development Notes
This project uses Java SE (Standard Edition) as it provides all the necessary APIs for desktop application development, including file I/O, collections, and networking capabilities. The application demonstrates several important programming concepts:

- Object-oriented design principles
- Design patterns (Singleton, Builder)
- Exception handling
- File I/O operations
- Stream processing
- Collections framework usage

