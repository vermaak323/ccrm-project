package edu.ccrm.util;

import edu.ccrm.domain.Student;
import java.util.Comparator;

public class Comparators {
    public static Comparator<Student> byName() {
        return Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER);
    }
}