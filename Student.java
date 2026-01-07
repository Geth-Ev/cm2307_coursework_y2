public class Student extends User {
    private String university;
    private String studentID;

    public Student(int id, String name, String email, String password, String university, String studentID, Role role) {
        super(id, name, email, password, role);
        this.university = university;
        this.studentID = studentID;
    }

    public String getUniversity() { return university; }
    public String getStudentID() { return studentID; }

    public void setUniversity(String university) { this.university = university; }
    public void setStudentID(String studentID) { this.studentID = studentID; }
}