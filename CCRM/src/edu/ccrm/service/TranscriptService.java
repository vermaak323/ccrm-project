package edu.ccrm.service;

import edu.ccrm.domain.Student;

public interface TranscriptService {
    double computeGPA(Student student);
    void printTranscript(Student student);
}