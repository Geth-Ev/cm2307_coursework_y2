import java.util.List;
import java.util.ArrayList;

public class Room {
    private double price;
    private RoomType type;
    private List<String> amenities;
    private List<DateRange> availability;
    
    public boolean isAvailable(LocalDate start, LocalDate end) {
        DateRange requestedBooking = new DateRange(start, end);
        for (DateRange : dates) {
            if (dates.overlaps(requestedBooking)) {
                return true;
            }
        }
        return false;
    }
}