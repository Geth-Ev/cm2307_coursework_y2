import java.time.LocalDate;

public class Booking {
    private int id;
    private Room room;
    private Student student;
    private LocalDate start;
    private LocalDate end;

    public Booking(int id, Room room, Student student, LocalDate start, LocalDate end) {
        this.id = id;
        this.room = room;
        this.student = student;
        this.start = start;
        this.end = end;
    }

    public int getId() { return id; }
    public Room getRoom() { return room; }
    public Student getStudent() { return student; }
    public LocalDate getStart() { return start; }
    public LocalDate getEnd() { return end; }
    public LocalDate getStartDate() { return start; }
    public LocalDate getEndDate() { return end; }
}