import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentRentalSystem system = new StudentRentalSystem();
    /// TODO: I need to add input validation throughout the program to ensure robustness
    /// & Remove any redundant code from unfinished potential functional requirements
    public static void main(String[] args) { // Main program loop
        while (true) {
            if (!system.isLoggedIn())
            {
                showLoginMenu();
            }
            else {
                switch (system.getCurrentUser().getRole()) {
                    case STUDENT:
                        showStudentMenu();
                        break;
                    case HOMEOWNER:
                        showHomeownerMenu();
                        break;
                }
            }
        }
    }

    public static void showLoginMenu() { // Displays if no user is logged in
        System.out.println("--- Login Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Choose an option: ");

        int choice = readNum("");

        switch (choice) {
            case 1:
                handleRegister();
                break;
            case 2:
                handleLogin();
                break;
            case 3:
                System.out.println("Exiting");
                System.exit(0);
                break;
        }

    }

    public static void showStudentMenu() { // Displays if logged in user is a student
        System.out.println("--- Student Menu ---");
        System.out.println("1. Edit profile");
        System.out.println("2. Book a Room");
        System.out.println("3. Logout");
        System.out.println("4. Exit");
        System.out.println("Choose an option: ");

        int choice = readNum("");

        switch (choice) {
            case 1:
                editProfile();
                break;
            case 2:
                handleBooking();
                break;
            case 3:
                system.logout();
                System.out.println("Logged out successfully");
                break;
            case 4:
                System.out.println("Exiting");
                System.exit(0);
                break;
        }
    }

    public static void showHomeownerMenu() { // Displays if logged in user is a homeowner
        System.out.println("--- Homeowner Menu ---");
        System.out.println("1. Edit profile");
        System.out.println("2. View Properties");
        System.out.println("3. Add Property");
        System.out.println("4. Add Room to Property");
        System.out.println("5. Logout");
        System.out.println("6. Exit");
        System.out.println("Choose an option: ");

        int choice = readNum("");

        switch (choice) {
            case 1:
                editProfile();
                break;
            case 2:
                viewProperties();
                break;
            case 3:
                handleAddProperty();
                break;
            case 4:
                handleAddRoom();
                break;
            case 5:
                system.logout();
                System.out.println("Logged out successfully.");
                break;
            case 6:
                System.out.println("Exiting");
                System.exit(0);
                break;
        }
    }

    public static void handleRegister() {
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        int userType = 0;
        while (userType != 1 && userType != 2) {
            System.out.println("Are you a Homeowner or Student? (1=Homeowner, 2=Student): ");
            userType = readNum("");
        }

        int id = (int)(Math.random() * 1000); // Random ID generation
        User newUser;
        if (userType == 1) { // Homeowner registration
            newUser = new Homeowner(id, name, email, password, Role.HOMEOWNER);
        } else { // Student registration
            System.out.println("Please enter your university: ");
            String university = scanner.nextLine();
            System.out.println("Please enter your student ID: ");
            String studentID = scanner.nextLine();
            newUser = new Student(id, name, email, password, university, studentID, Role.STUDENT);
        }

        system.registerUser(newUser);
        System.out.println("Registration successful. You can now log in");
    }

    public static void handleLogin() { // Handles user login upon choice in menu
        System.out.println("Please enter your email: ");
        String email = scanner.nextLine();
        System.out.println("Please enter your password: ");
        String password = scanner.nextLine();

        if (system.login(email, password)) {
            System.out.println("Login successful.");
        }
        else {
            System.out.println("Invalid email or password");
        }
    }
    
    public static void handleAddProperty() {
        System.out.println("Please enter property description: ");
        String description = scanner.nextLine();
        System.out.println("Please enter property address: ");
        String address = scanner.nextLine();
        System.out.println("Please enter property rent: ");
        double rent = readDouble("");

        int id = (int)(Math.random() * 1000); // Random ID generation
        Homeowner owner = (Homeowner) system.getCurrentUser();
        Property newProperty = new Property(id, rent, description, address, 0.0, owner);
        system.addProperty(newProperty);
    }

    public static void handleAddRoom() {
        Homeowner owner = (Homeowner) system.getCurrentUser();
        List<Property> ownerProperties = system.getPropertiesByOwner(owner);
        if (ownerProperties.isEmpty()) {
            System.out.println("You have no properties to add rooms to");
            return;
        }
        viewProperties();
        System.out.println("Please enter Property ID to add room to: ");
        int propertyId = readNum("");
        Property selectedProperty = null;
        for (Property property : ownerProperties) { // Finds property matching the entered ID
            if (property.getId() == propertyId) {
                selectedProperty = property;
                break;
            }
        }
        if (selectedProperty == null) {
            System.out.println("Property not found");
            return;
        }
        System.out.println("Please enter room price: ");
        double price = readDouble("");
        System.out.println("Please enter room type (Single, Single Ensuite, Studio): ");
        String roomType = scanner.next().toUpperCase();
        System.out.println("Please enter amenities (seperated by commas): ");
        String amenitiesInput = scanner.nextLine();

        int id = (int)(Math.random() * 1000); // Random ID generation
        Room room = new Room(id, price, RoomType.valueOf(roomType), amenitiesInput, new ArrayList<>(), selectedProperty);
        selectedProperty.addRoom(room);
        System.out.println("Room added with ID: " + id);
    }

    public static void viewProperties() {
        Homeowner owner = (Homeowner) system.getCurrentUser();
        List<Property> ownerProperties = system.getPropertiesByOwner(owner);
        if (ownerProperties.isEmpty()) {
            System.out.println("You have no properties listed");
            return;
        }
        // Use selection sort
        List<Property> sorted = new ArrayList<>(ownerProperties);
        for (int i = 0; i < sorted.size() - 1; i++) {
            int min = i;
            for (int j = i + 1; j < sorted.size(); j++) {
                if (sorted.get(j).getId() < sorted.get(min).getId()) {
                    min = j;
                }
            }
            if (min != i) {
                Property temp = sorted.get(i);
                sorted.set(i, sorted.get(min));
                sorted.set(min, temp);
            }
        }

        for (Property property : sorted) {
            System.out.println("Property ID: %d, Description: %s, Address: %s, Rent: %.2f, Avg Rating: %.2f".formatted(property.getId(), property.getDescription(), property.getAddress(), property.getRent(), property.getAvgRating()));
        }
    }

    public static void handleBooking() {
        if (system.getCurrentUser().getRole() != Role.STUDENT) { // Ensures that only students can book rooms (Not required as login menu only shows student menu for students, but added as extra safety)
            System.out.println("Only students can book rooms");
            return;
        }

        List<Property> properties = system.getAllProperties(); // Checks if there are any properties available, returns if not
        if (properties.isEmpty()) {
            System.out.println("No properties available for booking");
            return;
        }

        System.out.println("Available Properties:"); // Iterates through and displays all available properties
        for (Property property : properties) {
            System.out.println("Property ID: %d, Description: %s, Address: %s, Rent: %.2f".formatted(property.getId(), property.getDescription(), property.getAddress(), property.getRent()));
        }

        System.out.println("Enter Property ID to view rooms: "); // Asks user to select property by ID
        int propertyId = readNum("");
        if (propertyId < 0) { // Validates input
            System.out.println("Invalid Property ID");
            return;
        }

        Property selectedProperty = null;
        for (Property property : properties) { // Finds property matching the entered ID
            if (property.getId() == propertyId) {
                selectedProperty = property;
                break;
            }
        }
        if (selectedProperty == null) {
            System.out.println("Property not found");
            return;
        }

        List<Room> rooms = selectedProperty.getRooms();
        if (rooms == null || rooms.isEmpty()) {
            System.out.println("No rooms available for this property");
            return;
        }
        System.out.println("Available Rooms:");
        for (Room room : rooms) { // Displays all rooms in the selected property
            System.out.println("Room ID: %d, Type: %s, Price: %.2f".formatted(room.getId(), room.getType(), room.getPrice()));
        }
        System.out.println("Enter Room ID to book: ");
        int selectedRoomId = readNum("");
        Room roomToBook = null;
        for (Room room : rooms) {
            if (room.getId() == selectedRoomId) {
                roomToBook = room;
                break;
            }
        }
        if (roomToBook == null) {
            System.out.println("Room not found");
            return;
        }
        
    }

    public static int readNum(String input) { // Validates int inputs
        while (true) {
            try {
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    public static double readDouble(String prompt) { // Validates double inputs
        while (true) {
            if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Double.parseDouble(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }
    
    public static void editProfile() {
        User current = system.getCurrentUser();
        if (current == null) return;
        System.out.println("--- Edit Profile ---"); // --- Display current profile details ---
        System.out.println("Current info: Name: %s, Email: %s, Password: %s".formatted(current.getName(), current.getEmail(), current.getPassword()));
        if (current.getRole() == Role.STUDENT) {
            System.out.println("University: %s, Student ID: %s".formatted(((Student) current).getUniversity(), ((Student) current).getStudentID()));
        }
        System.out.println("Enter new name (leave blank to keep): "); // --- Ask and edit profile details ---
        String name = scanner.nextLine();
        if (!name.isBlank()) current.setName(name);
        System.out.println("Enter new email (leave blank to keep): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) current.setEmail(email);
        System.out.println("Enter new password (leave blank to keep): ");
        String password = scanner.nextLine();
        if (!password.isBlank()) current.setPassword(password);
        if (current.getRole() == Role.STUDENT) {
            Student s = (Student) current;
            System.out.println("Enter new university (leave blank to keep): ");
            String university = scanner.nextLine();
            if (!university.isBlank()) s.setUniversity(university);
            System.out.println("Enter new student ID (leave blank to keep): ");
            String studentID = scanner.nextLine();
            if (!studentID.isBlank()) s.setStudentID(studentID);
        }
        System.out.println("Profile updated");
    }
}