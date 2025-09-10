import java.util.*;
import java.time.LocalDate;

// ===== DOMAIN CLASSES =====
abstract class Person {
    protected String id, name, email;
    public Person(String id, String name, String email) {
        this.id = id; this.name = name; this.email = email;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public void setName(String n){ name=n; }
    public void setEmail(String e){ email=e; }
    public abstract String getRole();
    public String toString(){ return getRole() + "[" + id + ","+name+","+email+"]"; }
}

class Student extends Person {
    private String regNo;
    private LocalDate admissionDate = LocalDate.now();
    private boolean active = true;
    private List<Enrollment> enrollments = new ArrayList<>();
    public Student(String id,String reg,String name,String email){ super(id,name,email); this.regNo=reg; }
    public String getRegNo(){return regNo;}
    public List<Enrollment> getEnrollments(){return enrollments;}
    public void addEnrollment(Enrollment e){ enrollments.add(e); }
    public void deactivate(){ active=false;}
    public boolean isActive(){return active;}
    public String getRole(){ return "Student"; }
}

class Course {
    private String code,title,dept;
    private int credits;
    private Semester sem;
    private boolean active=true;
    public Course(String code,String title,int credits,String dept,Semester sem){
        assert credits>0 && credits<=6:"Credits must be 1-6";
        this.code=code; this.title=title; this.credits=credits; this.dept=dept; this.sem=sem;
    }
    public String getCode(){return code;}
    public String getTitle(){return title;}
    public int getCredits(){return credits;}
    public Semester getSemester(){return sem;}
    public String getDepartment(){return dept;}
    public boolean isActive(){return active;}
    public void setActive(boolean b){active=b;}
    public String toString(){ return code+" - "+title+" ("+credits+"cr,"+dept+","+sem+")"; }
}

class Enrollment {
    private final Student s; private final Course c;
    private Grade grade;
    public Enrollment(Student s,Course c){ this.s=s; this.c=c; }
    public Course getCourse(){ return c;}
    public Grade getGrade(){ return grade;}
    public void assignGrade(Grade g){ grade=g;}
}

enum Grade {
    A(4.0), B(3.0), C(2.0), D(1.0), F(0.0);
    private final double pts;
    Grade(double p){ pts=p;} public double getPoints(){return pts;}
}
enum Semester { SPRING,SUMMER,FALL,WINTER; }

// ===== EXCEPTIONS =====
class DuplicateEnrollmentException extends Exception{ public DuplicateEnrollmentException(String m){ super(m);} }
class MaxCreditLimitExceededException extends Exception{ public MaxCreditLimitExceededException(String m){ super(m);} }

// ===== MAIN APP =====
public class Main {
    static List<Student> students = new ArrayList<>();
    static List<Course> courses = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        while(true){
            System.out.println("\n==== Campus Course & Records Manager (Single File) ====");
            System.out.println("1.Add Student  2.List Students  3.Add Course  4.List Courses");
            System.out.println("5.Enroll  6.Record Grade  7.Transcript  8.Exit");
            System.out.print("Choice: ");
            int ch = Integer.parseInt(sc.nextLine());
            try{
                switch(ch){
                    case 1-> addStudent();
                    case 2-> listStudents();
                    case 3-> addCourse();
                    case 4-> listCourses();
                    case 5-> enroll();
                    case 6-> recordGrade();
                    case 7-> transcript();
                    case 8-> {System.out.println("Goodbye!"); return;}
                }
            } catch(Exception e){ System.out.println("❌ "+e.getMessage());}
        }
    }

    static void addStudent(){
        System.out.print("ID: "); String id=sc.nextLine();
        System.out.print("RegNo: "); String reg=sc.nextLine();
        System.out.print("Name: "); String n=sc.nextLine();
        System.out.print("Email: "); String e=sc.nextLine();
        students.add(new Student(id,reg,n,e));
        System.out.println("✅ Student added");
    }
    static void listStudents(){ students.forEach(System.out::println); }

    static void addCourse(){
        System.out.print("Code: "); String code=sc.nextLine();
        System.out.print("Title: "); String t=sc.nextLine();
        System.out.print("Credits: "); int cr=Integer.parseInt(sc.nextLine());
        System.out.print("Dept: "); String d=sc.nextLine();
        System.out.print("Semester (SPRING/SUMMER/FALL/WINTER): ");
        Semester sem=Semester.valueOf(sc.nextLine().toUpperCase());
        courses.add(new Course(code,t,cr,d,sem));
        System.out.println("✅ Course added");
    }
    static void listCourses(){ courses.forEach(System.out::println); }

    static void enroll() throws DuplicateEnrollmentException,MaxCreditLimitExceededException {
        Student s=findStudent();
        Course c=findCourse();
        if(s==null||c==null){System.out.println("❌ Not found"); return;}
        for(Enrollment e: s.getEnrollments()){
            if(e.getCourse().getCode().equals(c.getCode()))
                throw new DuplicateEnrollmentException("Already enrolled!");
        }
        int total=s.getEnrollments().stream().mapToInt(e->e.getCourse().getCredits()).sum();
        if(total+c.getCredits()>18) throw new MaxCreditLimitExceededException("Max 18 credits exceeded");
        s.addEnrollment(new Enrollment(s,c));
        System.out.println("✅ Enrollment successful");
    }

    static void recordGrade(){
        Student s=findStudent(); Course c=findCourse();
        if(s==null||c==null)return;
        Enrollment e=s.getEnrollments().stream().filter(x->x.getCourse().getCode().equals(c.getCode())).findFirst().orElse(null);
        if(e==null){ System.out.println("Not enrolled!"); return;}
        System.out.print("Enter Grade (A/B/C/D/F): ");
        Grade g=Grade.valueOf(sc.nextLine().toUpperCase());
        e.assignGrade(g);
        System.out.println("✅ Grade recorded");
    }

    static void transcript(){
        Student s=findStudent();
        if(s==null)return;
        double pts=0; int cr=0;
        for(Enrollment e:s.getEnrollments()){
            System.out.println(e.getCourse().getCode()+" "+e.getCourse().getTitle()+
                " | Credits: "+e.getCourse().getCredits()+" | Grade: "+(e.getGrade()==null?"Not Graded":e.getGrade()));
            if(e.getGrade()!=null){
                pts+=e.getGrade().getPoints()*e.getCourse().getCredits();
                cr+=e.getCourse().getCredits();
            }
        }
        double gpa= cr==0?0:pts/cr;
        System.out.println("📊 GPA = "+String.format("%.2f",gpa));
    }

    static Student findStudent(){
        System.out.print("Student ID: "); String id=sc.nextLine();
        return students.stream().filter(s->s.getId().equals(id)).findFirst().orElse(null);
    }
    static Course findCourse(){
        System.out.print("Course Code: "); String code=sc.nextLine();
        return courses.stream().filter(c->c.getCode().equals(code)).findFirst().orElse(null);
    }
}