# CCRM Usage Guide

## Compilation and Execution

### Building the Project
To compile all Java source files, run the compilation script:
```bash
./compile.sh
```

### Running the Application
After successful compilation, start the application with:
```bash
java -cp out edu.ccrm.cli.Main
```

## Using Sample Data
The application comes with sample data files that can be used for testing:

### Importing Sample Data
- **Student Data**: Use `sample-data/students.csv` when testing the student import feature
- **Course Data**: Use `sample-data/courses.csv` when testing the course import feature

### Data Format
- Both CSV files include headers in the first row
- Make sure to provide the correct file paths when prompted by the application
- The application will validate the data format and provide feedback on any issues

## Application Features
1. **Student Management** - View existing students and add new ones
2. **Course Management** - Browse available courses and create new courses
3. **Student Enrollment** - Enroll students in specific courses
4. **Data Import/Export** - Import data from CSV files or export current data
5. **System Backup** - Create backups of your data directories

## Tips for Best Experience
- Always ensure your CSV files follow the expected format
- Use absolute file paths when importing/exporting data
- Create a dedicated directory for exports before using the export feature
- Regular backups are recommended to prevent data loss