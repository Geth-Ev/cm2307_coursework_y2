public class Student extends User {
    String university;
    String studentID;

    public Student(int id, String name, String email, String password, String university, String studentID) {
        super(id, name, email, password);
        this.university = university;
        this.studentID = studentID;
    }
}