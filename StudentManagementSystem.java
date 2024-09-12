import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class StudentManagementSystem {
    private static class Student {
        private String name;
        private String rollNumber;
        private String grade;
        private int age;
        private String email;

        // Constructor
        public Student(String name, String rollNumber, String grade, int age, String email) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
            this.age = age;
            this.email = email;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getRollNumber() { return rollNumber; }
        public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        @Override
        public String toString() {
            return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade + ", Age: " + age + ", Email: " + email;
        }
    }

    private List<Student> students;

    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

    // Add a student
    public void addStudent(Student student) {
        students.add(student);
    }

    // Remove a student
    public void removeStudent(String rollNumber) {
        Iterator<Student> iterator = students.iterator();
        while (iterator.hasNext()) {
            Student student = iterator.next();
            if (student.getRollNumber().equals(rollNumber)) {
                iterator.remove();
                return;
            }
        }
    }

    // Search for a student
    public Student searchStudent(String rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber().equals(rollNumber)) {
                return student;
            }
        }
        return null;
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    // Save students to a file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Student student : students) {
                writer.println(student.getName() + "," + student.getRollNumber() + "," + student.getGrade() + "," + student.getAge() + "," + student.getEmail());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    // Load students from a file
    public void loadFromFile(String filename) {
        students.clear();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                if (data.length == 5) {
                    addStudent(new Student(data[0], data[1], data[2], Integer.parseInt(data[3]), data[4]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file.");
        }
    }

    // Main method to run the program
    public static void main(String[] args) {
        StudentManagementSystem sms = new StudentManagementSystem();
        Scanner scanner = new Scanner(System.in);
        String filename = "students.txt";

        // Load students from file if it exists
        sms.loadFromFile(filename);

        while (true) {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save and Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter roll number: ");
                    String rollNumber = scanner.nextLine();
                    System.out.print("Enter grade: ");
                    String grade = scanner.nextLine();
                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    if (name.isEmpty() || rollNumber.isEmpty() || grade.isEmpty() || age <= 0 || email.isEmpty()) {
                        System.out.println("Invalid input. Please try again.");
                        break;
                    }

                    sms.addStudent(new Student(name, rollNumber, grade, age, email));
                    break;
                case 2:
                    System.out.print("Enter roll number to remove: ");
                    rollNumber = scanner.nextLine();
                    sms.removeStudent(rollNumber);
                    break;
                case 3:
                    System.out.print("Enter roll number to search: ");
                    rollNumber = scanner.nextLine();
                    Student student = sms.searchStudent(rollNumber);
                    if (student != null) {
                        System.out.println(student);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 4:
                    sms.displayAllStudents();
                    break;
                case 5:
                    sms.saveToFile(filename);
                    System.out.println("Data saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
