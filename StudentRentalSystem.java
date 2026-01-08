import java.util.List;
import java.util.ArrayList;

public class StudentRentalSystem {
    private List<User> users;
    private User currentUser;
    private List<Property> properties;
    private List<Booking> bookings;

    public StudentRentalSystem() {
        this.users = new ArrayList<>();
        this.currentUser = null;
        this.properties = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public void registerUser(User user) { // Adds user to the system
        users.add(user);
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
            if (property.getOwner() != null && property.getOwner().equals(homeowner)) {
                ownerProperties.add(property);
            }
        }
        return ownerProperties;
    }

    public boolean login(String email, String password) { // Authenticates user credentials
        for (User user : users) {
            if (user.authenticateUser(email, password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    public void addBookingReq(Booking booking) { // Adds booking to the system
        bookings.add(booking);
    }

    public List<Booking> getRoomBookings(Room room) { // Checks if bookings match a room, returns all bookings for that room
        List<Booking> roomBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoom() != null && booking.getRoom().equals(room)) {
                roomBookings.add(booking);
            }
        }
        return roomBookings;
    }

    public List<Booking> getBookingsForOwner(Homeowner owner) { // Checks if bookings match a homeowner's properties, returns all bookings for that homeowner
        List<Booking> ownerBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getRoom() != null && booking.getRoom().getProperty() != null && owner.equals(booking.getRoom().getProperty().getOwner())) {
                ownerBookings.add(booking);
            }
        }
        return ownerBookings;
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