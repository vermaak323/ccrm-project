package edu.ccrm.domain;

public class Instructor extends Person {
    private String department;

    public Instructor(String id, String name, String email, String department) {
        super(id, name, email);
        this.department = department;
    }

    public String getDepartment() { return department; }
    public void setDepartment(String dept) { this.department = dept; }

    @Override
    public String getRole() { return "Instructor"; }
}