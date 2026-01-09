import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Room {
    private int id;
    private double price;
    private RoomType type;
    private List<String> amenities = new ArrayList<>();
    private List<DateRange> availability = new ArrayList<>();
    private List<DateRange> bookedDates = new ArrayList<>();
    private Property property;
    
    public Room(int id, double price, RoomType type, List<String> amenities, List<DateRange> bookedDates, Property property) {
        this.id = id;
        this.price = price;
        this.type = type;
        this.amenities = amenities;
        this.bookedDates = bookedDates;
        this.property = property;
    }

    public boolean isAvailable(LocalDate start, LocalDate end) {
        DateRange requestedBooking = new DateRange(start, end);
        if (bookedDates == null || bookedDates.isEmpty()) return true;
        for (DateRange dates : bookedDates) {
            if (dates.overlaps(requestedBooking)) {
                return false;
            }
        }
        return true;
    }

    public void addBooking(LocalDate start, LocalDate end) {
        DateRange bookingRange = new DateRange(start, end);
        if (!isAvailable(start, end)) {
            throw new IllegalStateException("Room unavailable for the requested dates");
        }
        bookedDates.add(bookingRange);
    }

    public void removeBooking(LocalDate start, LocalDate end) {
        DateRange bookingRange = new DateRange(start, end);
        if (bookedDates != null) {
            bookedDates.remove(bookingRange);
        }
    }

    public Property getProperty() { return property; }
    public int getId() { return id; }
    public double getPrice() { return price; }
    public RoomType getType() { return type; }
    public List<String> getAmenities() { return amenities; }
    public List<DateRange> getBookedDates() { return bookedDates; }
}