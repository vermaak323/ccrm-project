package edu.ccrm.domain;

public class Course {
    private String code;
    private String title;
    private int credits;
    private Instructor instructor;
    private Semester semester;
    private String department;
    private boolean active = true;

    public Course(String code, String title, int credits, String department, Semester semester) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.department = department;
        this.semester = semester;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public Instructor getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public boolean isActive() { return active; }

    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }
    public void setSemester(Semester semester) { this.semester = semester; }
    public void setDepartment(String department) { this.department = department; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return code + " - " + title + " (" + credits +
                " credits, Dept: " + department + ", Semester: " + semester + ", Active: " + active + ")";
    }
}