package edu.ccrm.config;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;

public class CourseBuilder {
    private String code, title, dept;
    private int credits;
    private Semester sem;

    public CourseBuilder code(String code) { this.code = code; return this; }
    public CourseBuilder title(String title) { this.title = title; return this; }
    public CourseBuilder dept(String dept) { this.dept = dept; return this; }
    public CourseBuilder credits(int credits) { this.credits = credits; return this; }
    public CourseBuilder semester(Semester sem) { this.sem = sem; return this; }

    public Course build() { return new Course(code, title, credits, dept, sem); }
}