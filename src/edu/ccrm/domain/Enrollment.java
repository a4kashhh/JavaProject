package edu.ccrm.domain;

import java.time.LocalDateTime;

public class Enrollment {
    private final String courseCode;
    private final int courseCredits;
    private final LocalDateTime enrolledAt;
    private Grade grade;

    public Enrollment(String courseCode, int credits){
        this.courseCode = courseCode;
        this.courseCredits = credits;
        this.enrolledAt = LocalDateTime.now();
    }
    public String getCourseCode(){ return courseCode; }
    public int getCourseCredits(){ return courseCredits; }
    public LocalDateTime getEnrolledAt(){ return enrolledAt; }
    public Grade getGrade(){ return grade; }
    public void setGrade(Grade g){ this.grade = g; }
    @Override
    public String toString(){ return String.format("Enrollment[%s,%dcr,grade=%s]", courseCode, courseCredits, grade); }
}
