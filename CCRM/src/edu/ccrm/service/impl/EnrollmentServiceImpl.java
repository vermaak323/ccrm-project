package edu.ccrm.service.impl;

import edu.ccrm.domain.*;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentServiceImpl implements EnrollmentService {

    private static final int MAX_CREDITS_PER_SEMESTER = 18;

    @Override
    public void enroll(Student student, Course course)
            throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        // Rule 1: No duplicate enrollments
        for (Enrollment e : student.getEnrollments()) {
            if (e.getCourse().getCode().equals(course.getCode())) {
                throw new DuplicateEnrollmentException(
                        "Student already enrolled in course: " + course.getCode());
            }
        }

        // Rule 2: Credit limit check
        int totalCredits = student.getEnrollments().stream()
                .mapToInt(e -> e.getCourse().getCredits())
                .sum();

        if (totalCredits + course.getCredits() > MAX_CREDITS_PER_SEMESTER) {
            throw new MaxCreditLimitExceededException(
                    "Cannot exceed " + MAX_CREDITS_PER_SEMESTER + " credits! " +
                    "Current total: " + totalCredits + ", Adding: " + course.getCredits());
        }

        // Add enrollment
        Enrollment enrollment = new Enrollment(student, course);
        student.addEnrollment(enrollment);
    }

    @Override
    public void unenroll(Student student, Course course) {
        student.getEnrollments().removeIf(e -> e.getCourse().getCode().equals(course.getCode()));
    }

    @Override
    public List<Enrollment> getEnrollments(Student student) {
        return new ArrayList<>(student.getEnrollments());
    }
}