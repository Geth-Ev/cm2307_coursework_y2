public class Booking {
    int id;
    Room room;
    Student student;
    LocalDate start;
    LocalDate end;
    BookingStatus status;

    public Booking(Room room, Student student, LocalDate start, LocalDate end) {
        this.room = room;
        this.student = student;
        this.start = start;
        this.end = end;
        this.status = BookingStatus.REQUESTED;
    }

    public void accept() {
        status = BookingStatus.ACCEPTED;
        room.addBooking(start, end);
    }

    public void reject() {
        status = BookingStatus.REJECTED;
    }

    public void cancel() {
        status = BookingStatus.CANCELLED;
        room.removeBooking(start, end);
    }
}