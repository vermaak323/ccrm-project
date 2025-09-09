package edu.ccrm.service.impl;

import edu.ccrm.domain.*;
import edu.ccrm.service.TranscriptService;

public class TranscriptServiceImpl implements TranscriptService {
    @Override
    public double computeGPA(Student student) {
        double totalPoints = 0;
        int totalCredits = 0;
        for (Enrollment e : student.getEnrollments()) {
            if (e.getGrade() != null) {
                totalPoints += e.getGrade().getPoints() * e.getCourse().getCredits();
                totalCredits += e.getCourse().getCredits();
            }
        }
        return (totalCredits == 0) ? 0.0 : totalPoints / totalCredits;
    }

    @Override
    public void printTranscript(Student student) {
        System.out.println("\n=== Transcript for " + student.getName() + " ===");
        for (Enrollment e : student.getEnrollments()) {
            String grade = (e.getGrade() == null) ? "Not Graded" : e.getGrade().name();
            System.out.println(e.getCourse().getCode() + " - " + e.getCourse().getTitle() +
                    " | Credits: " + e.getCourse().getCredits() + " | Grade: " + grade);
        }
        System.out.printf("GPA: %.2f%n", computeGPA(student));
    }
}