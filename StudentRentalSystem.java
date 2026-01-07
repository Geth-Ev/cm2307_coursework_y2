import java.util.List;
import java.util.ArrayList;

public class StudentRentalSystem {
    private List<User> users;
    private User currentUser;

    public StudentRentalSystem() {
        this.users = new ArrayList<>();
        this.currentUser = null;
    }

    public void registerUser(User user) { // Adds user to the system
        users.add(user);
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