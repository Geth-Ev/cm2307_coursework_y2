import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StudentRentalSystem system = new StudentRentalSystem();

    public static void main(String[] args) {
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

    public static void showLoginMenu() {
        System.out.println("--- Login Menu ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

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

    public static void showStudentMenu() {
        System.out.println("--- Student Menu ---");
        System.out.println("1. Edit profile");
        System.out.println("2. Logout");
        System.out.println("3. Exit");
        System.out.println("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                editProfile();
                break;
            case 2:
                system.logout();
                System.out.println("Logged out successfully.");
                break;
            case 3:
                System.out.println("Exiting");
                System.exit(0);
                break;
        }
    }

    public static void showHomeownerMenu() {
        System.out.println("--- Homeowner Menu ---");
    }
    //add show homeownermenu method here

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
            userType = scanner.nextInt();
            scanner.nextLine();
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
        System.out.println("Registration successful. You can now log in.");
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
            System.out.println("Invalid email or password.");
        }
    }

    public static void editProfile() {
        User current = system.getCurrentUser();
        if (current == null) return;
        System.out.println("--- Edit Profile ---");
        System.out.println("Current info: Name: %s, Email: %s, Password: %s".formatted(current.getName(), current.getEmail(), current.getPassword()));
        if (current.getRole() == Role.STUDENT) {
            System.out.println("University: %s, Student ID: %s".formatted(((Student) current).getUniversity(), ((Student) current).getStudentID()));
        }
        System.out.println("Enter new name (leave blank to keep): ");
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
        System.out.println("Profile updated.");
    }
}