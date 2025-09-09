package edu.ccrm.cli;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.io.BackupService;
import edu.ccrm.service.*;
import edu.ccrm.service.impl.*;
import edu.ccrm.exception.*;

import java.util.Scanner;
import java.nio.file.*;

public class CampusApp {
    private static final Scanner sc = new Scanner(System.in);

    private static final StudentService studentService = new StudentServiceImpl();
    private static final CourseService courseService = new CourseServiceImpl();
    private static final EnrollmentService enrollmentService = new EnrollmentServiceImpl();
    private static final TranscriptService transcriptService = new TranscriptServiceImpl();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Campus Course & Records Manager =====");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Add Course");
            System.out.println("4. List Courses");
            System.out.println("5. Enroll Student in Course");
            System.out.println("6. Unenroll Student from Course");
            System.out.println("7. Record Grade");
            System.out.println("8. Print Transcript");
            System.out.println("9. Export Data");
            System.out.println("10. Import Data");
            System.out.println("11. Backup Data");
            System.out.println("12. Show Backup Size");
            System.out.println("13. GPA Report");
            System.out.println("14. Exit");

            System.out.print("Enter choice: ");
            int choice;
            try { choice = Integer.parseInt(sc.nextLine()); }
            catch (Exception e) { System.out.println("❌ Invalid input."); continue; }

            switch (choice) {
                case 1 -> addStudentUI();
                case 2 -> listStudentsUI();
                case 3 -> addCourseUI();
                case 4 -> listCoursesUI();
                case 5 -> enrollStudentUI();
                case 6 -> unenrollStudentUI();
                case 7 -> recordGradeUI();
                case 8 -> printTranscriptUI();
                case 9 -> exportDataUI();
                case 10 -> importDataUI();
                case 11 -> backupUI();
                case 12 -> backupSizeUI();
                case 13 -> gpaReportUI();
                case 14 -> { System.out.println("👋 Goodbye!"); return; }
                default -> System.out.println("❌ Invalid choice!");
            }
        }
    }

    // ---------------- MENU METHODS ----------------

    private static void addStudentUI() {
        System.out.print("Enter ID: ");
        String id = sc.nextLine();
        System.out.print("Enter RegNo: ");
        String regNo = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        Student s = new Student(id, regNo, name, email);
        studentService.addStudent(s);
        System.out.println("✅ Student added!");
    }

    private static void listStudentsUI() {
        System.out.println("--- Students ---");
        studentService.listStudents().forEach(System.out::println);
    }

    private static void addCourseUI() {
        System.out.print("Enter Code: ");
        String code = sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(sc.nextLine());
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Semester (SPRING/SUMMER/FALL/WINTER): ");
        Semester sem;
        try {
            sem = Semester.valueOf(sc.nextLine().trim().toUpperCase());
        } catch (Exception e) {
            System.out.println("❌ Invalid semester entered!");
            return;
        }

        Course c = new Course(code, title, credits, dept, sem);
        courseService.addCourse(c);
        System.out.println("✅ Course added!");
    }

    private static void listCoursesUI() {
        System.out.println("--- Courses ---");
        courseService.listCourses().forEach(System.out::println);
    }

    private static void enrollStudentUI() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findById(sid);
        if (s == null) { System.out.println("❌ Student not found."); return; }

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();
        Course c = courseService.findByCode(code);
        if (c == null) { System.out.println("❌ Course not found."); return; }

        try {
            enrollmentService.enroll(s, c);
            System.out.println("✅ Enrolled successfully.");
        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    private static void unenrollStudentUI() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findById(sid);
        if (s == null) { System.out.println("❌ Student not found."); return; }

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();
        Course c = courseService.findByCode(code);
        if (c == null) { System.out.println("❌ Course not found."); return; }

        enrollmentService.unenroll(s, c);
        System.out.println("✅ Student unenrolled from course if found.");
    }

    private static void recordGradeUI() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findById(sid);
        if (s == null) { System.out.println("❌ Student not found."); return; }

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();

        Enrollment target = s.getEnrollments().stream()
                .filter(e -> e.getCourse().getCode().equals(code))
                .findFirst().orElse(null);

        if (target == null) { System.out.println("❌ Student not enrolled in course."); return; }

        System.out.print("Enter Grade (A/B/C/D/F): ");
        try {
            Grade g = Grade.valueOf(sc.nextLine().trim().toUpperCase());
            target.assignGrade(g);
            System.out.println("✅ Grade recorded!");
        } catch (Exception e) {
            System.out.println("❌ Invalid grade entered!");
        }
    }

    private static void printTranscriptUI() {
        System.out.print("Enter Student ID: ");
        String sid = sc.nextLine();
        Student s = studentService.findById(sid);
        if (s == null) { System.out.println("❌ Student not found."); return; }
        transcriptService.printTranscript(s);
    }

    private static void exportDataUI() {
        try {
            ImportExportService.exportStudents("students.csv");
            ImportExportService.exportCourses("courses.csv");
            System.out.println("✅ Data exported.");
        } catch (Exception e) {
            System.out.println("❌ Export failed: " + e.getMessage());
        }
    }

    private static void importDataUI() {
        try {
            ImportExportService.importStudents("students.csv");
            System.out.println("✅ Data imported.");
        } catch (Exception e) {
            System.out.println("❌ Import failed: " + e.getMessage());
        }
    }

    // ---------------- BACKUP & REPORT ----------------

    private static void backupUI() {
        try {
            Path backupDir = BackupService.backupExportDir(Paths.get("."));
            System.out.println("✅ Backup created at: " + backupDir.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("❌ Backup failed: " + e.getMessage());
        }
    }

    private static void backupSizeUI() {
        try {
            Path lastBackup = Files.list(Paths.get("."))
                    .filter(Files::isDirectory)
                    .filter(d -> d.getFileName().toString().startsWith("backup-"))
                    .max((a, b) -> a.getFileName().toString().compareTo(b.getFileName().toString()))
                    .orElse(null);

            if (lastBackup == null) {
                System.out.println("❌ No backup found.");
                return;
            }

            long size = BackupService.getDirSize(lastBackup);
            System.out.println("📂 Backup directory: " + lastBackup);
            System.out.println("💾 Total size: " + size + " bytes");
        } catch (Exception e) {
            System.out.println("❌ Error calculating backup size: " + e.getMessage());
        }
    }

    private static void gpaReportUI() {
        System.out.println("=== GPA Report ===");
        AppConfig.getInstance().getStudents().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        s -> {
                            double gpa = transcriptService.computeGPA(s);
                            if (gpa >= 3.5) return "Excellent (>=3.5)";
                            else if (gpa >= 2.0) return "Average (2.0–3.49)";
                            else return "Weak (<2.0)";
                        }
                ))
                .forEach((category, list) -> {
                    System.out.println(category + ": " + list.size() + " students");
                    list.forEach(st -> System.out.println(" - " + st.getName() + " (GPA = " +
                            String.format("%.2f", transcriptService.computeGPA(st)) + ")"));
                });
    }
}