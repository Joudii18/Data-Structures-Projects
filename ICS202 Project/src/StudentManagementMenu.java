import java.io.IOException;
import java.util.Scanner;
import java.util.LinkedList;

public class StudentManagementMenu {

    public static void main(String[] args) throws IOException {
        // File path for the students-details.csv file
        String filePath = "/Users/joudaljabri/Desktop/ICS202/students-details.csv"; // Ensure the correct path is provided

        // Initialize the Student Records Management System
        StudentRecordsManagementSystem system = new StudentRecordsManagementSystem(filePath);

        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Menu loop
        while (true) {
            System.out.println("\nStudent Management Menu:");
            System.out.println("1. Search Student");
            System.out.println("2. Add New Student");
            System.out.println("3. Show Students in an Academic Level");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // Consume invalid input
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchStudent(system, scanner);
                    break;

                case 2:
                    addStudent(system, scanner);
                    break;

                case 3:
                    showStudentsByAcademicLevel(system, scanner);
                    break;

                case 4:
                    System.out.println("Exiting program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }

    private static void addStudent(StudentRecordsManagementSystem system, Scanner scanner) {
        System.out.println("\nAdd a New Student:");

        int id = getValidatedID(scanner);

        System.out.print("Enter First Name: ");
        String firstName = formatFirstName(getValidatedName(scanner));

        System.out.print("Enter Last Name: ");
        String lastName = formatLastName(getValidatedLastName(scanner));

        System.out.print("Enter Birth Date (DD/MM/YYYY): ");
        String birthDate = getValidatedDate(scanner);

        System.out.print("Enter Class Level (OR, FR, SO, JR, SR): ");
        String classLevel = getValidatedClassLevel(scanner);

        Student student = new Student(id, firstName, lastName, birthDate, classLevel);
        system.insert(student);

        System.out.println("Student added successfully!");
    }

    private static void searchStudent(StudentRecordsManagementSystem system, Scanner scanner) {
        while (true) {
            System.out.println("\nSearch for a Student:");
            System.out.println("1. Search by First Name");
            System.out.println("2. Search by Last Name");
            System.out.println("3. Search by Student ID");
            System.out.println("4. Return to Main Menu");
            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
                scanner.next(); // Consume invalid input
                continue;
            }

            int searchChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            LinkedList<Student> results = new LinkedList<>();

            switch (searchChoice) {
                case 1:
                    System.out.print("Enter First Name: ");
                    String firstName = formatFirstName(getValidatedName(scanner));
                    results = system.findByFirstName(firstName);
                    break;

                case 2:
                    System.out.print("Enter Last Name: ");
                    String lastName = formatLastName(getValidatedLastName(scanner));
                    results = system.findByLastName(lastName);
                    break;

                case 3:
                    System.out.print("Enter Student ID: ");
                    int id = getValidatedID(scanner);
                    Student student = system.findByStudentID(id);
                    if (student != null) results.add(student);
                    break;

                case 4:
                    return;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                    continue;
            }

            if (results.isEmpty()) {
                System.out.println("No students found.");
            } else {
                System.out.println("\nStudents Found:");
                int index = 1;
                for (Student student : results) {
                    System.out.println(index + ". " + student);
                    index++;
                }

                System.out.println("\nOptions:");
                System.out.println("1. Edit a Student");
                System.out.println("2. Delete a Student");
                System.out.println("3. Return to Search Menu");
                System.out.print("Enter your choice: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    scanner.next(); // Consume invalid input
                    continue;
                }

                int subChoice = scanner.nextInt();
                scanner.nextLine();

                switch (subChoice) {
                    case 1:
                        System.out.print("Enter the number of the Student to Edit: ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();
                            continue;
                        }
                        int editIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (editIndex > 0 && editIndex <= results.size()) {
                            Student studentToEdit = results.get(editIndex - 1);
                            editStudent(system, scanner, studentToEdit);
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;

                    case 2:
                        System.out.print("Enter the number of the Student to Delete: ");
                        if (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            scanner.next();
                            continue;
                        }
                        int deleteIndex = scanner.nextInt();
                        scanner.nextLine();
                        if (deleteIndex > 0 && deleteIndex <= results.size()) {
                            Student studentToDelete = results.get(deleteIndex - 1);
                            system.deleteStudent(studentToDelete.getStudentID());
                            System.out.println("Student deleted successfully.");
                        } else {
                            System.out.println("Invalid choice.");
                        }
                        break;

                    case 3:
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
    }

    private static void editStudent(StudentRecordsManagementSystem system, Scanner scanner, Student student) {
        System.out.println("\nEditing Student: " + student);

        System.out.print("Enter New First Name (Leave blank to keep current): ");
        String newFirstName = scanner.nextLine();
        if (!newFirstName.isEmpty()) {
            system.updateFirstName(student.getStudentID(), formatFirstName(newFirstName));
        }

        System.out.print("Enter New Last Name (Leave blank to keep current): ");
        String newLastName = scanner.nextLine();
        if (!newLastName.isEmpty() && newLastName.matches("^[A-Za-z\\-]+$")) {
            system.updateLastName(student.getStudentID(), formatLastName(newLastName));
        } else if (!newLastName.isEmpty()) {
            System.out.println("Invalid last name format. Keeping current last name.");
        }

        System.out.print("Enter New Birth Date (Leave blank to keep current): ");
        String newBirthDate = scanner.nextLine();
        if (!newBirthDate.isEmpty() && newBirthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
            system.updateBirthDate(student.getStudentID(), newBirthDate);
        } else if (!newBirthDate.isEmpty()) {
            System.out.println("Invalid date format. Keeping current birth date.");
        }

        System.out.print("Enter New Class Level (Leave blank to keep current): ");
        String newClassLevel = scanner.nextLine().toUpperCase();
        if (!newClassLevel.isEmpty() && newClassLevel.matches("OR|FR|SO|JR|SR")) {
            system.updateClassLevel(student.getStudentID(), newClassLevel);
        } else if (!newClassLevel.isEmpty()) {
            System.out.println("Invalid class level. Keeping current class level.");
        }

        System.out.println("Student updated successfully.");
    }

    private static void showStudentsByAcademicLevel(StudentRecordsManagementSystem system, Scanner scanner) {
        System.out.print("\nEnter Class Level (OR, FR, SO, JR, SR): ");
        String classLevel = getValidatedClassLevel(scanner);

        LinkedList<Student> students = system.findClassLevel(classLevel);

        if (students == null || students.isEmpty()) {
            System.out.println("No students found in class level: " + classLevel);
        } else {
            System.out.println("\nStudents in Class Level '" + classLevel + "':");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Utility functions for input validation
    private static int getValidatedID(Scanner scanner) {
        int id = -1;
        while (id < 0 || id > 99999) {
            System.out.print("Enter Student ID (up to 5 digits): ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                if (id < 0 || id > 99999) {
                    System.out.println("Invalid ID. Please enter a number between 0 and 99999.");
                }
            } else {
                System.out.println("Invalid input. Please enter a numeric ID.");
                scanner.next();
            }
        }
        scanner.nextLine(); // Consume newline
        return id;
    }

    private static String getValidatedName(Scanner scanner) {
        String name;
        while (true) {
            name = scanner.nextLine().trim();
            if (name.matches("[A-Za-z]+")) {
                break;
            } else {
                System.out.println("Invalid name. Please enter only alphabetic characters.");
                System.out.print("Enter name: ");
            }
        }
        return name;
    }

    private static String getValidatedLastName(Scanner scanner) {
        String lastName;
        while (true) {
            lastName = scanner.nextLine().trim();
            if (lastName.matches("^[A-Za-z\\-]+$")) {
                break;
            } else {
                System.out.println("Invalid Last Name. Please enter alphabetic characters or hyphens.");
                System.out.print("Enter Last Name: ");
            }
        }
        return lastName;
    }

    private static String getValidatedDate(Scanner scanner) {
        String birthDate;
        while (true) {
            birthDate = scanner.nextLine().trim();
            if (birthDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
                break;
            } else {
                System.out.println("Invalid date format! Please use this specific form: DD/MM/YYYY.");
                System.out.print("Enter Birth Date (DD/MM/YYYY): ");
            }
        }
        return birthDate;
    }

    private static String getValidatedClassLevel(Scanner scanner) {
        String classLevel;
        while (true) {
            classLevel = scanner.nextLine().trim().toUpperCase();
            if (classLevel.matches("OR|FR|SO|JR|SR")) {
                break;
            } else {
                System.out.println("Invalid class level! Please enter one of the following: OR, FR, SO, JR, or SR.");
                System.out.print("Enter Class Level (OR, FR, SO, JR, SR): ");
            }
        }
        return classLevel;
    }

    // Utility functions for formatting names
    private static String formatFirstName(String firstName) {
        return firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase();
    }

    private static String formatLastName(String lastName) {
        if(lastName.contains("-")){
            lastName = lastName.toLowerCase(); // Start by converting everything to lowercase
            String[] parts = lastName.split("-"); // Split by dashes
            lastName=parts[0].substring(0,1).toUpperCase()+parts[0].charAt(1)+"-"+parts[1].substring(0,1).toUpperCase()+parts[1].substring(1);
        }
        else{
            lastName = lastName.toLowerCase();
            lastName=lastName.substring(0,1).toUpperCase()+lastName.charAt(1)+"-"+lastName.substring(2,3).toUpperCase()+lastName.substring(3);
        }

        return lastName;
    }
}
