import java.util.List;
import java.util.ArrayList;

public class Property {
    private int id;
    private String description;
    private String address;
    private Homeowner owner;
    private List<Room> rooms = new ArrayList<>();
    private String city;
    private String area;

    public Property(int id, String description, String address, Homeowner owner, String city, String area) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.owner = owner;
        this.city = city;
        this.area = area;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getAddress() { return address; }
    public Homeowner getOwner() { return owner; }
    public String getCity() { return city; }
    public String getArea() { return area; }
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