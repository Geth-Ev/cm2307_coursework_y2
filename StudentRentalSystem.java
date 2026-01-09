import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class StudentRentalSystem {
    private List<User> users;
    private Map<String, User> usersByEmail; // HashMap for O(1) login
    private List<Property> properties;
    private List<Booking> bookings;
    private User currentUser;

    public StudentRentalSystem() {
        this.users = new ArrayList<>();
        this.usersByEmail = new HashMap<>();
        this.currentUser = null;
        this.properties = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void registerUser(User user) { // Adds user to the system
        users.add(user);
        usersByEmail.put(user.getEmail(), user); // Index by email for fast login
    }

    public void addProperty(Property property) { // Adds property to the system
        properties.add(property);
    }

    public void removeProperty(Property property) { // Removes property from the system
        properties.remove(property);
    }

    public List<Property> getAllProperties() { // Returns all properties
        return properties;
    }

    public List<Property> getPropertiesByOwner(Homeowner homeowner) { // Returns properties owned by specified homeowner
        List<Property> ownerProperties = new ArrayList<>();
        for (Property property : properties) {
            if (property.getOwner() != null && homeowner != null && property.getOwner().getId() == homeowner.getId()) {
                ownerProperties.add(property);
            }
        }
        return ownerProperties;
    }

    public boolean login(String email, String password) { // Authenticates user credentials - O(1) with HashMap
        User user = usersByEmail.get(email);
        if (user != null && user.authenticateUser(email, password)) {
            currentUser = user;
            return true;
        }
        return false;
    }

    public void addBookingReq(Booking booking) { // Adds booking to the system
        bookings.add(booking);
    }

    public List<Booking> getRoomBookings(Room room) { // Checks if bookings match a room, returns all bookings for that room
        List<Booking> roomBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoom() != null && room != null && booking.getRoom().getId() == room.getId()) {
                roomBookings.add(booking);
            }
        }
        return roomBookings;
    }

    public List<Booking> getBookingsForOwner(Homeowner owner) { // Checks if bookings match a homeowner's properties, returns all bookings for that homeowner
        List<Booking> ownerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoom() != null && booking.getRoom().getProperty() != null && owner != null) {
                Homeowner propOwner = booking.getRoom().getProperty().getOwner();
                if (propOwner != null && propOwner.getId() == owner.getId()) {
                    ownerBookings.add(booking);
                }
            }
        }
        return ownerBookings;
    }

    public List<Room> searchRooms(String city, String area, double minPrice, double maxPrice, RoomType type) {
        // Combined search filter by city, area, price range, and room type
        List<Room> matchingRooms = new ArrayList<>();
        for (Property property : properties) {
            // Filter by location (skip filter if blank/null)
            if (city != null && !city.isEmpty() && !property.getCity().equalsIgnoreCase(city)) {
                continue;
            }
            if (area != null && !area.isEmpty() && !property.getArea().equalsIgnoreCase(area)) {
                continue;
            }
            // Then filter rooms by price and type
            for (Room room : property.getRooms()) {
                boolean priceMatch = room.getPrice() >= minPrice && room.getPrice() <= maxPrice;
                boolean typeMatch = (type == null) || (room.getType() == type);
                if (priceMatch && typeMatch) {
                    matchingRooms.add(room);
                }
            }
        }
        return matchingRooms;
    }

    public void logout() { // Logs out the current user
        currentUser = null;
    }

    public User getCurrentUser() { // Returns the currently logged in user
        return currentUser;
    }

    public boolean isLoggedIn() { // Checks if user is logged in
        return currentUser != null;
    }
}