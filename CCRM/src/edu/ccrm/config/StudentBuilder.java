package edu.ccrm.config;

import edu.ccrm.domain.Student;

public class StudentBuilder {
    private String id, regNo, name, email;

    public StudentBuilder id(String id) { this.id = id; return this; }
    public StudentBuilder regNo(String regNo) { this.regNo = regNo; return this; }
    public StudentBuilder name(String name) { this.name = name; return this; }
    public StudentBuilder email(String email) { this.email = email; return this; }

    public Student build() { return new Student(id, regNo, name, email); }
}