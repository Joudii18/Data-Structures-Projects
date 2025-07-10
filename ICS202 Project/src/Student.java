import java.sql.PreparedStatement;

/**
 * Represents a student with basic details such as ID, name, birth date, and class level.
 */
public class Student {

    // Unique identifier for the student
    public int ID;

    // First name of the student
    public String firstName;

    // Last name of the student
    public String lastName;

    // Birth date of the student in DD/MM/YYYY format
    public String birthDate;

    // Academic class level of the student (e.g., OR, FR, SO, JR, SR)
    public String classLevel;

    /**
     * Default constructor for creating an empty student object.
     */
    public Student() {}

    /**
     * Parameterized constructor for initializing a student with all details.
     */
    public Student(int ID, String firstName, String lastName, String birthDate, String classLevel) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.classLevel = classLevel;
    }

    // Getter methods

    /**
     * Retrieves the student's ID.
     */
    public int getStudentID() {
        return ID;
    }

    /**
     * Retrieves the student's birth date.
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * Retrieves the student's first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the student's last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Retrieves the student's academic class level.
     */
    public String getClassLevel() {
        return classLevel;
    }

    // Setter methods

    /**
     * Updates the student's ID.
     */
    public void updateID(int ID) {
        this.ID = ID;
    }

    /**
     * Updates the student's first name.
     */
    public void updateFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Updates the student's last name.
     */
    public void updateLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Updates the student's birth date.
     */
    public void updateBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Updates the student's academic class level.
     */
    public void updateClassLevel(String classLevel) {
        this.classLevel = classLevel;
    }

    /**
     * Provides a string representation of the student.
     */
    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " " + this.birthDate + " " + this.classLevel;
    }
}
