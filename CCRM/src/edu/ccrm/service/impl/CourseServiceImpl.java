package edu.ccrm.service.impl;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.CourseService;

import java.util.List;
import java.util.stream.Collectors;

public class CourseServiceImpl implements CourseService {
    private final List<Course> courses = AppConfig.getInstance().getCourses();

    @Override
    public void addCourse(Course c) {
        courses.add(c);
    }

    @Override
    public Course findByCode(String code) {
        return courses.stream().filter(c -> c.getCode().equals(code)).findFirst().orElse(null);
    }

    @Override
    public List<Course> listCourses() {
        return courses;
    }

    @Override
    public void updateCourse(String code, String newTitle, int credits) {
        Course c = findByCode(code);
        if (c != null) {
            c.setTitle(newTitle);
            c.setCredits(credits);
        }
    }

    @Override
    public void deactivateCourse(String code) {
        Course c = findByCode(code);
        if (c != null) c.setActive(false);  // Add "active" field into Course class
    }

    @Override
    public List<Course> findByInstructor(String instructorName) {
        return courses.stream()
                .filter(c -> c.getInstructor() != null && c.getInstructor().getName().equalsIgnoreCase(instructorName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findByDepartment(String department) {
        return courses.stream()
                .filter(c -> c.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> findBySemester(Semester semester) {
        return courses.stream()
                .filter(c -> c.getSemester() == semester)
                .collect(Collectors.toList());
    }
}