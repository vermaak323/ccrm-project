package edu.ccrm.service;

import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import java.util.List;

public interface EnrollmentService {

    void enroll(Student student, Course course)
        throws DuplicateEnrollmentException, MaxCreditLimitExceededException;

    void unenroll(Student student, Course course);

    List<Enrollment> getEnrollments(Student student);
}