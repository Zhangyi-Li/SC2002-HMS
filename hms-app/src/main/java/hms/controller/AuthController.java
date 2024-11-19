package controller;

// Import necessary classes
import java.util.ArrayList; // For handling lists
import java.util.List; // For handling array lists
import model.user.User;
import services.AuthService; // For handling user-related logic
import services.RegisterNewPatient; // For authentication services
import services.ResetPassword; // For handling new patient registration
import storage.PatientStorage; // Import the resetPassword service class
import storage.StaffStorage; // For displaying login-related views
import view.LoginView; // Import the StaffStorage class

/**
 * Controller class to handle authentication, registration, and password reset.
 */
public class AuthController {

    private static AuthService authService; // Instance of AuthService for authentication
    private static final LoginView loginView = new LoginView(); // Instance of LoginView for user interaction
    private static final RegisterNewPatient registerService = new RegisterNewPatient(); // Instance of RegisterNewPatient for registration
    private static final ResetPassword resetPasswordService = new ResetPassword(); // Instance of ResetPassword for password reset

    public AuthController() {
    }

    /**
     * Handles the login flow for the application.
     *
     * @return Authenticated User object if successful, or null otherwise.
     */
    public User login() {
        authService = new AuthService(); // Initialize the authentication service
        boolean exit = false; // Flag to determine when to exit the loop
        User authenticatedUser = null; // Holds the authenticated user
        
        List<User> patients = new ArrayList<>(PatientStorage.getData());
        List<User> staffs = new ArrayList<>(StaffStorage.getData());

        // Main loop for login, registration, and password reset
        while (!exit) {
            int choice = loginView.showLoginMenu(); // Display the login menu and get user choice

            switch (choice) {
                case 0 -> {
                    // Handle exit choice
                    loginView.displayMessage("\nShutting down HMS...");
                    exit = true;
                }
                case 1 -> {
                    // Handle login choice
                    
                    int loginTypeChoice = loginView.showUserTypeMenu(); // Display the user type menu and get user choice
                    switch (loginTypeChoice) {
                        case 0 -> {
                            // Go back to the main menu
                            break;
                        }
                        case 1 -> {

                            authenticatedUser = authenticateUser(patients);
                                    
                            if (authenticatedUser != null) {
                                exit = true; // Exit loop if login is successful
                                loginView.displayMessage("\nWelcome " + authenticatedUser.getName() + "!");
                            } else {
                                loginView.displayMessage("Authentication failed. Please try again...");
                            }
                        }
                        case 2 -> {
                            authenticatedUser = authenticateUser(staffs);
                            
                            if (authenticatedUser != null) {
                                exit = true; // Exit loop if login is successful
                                loginView.displayMessage("\nWelcome " + authenticatedUser.getName() + "!");
                            } else {
                                loginView.displayMessage("Authentication failed. Please try again...");
                            }
                        }
                    }
                }
                case 2 -> {
                    // Call the startRegistration method from the registerNewPatient class
                    registerService.startRegistration();
                }
                case 3 -> {
                    // Call the resetPassword method from the resetPassword class
                    resetPasswordService.startResetPassword();
                }
                default -> {
                    // Handle invalid input
                    loginView.displayMessage("Invalid input. Please enter a valid option (0-3)!");
                }
            }
        }

        return authenticatedUser; // Return the authenticated user
    }

    /**
     * Handles the authentication of a user.
     *
     * @return Authenticated User object if successful, or null otherwise.
     */
    private User authenticateUser(List<User> users) {
        String id = loginView.getInput("Enter hospitalID: "); // Prompt for Hospital ID
        String password = loginView.getInput("Enter password: "); // Prompt for password
        return authService.authenticate(users, id, password); // Authenticate user using AuthService
    }
}
