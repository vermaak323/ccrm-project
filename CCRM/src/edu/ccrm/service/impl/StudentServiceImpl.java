package edu.ccrm.service.impl;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Student;
import edu.ccrm.service.StudentService;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final List<Student> students = AppConfig.getInstance().getStudents();

    @Override
    public void addStudent(Student s) {
        students.add(s);
    }

    @Override
    public Student findById(String id) {
        return students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Student> listStudents() {
        return students;
    }

    @Override
public void updateStudent(String id, String newName, String newEmail) {
    Student s = findById(id);
    if (s != null) {
        s.setName(newName);   // inherited from Person
        s.setEmail(newEmail); // inherited from Person
    }
}

    @Override
    public void deactivateStudent(String id) {
        Student s = findById(id);
        if (s != null) s.deactivate();
    }
}