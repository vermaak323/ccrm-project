package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;

public interface StudentService {
    void addStudent(Student s);
    Student findById(String id);
    List<Student> listStudents();
    void updateStudent(String id, String newName, String newEmail);
    void deactivateStudent(String id);
}