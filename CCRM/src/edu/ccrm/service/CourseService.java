package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.List;

public interface CourseService {
    void addCourse(Course c);
    Course findByCode(String code);
    List<Course> listCourses();
    void updateCourse(String code, String newTitle, int credits);
    void deactivateCourse(String code);

    // Stream features
    List<Course> findByInstructor(String instructorName);
    List<Course> findByDepartment(String department);
    List<Course> findBySemester(Semester semester);
}