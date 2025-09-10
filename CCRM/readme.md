https://github.com/user-attachments/assets/375bfea4-98c4-4e78-a63d-8ac353b15924



**Campus Course & Records Manager (CCRM)**

**Overview:**

Campus Course & Records Manager (CCRM) is a **console-based Java SE application**.  
It allows an institute to manage:
- Students (add/list/update/deactivate, enrollments, transcript)
- Courses (add/list/update/deactivate, filtering by semester/department/instructor)
- Enrollments (business rules: no duplicate enrollments, max credits limit)
- Grades & GPA calculation (transcript printing)
- File utilities (CSV import/export, backups with NIO.2)
- Reports (GPA distribution report with Streams)
- Utility functions (validators, comparators, recursion)

This project demonstrates **core Java SE, OOP, advanced features, exceptions, design patterns** and includes all syllabus topics.

**I have also uploaded a single runnable main.java file**  
**How to Run (other than single runnable main.java file)**
In mac:
1. Open your zsh terminal
2. command: cd
3. then drag and drop your file so that the file path is automatically inserted
4. command: javac -d out $(find src -name "*.java")
5. command to check if it is compiled: ls out
6. it will show edu
7. command: java -up out edu.ccrm.cli.CampusApp


**Evolution of Java**
1995 – Java 1.0 released by Sun Microsystems
2004 – Java 5 (Generics, Enums, Annotations)
2011 – Java 7 (NIO.2 File API)
2014 – Java 8 (Lambdas & Streams)
2017 – Java 9 (Modules)
2021 – Java 17 (Long-Term Support)
2023 – Java 21 (Latest LTS)


**📊 Java ME vs SE vs EE**

**🔹 Java ME (Micro Edition)**
Target: Small devices, embedded systems, old mobile phones
APIs: Lightweight, limited set of Java libraries
Example usage: Old mobile apps (pre‑Android, like Nokia/Symbian apps)

**🔹 Java SE (Standard Edition)**
Target: Desktops, command-line apps, small utilities
APIs: Full core Java APIs (Collections, Streams, I/O, Networking etc.)
Example usage: General purpose tools, OOP learning, desktop apps

**🔹 Java EE (Enterprise Edition)**
Target: Large scale enterprise & web servers
APIs: Includes all of SE + enterprise APIs (Servlets, JSP, EJB, JPA, Web Services)
Example usage: Banking systems, e‑commerce, enterprise platforms


**project structure**
src/edu/ccrm/
 ├─ cli/          → Main menu (CampusApp.java)
 ├─ domain/       → Person, Student, Instructor, Course, Enrollment, Grade, Semester
 ├─ service/      → Interfaces
 ├─ service/impl/ → Implementations
 ├─ config/       → AppConfig (Singleton), Builders
 ├─ io/           → ImportExportService, BackupService
 ├─ util/         → Validators, Comparators, RecursionUtils
 └─ exception/    → Custom Exceptions


test-data/                          → Sample CSV files (students.csv, courses.csv)
screenshots/                        → Java version, CLI demo, transcript, GPA report, backup folder
JAVA CCRM RECORDING.mp4             → Running video
README.md



**⚙️ Java Architecture: JDK, JRE, JVM**

**🔸 JVM (Java Virtual Machine)**
JVM is the engine that executes Java bytecode.
It makes Java platform independent (Write Once, Run Anywhere).
Handles important tasks like:
Memory management (Garbage Collection)
Bytecode verification and execution
Security checks

**🔸 JRE (Java Runtime Environment)**
JRE = JVM + libraries required to run Java applications.
Does not include the compiler.
It’s enough if you only want to run Java programs (not write/compile them).

**🔸 JDK (Java Development Kit)**
JDK = JRE + development tools.
Includes:
javac (compiler)
Debuggers, Javadoc, and other tools
Needed by developers to compile + run Java programs.


## 📑 Mapping Table (Syllabus → Code)

| Syllabus Topic                  | Where Implemented (File / Class / Method)                     |
|---------------------------------|----------------------------------------------------------------|
| **Encapsulation**                | All domain classes (private fields + getters/setters, e.g., `Student.java`, `Course.java`) |
| **Inheritance**                  | `Person.java` (abstract) → extended by `Student.java`, `Instructor.java` |
| **Abstraction**                  | `Person.java` (abstract class with abstract method `getRole()`) |
| **Polymorphism**                 | Overridden `getRole()`, `toString()` in `Student.java` & `Instructor.java` |
| **Interfaces**                   | `StudentService.java`, `CourseService.java`, etc.             |
| **Singleton Pattern**            | `AppConfig.java` (ensures single instance for storing students & courses) |
| **Builder Pattern**              | `StudentBuilder.java`, `CourseBuilder.java` (fluent object creation) |
| **Custom Exceptions**            | `DuplicateEnrollmentException.java`, `MaxCreditLimitExceededException.java` |
| **Exception Handling**           | `CampusApp.java` (try/catch in enrollment, import/export)      |
| **Checked & Unchecked Exceptions** | Service layer throws custom exceptions; CLI catches & prints  |
| **Date/Time API**                | `Student.java` (admissionDate as `LocalDate`), `BackupService.java` (timestamp folders) |
| **Enums**                        | `Grade.java` (stores points), `Semester.java` (SPRING, SUMMER, FALL, WINTER) |
| **Streams / Lambdas**            | `CourseServiceImpl.java` (filtering by instructor/department), `CampusApp.gpaReportUI()` |
| **Recursion**                    | `BackupService.getDirSize()` and `util/RecursionUtils.java`    |
| **File I/O (NIO.2)**             | `ImportExportService.java`, `BackupService.java` (Files, Paths APIs) |
| **Anonymous Inner Class**        | Example comparator in CLI for sorting students                 |
| **Assertions**                   | `Course.java` constructor (`assert credits > 0 && credits <= 6`) |
| **Arrays & Array Utilities**     | `util/Comparators.java`, sorting examples with `Arrays.sort()` |



## 🧪 Notes on Enabling Assertions

Assertions are used in the project to enforce invariants.  
For example, in `Course.java` constructor:

```java
assert credits > 0 && credits <= 6 : "Credits must be between 1 and 6";
