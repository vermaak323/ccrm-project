package edu.ccrm.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Student extends Person {
    private String regNo;
    private boolean active;
    private LocalDate admissionDate;
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student(String id, String regNo, String name, String email) {
        super(id, name, email);
        this.regNo = regNo;
        this.active = true;
        this.admissionDate = LocalDate.now();
    }

    public String getRegNo() { return regNo; }
    public boolean isActive() { return active; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    public void addEnrollment(Enrollment e) { enrollments.add(e); }
    public void deactivate() { this.active = false; }

    @Override
    public String getRole() { return "Student"; }

    @Override
    public String toString() {
        return "Student[id=" + id + ", regNo=" + regNo + ", name=" + name +
                ", email=" + email + ", active=" + active + ", admissionDate=" + admissionDate + "]";
    }
}