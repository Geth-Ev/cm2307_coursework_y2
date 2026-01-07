import java.util.List;
import java.util.ArrayList;

public class StudentRentalSystem {
    private List<User> users;
    private User currentUser;
    private List<Property> properties;

    public StudentRentalSystem() {
        this.users = new ArrayList<>();
        this.currentUser = null;
        this.properties = new ArrayList<>();
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