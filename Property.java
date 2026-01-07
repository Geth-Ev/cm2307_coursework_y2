import java.util.List;
import java.util.ArrayList;

public class Property {
    private int id;
    private String description;
    private String address;
    private Homeowner owner;
    private double avgRating;
    private double rent;
    private List<Room> rooms = new ArrayList<>();

    public Property(int id, double rent, String description, String address, double avgRating, Homeowner owner) {
        this.id = id;
        this.rent = rent;
        this.description = description;
        this.address = address;
        this.avgRating = 0.0;
        this.owner = owner;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public double getRent() { return rent; }
    public Homeowner getOwner() { return owner; }
    public double getAvgRating() { return avgRating; }

    public List<Room> getRooms() { return rooms; }
    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
        }
    }
    public void removeRoom(Room room) {
        rooms.remove(room);
    }
}