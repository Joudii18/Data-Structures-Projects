import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Manages student records using various data structures for efficient search, insertion, and deletion.
 */
public class StudentRecordsManagementSystem {

    // Hash tables for storing students by ID and class level
    private HashTable<LinkedList<Student>> IDs;
    private HashTable<LinkedList<Student>> classLevels;

    // AVL Trees for storing students by first and last names
    private AVLTree<String> firstNames;
    private AVLTree<String> lastNames;

    /**
     * Constructs a StudentRecordsManagementSystem and initializes it with data from a file.
     */
    public StudentRecordsManagementSystem(String filePath) throws IOException {
        IDs = new HashTable<>(11); // Hash table for IDs with a size of 11
        classLevels = new HashTable<>(); // Hash table for class levels
        firstNames = new AVLTree<>(); // AVL tree for first names
        lastNames = new AVLTree<>(); // AVL tree for last names

        try (Scanner fileInput = new Scanner(new File(filePath))) {
            // Skip the header line
            if (fileInput.hasNextLine()) fileInput.nextLine();

            // Process each line in the file
            while (fileInput.hasNextLine()) {
                String[] details = fileInput.nextLine().split(",");
                int studentId = Integer.parseInt(details[0].trim());
                String lastName = details[1].trim();
                String firstName = details[2].trim();
                String dateOfBirth = details[3].trim();
                String universityLevel = details[4].trim();

                Student student = new Student(studentId, firstName, lastName, dateOfBirth, universityLevel);
                insert(student);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }

    /**
     * Inserts a student into the system.
     */
    public void insert(Student student) {
        if (student != null) {
            IDs.insertByID(student);
            firstNames.insert(student.getFirstName(), student);
            lastNames.insert(student.getLastName(), student);
            classLevels.insertByClassLevel(student);
        } else {
            System.out.println("Student is null");
        }
    }

    /**
     * Deletes a student from the system by their ID.
     */
    public void deleteStudent(int ID) {
        Student student = IDs.deleteByID(ID);
        if (student != null) {
            classLevels.deleteFromClassLevel(student);
            firstNames.deleteAVL(student.getFirstName(), student);
            lastNames.deleteAVL(student.getLastName(), student);
        }
    }

    /**
     * Updates a student's ID in the system.
     */
    public void updateID(int ID) {
        Student student = IDs.find(ID);
        if (student != null) {
            IDs.deleteByID(ID);
            student.ID = ID;
            IDs.insertByID(student);
        }
    }

    /**
     * Updates a student's first name in the system.
     */
    public void updateFirstName(int ID, String newFirstName) {
        Student student = IDs.find(ID);
        if (student != null) {
            firstNames.deleteAVL(student.getFirstName(), student);
            student.firstName = newFirstName;
            firstNames.insert(student.getFirstName(), student);
        }
    }

    /**
     * Updates a student's last name in the system.
     */
    public void updateLastName(int ID, String newLastName) {
        Student student = IDs.find(ID);
        if (student != null) {
            lastNames.deleteAVL(student.getLastName(), student);
            student.lastName = newLastName;
            lastNames.insert(student.getLastName(), student);
        }
    }

    /**
     * Updates a student's birth date in the system.
     */
    public void updateBirthDate(int ID, String birthDate) {
        Student student = IDs.find(ID);
        if (student != null) {
            student.birthDate = birthDate;
        }
    }

    /**
     * Updates a student's class level in the system.
     */
    public void updateClassLevel(int ID, String classLevel) {
        Student student = IDs.find(ID);
        if (student != null) {
            classLevels.deleteFromClassLevel(student);
            student.classLevel = classLevel;
            classLevels.insertByClassLevel(student);
        }
    }

    /**
     * Finds a student by their ID.
     */
    public Student findByStudentID(int ID) {
        return IDs.find(ID);
    }

    /**
     * Finds students by their first name.
     */
    public LinkedList<Student> findByFirstName(String name) {
        return firstNames.search(name);
    }

    /**
     * Finds students by their last name.
     */
    public LinkedList<Student> findByLastName(String name) {
        return name != null ? lastNames.search(name) : null;
    }

    /**
     * Finds students by their academic class level.
     */
    public LinkedList<Student> findClassLevel(String classLevel) {
        return classLevel != null ? classLevels.getList(classLevel) : null;
    }
}
