import java.util.LinkedList;

/**
 * Represents a Hash Table designed to store and manage Student objects.
 */
public class HashTable<T> {

    // Array of linked lists to handle collisions via chaining
    private LinkedList<Student>[] data;

    // Size of the hash table (number of buckets)
    private int size;

    /**
     * Constructor to initialize the hash table with a specific size.
     */
    public HashTable(int size) {
        this.data = new LinkedList[size]; // Create an array of linked lists
        this.size = size;
        for (int i = 0; i < size; i++) {
            data[i] = new LinkedList<>(); // Initialize each bucket
        }
    }

    /**
     * Default constructor initializing the hash table with a default size of 5.
     */
    public HashTable() {
        this(5); // Calls the parameterized constructor with size = 5
    }

    /**
     * Inserts a student into the hash table, hashed by their student ID.
     */
    public void insertByID(Student student) {
        int index = hash(student.getStudentID()); // Hash the student ID to get the index
        data[index].add(student);                // Add the student to the appropriate bucket
    }

    /**
     * Inserts a student into the hash table based on their class level.
     */
    public void insertByClassLevel(Student student) {
        switch (student.getClassLevel()) {
            case "OR":
                data[0].add(student);
                break;
            case "FR":
                data[1].add(student);
                break;
            case "SO":
                data[2].add(student);
                break;
            case "JR":
                data[3].add(student);
                break;
            default:
                data[4].add(student);
        }
    }

    /**
     * Deletes a student by their ID, marking the entry as deleted.
     */
    public Student deleteByID(int studentID) {
        int index = hash(studentID); // Hash the student ID to find the bucket
        for (Student student : data[index]) {
            if (student.getStudentID() == studentID) {
                int studentIndex = data[index].indexOf(student);
                Student copy = data[index].get(studentIndex); // Get the student to return
                data[index].set(studentIndex, new Deleted()); // Replace with a placeholder "Deleted" object
                return copy;
            }
        }
        return null; // Return null if the student is not found
    }

    /**
     * Deletes a student from the hash table based on their class level.
     */
    public void deleteFromClassLevel(Student student) {
        switch (student.classLevel) {
            case "OR":
                data[0].remove(student);
                break;
            case "FR":
                data[1].remove(student);
                break;
            case "SO":
                data[2].remove(student);
                break;
            case "JR":
                data[3].remove(student);
                break;
            case "SR":
                data[4].remove(student);
                break;
        }
    }

    /**
     * Finds a student by their ID.
     */
    public Student find(int studentID) {
        int index = hash(studentID); // Hash the student ID to find the bucket
        for (Student student : data[index]) {
            if (student.getStudentID() == studentID) {
                return student; // Return the student if found
            }
        }
        return null; // Return null if not found
    }

    /**
     * Computes the hash value for a given student ID.
     */
    public int hash(int studentID) {
        return studentID % this.size; // Modulo operation for hashing
    }

    /**
     * Returns the list of students for a specific class level.
     */
    public LinkedList<Student> getList(String classLevel) {
        switch (classLevel) {
            case "OR":
                return data[0];
            case "FR":
                return data[1];
            case "SO":
                return data[2];
            case "JR":
                return data[3];
            case "SR":
                return data[4];
            default:
                return null; // Return null for invalid class levels
        }
    }
}