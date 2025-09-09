package edu.ccrm.config;

import edu.ccrm.domain.*;
import java.util.*;

public class AppConfig {
    private static AppConfig instance;

    private final List<Student> students = new ArrayList<>();
    private final List<Course> courses = new ArrayList<>();

    private AppConfig() {}

    public static synchronized AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

    public List<Student> getStudents() { return students; }
    public List<Course> getCourses() { return courses; }
}