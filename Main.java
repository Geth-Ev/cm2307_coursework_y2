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
                showMainMenu();
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

    public static void showMainMenu() {

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
            userType = scanner.nextInt();
            scanner.nextLine();
        }

        int id = (int)(Math.random() * 1000); // Random ID generation
        User newUser;
        if (userType == 1) { // Homeowner registration
            newUser = new Homeowner(id, name, email, password);
        } else { // Student registration
            System.out.println("Please enter your university: ");
            String university = scanner.nextLine();
            System.out.println("Please enter your student ID: ");
            String studentID = scanner.nextLine();
            newUser = new Student(id, name, email, password, university, studentID);
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
            scanner.nextLine();
        }
    }
}