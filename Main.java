import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentRentalSystem system = new StudentRentalSystem();
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
        System.out.println("2. Search Rooms");
        System.out.println("3. Book a Room");
        System.out.println("4. Logout");
        System.out.println("5. Exit");
        System.out.println("Choose an option: ");

        int choice = readNum("");

        switch (choice) {
            case 1:
                editProfile();
                break;
            case 2:
                handleSearchRooms();
                break;
            case 3:
                handleBooking();
                break;
            case 4:
                system.logout();
                System.out.println("Logged out successfully");
                break;
            case 5:
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

    public static String readEmail(String input) { // Validates email inputs
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"; // Email regex pattern
        if (input != null && !input.isEmpty()) System.out.print(input);
        while (true) {
            String email = scanner.nextLine();
            try {
                if (email.matches(emailRegex)) {
                    return email;
                } else {
                    System.out.println("Invalid email format. Please enter a valid email: ");
                }
            } catch (Exception e) {
                System.out.println("An error occurred. Please enter a valid email: ");
            }
        }
    }

    public static int readNum(String input) { // Validates int inputs
        while (true) {
            try {
                String number = scanner.nextLine();
                return Integer.parseInt(number.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number: ");
            }
        }
    }
    
    public static double readDouble(String input) { // Validates double inputs
        while (true) {
            if (input != null && !input.isEmpty()) System.out.print(input);
            String number = scanner.nextLine();
            try {
                return Double.parseDouble(number.trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    public static void handleRegister() {
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Please enter your email: ");
        String email = readEmail("");
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
        String email = readEmail("");
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
        System.out.println("Please enter property street: ");
        String address = scanner.nextLine();
        System.out.println("Please enter property city: ");
        String city = scanner.nextLine();
        System.out.println("Please enter property area: ");
        String area = scanner.nextLine();

        int id = (int)(Math.random() * 1000); // Random ID generation
        Homeowner owner = (Homeowner) system.getCurrentUser();
        Property newProperty = new Property(id, description, address, owner, city, area);
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
        List<String> amenitiesInput = handleAmenitiesInput();

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
            System.out.println("Property ID: %d, Description: %s, Address: %s, City: %s, Area: %s".formatted(property.getId(), property.getDescription(), property.getAddress(), property.getCity(), property.getArea()));
        }
    }
    public static List<String> handleAmenitiesInput() { // Makes user input into a list of amenities
        scanner.nextLine();
        String amenitiesLine = scanner.nextLine();
        String[] amenitiesArray = amenitiesLine.split(",");
        List<String> amenitiesList = new ArrayList<>();
        for (String amenity : amenitiesArray) {
            amenitiesList.add(amenity.trim());
        }
        return amenitiesList;
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
            System.out.println("Property ID: %d, Description: %s, Address: %s, City: %s, Area: %s".formatted(property.getId(), property.getDescription(), property.getAddress(), property.getCity(), property.getArea()));
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
            System.out.println("Room ID: %d, Type: %s, Price: %.2f, Amenities: %s".formatted(room.getId(), room.getType(), room.getPrice(), room.getAmenities()));
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

    public static void handleSearchRooms() { // Handles room search functionality
        System.out.println("--- Search Rooms ---");
        System.out.println("Enter city (leave blank for any): ");
        String city = scanner.nextLine().trim();
        System.out.println("Enter area (leave blank for any): ");
        String area = scanner.nextLine().trim();
        System.out.println("Enter minimum price: ");
        double minPrice = readDouble("");
        System.out.println("Enter maximum price: ");
        double maxPrice = readDouble("");
        System.out.println("Enter room type (Single, Single Ensuite, Studio): ");
        String roomTypeInput = scanner.nextLine().toUpperCase();
        RoomType roomType = null;
        try {
            roomType = RoomType.valueOf(roomTypeInput);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid room type. Using Single as default");
            roomType = RoomType.SINGLE;
        }
        List<Room> searchResults = system.searchRooms(city, area, minPrice, maxPrice, roomType);
        if (searchResults.isEmpty()) {
            System.out.println("No rooms found");
            return;
        }
        System.out.println("--- Search Results ---");
        for (int i = 0; i < searchResults.size(); i++) {
            Room room = searchResults.get(i);
            System.out.println("Room ID: %d, Type: %s, Price: £%.2f, Property: %s".formatted(room.getId(), room.getType(), room.getPrice(), room.getProperty().getAddress()));
        }
        System.out.println("Enter room number to view details (0 to go back): "); // Rooms are indexed from 1 for selection rathert than ID
        int choice = readNum("");
        if (choice == 0) return;
        if (choice > 0 && choice <= searchResults.size()) {
            viewRoomDetails(searchResults.get(choice - 1));
        } else {
            System.out.println("Invalid selection.");
        }
    }
    
    public static void viewRoomDetails(Room room) { // Displays detailed room info and booking option
        Property property = room.getProperty();
        System.out.println("--- Room Details ---");
        System.out.println("Room ID: " + room.getId());
        System.out.println("Type: " + room.getType());
        System.out.println("Price: £" + room.getPrice() + " per month");
        System.out.println("Amenities: " + String.join(", ", room.getAmenities()));
        System.out.println("--- Property Details ---");
        System.out.println("Address: " + property.getAddress());
        System.out.println("City: " + property.getCity());
        System.out.println("Area: " + property.getArea());
        System.out.println("Description: " + property.getDescription());
        System.out.println("Owner: " + property.getOwner().getName());
        System.out.println("1. Book this room");
        System.out.println("2. Back to search results");
        System.out.println("Choose an option: ");
        int choice = readNum("");
        if (choice == 1) {
            handleBooking();
        }
        else {
            return;
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