package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;

import java.io.*;
import java.nio.file.*;

public class ImportExportService {
    public static void exportStudents(String file) throws IOException {
        Path path = Paths.get(file);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Student s : AppConfig.getInstance().getStudents()) {
                writer.write(s.getId() + "," + s.getRegNo() + "," + s.getName() + "," + s.getEmail());
                writer.newLine();
            }
        }
    }

    public static void exportCourses(String file) throws IOException {
        Path path = Paths.get(file);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Course c : AppConfig.getInstance().getCourses()) {
                writer.write(c.getCode() + "," + c.getTitle() + "," + c.getCredits() +
                        "," + c.getDepartment() + "," + c.getSemester());
                writer.newLine();
            }
        }
    }

    public static void importStudents(String file) throws IOException {
        Path path = Paths.get(file);
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Student s = new Student(parts[0], parts[1], parts[2], parts[3]);
                    AppConfig.getInstance().getStudents().add(s);
                }
            }
        }
    }
}