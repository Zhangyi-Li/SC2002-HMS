package controller;

import java.sql.Date;
import java.util.ArrayList; // For handling lists
import java.util.List; // For handling array lists
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import model.user.Patient;
import model.user.User;
import services.AuthService; // For handling user-related logic
import storage.PatientStorage;
import storage.StaffStorage;
import storage.StorageGlobal; // Import the resetPassword service class
import view.LoginView; // Import the StaffStorage class

/**
 * Controller class to handle authentication, registration, and password reset.
 */
public class AuthController {

    private static AuthService authService = new AuthService(); // Instance of AuthService for authentication
    private static final LoginView loginView = new LoginView(); // Instance of LoginView for user interaction
    private static final Scanner sc;

    static {
        sc = new Scanner(System.in);
    }

    public AuthController() {
    }

    /**
     * Handles the login flow for the application.
     *
     * @return Authenticated User object if successful, or null otherwise.
     */
    public User login() {
        User authenticatedUser = null; // Holds the authenticated user
                    
        int loginTypeChoice = loginView.showUserTypeMenu(); // Display the user type menu and get user choice
        switch (loginTypeChoice) {
            case 0 -> {
                // Go back to the main menu
                break;
            }
            case 1 -> {
                List<User> patients = new ArrayList<>(StorageGlobal.PatientStorage().getData());
                authenticatedUser = authenticateUser(patients);
                        
                if (authenticatedUser != null) {
                    loginView.displayMessage("\nWelcome " + authenticatedUser.getName() + "!");
                } else {
                    loginView.displayMessage("Authentication failed. Please try again...");
                }
            }
            case 2 -> {
                List<User> staffs = new ArrayList<>(StorageGlobal.StaffStorage().getData());
                authenticatedUser = authenticateUser(staffs);
                
                if (authenticatedUser != null) {
                    loginView.displayMessage("\nWelcome " + authenticatedUser.getName() + "!");
                } else {
                    loginView.displayMessage("Authentication failed. Please try again...");
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

    /**
     * Handles the registration flow for new patients.
     */
    public void registerNewPatient() {
        System.out.println("Welcome to HMS New Patient Registration");
        System.out.println("Dear patient, please enter the following information for registration");
        String fullName = this.getInputWithPrompt("Please enter your full name:", (input) -> {
            return !input.trim().isEmpty();
        });
        if (fullName != null) {
            String dateOfBirth = this.getInputWithRetry("Date of Birth [YYYY-MM-DD]:", (input) -> {
                return input.matches("\\d{4}-\\d{2}-\\d{2}");
            }, "Invalid format. Please enter in [YYYY-MM-DD] format.");
            if (dateOfBirth != null) {
                String genderInput = this.getInputWithRetry("Gender [Male or Female]:", (input) -> {
                return input.equalsIgnoreCase("Male") || input.equalsIgnoreCase("Female");
                }, "Invalid gender. Please enter 'Male' or 'Female'.");
                if (genderInput != null) {
                    String var10000 = genderInput.substring(0, 1).toUpperCase();
                    String gender = var10000 + genderInput.substring(1).toLowerCase();
                    String bloodType = this.getInputWithRetry("Blood Type [A+, A, B+, B, O+, O, AB+, AB, other or unknown (enter 00)]:", (input) -> {
                        return Pattern.compile("^(A\\+|A|B\\+|B|O\\+|O|AB\\+|AB|00|other)$", 2).matcher(input).matches();
                    }, "Invalid blood type. Please enter one of the specified options.");
                    if (bloodType != null) {
                        String email = this.getInputWithRetry("Please enter your email address [NA if not applicable]:", (input) -> {
                            return input.contains("@") || input.equalsIgnoreCase("NA");
                        }, "Invalid email format. Please enter a valid email or 'NA'.");
                        if (email != null) {
                            if (PatientStorage.isDuplicateRecord(fullName, gender)) {
                                System.out.println("The user is already registered. Returning to the main menu...");
                            } else {
                                String hospitalId = PatientStorage.generateHospitalId();
                                String defaultPassword = "password";
                                Date dob = java.sql.Date.valueOf(dateOfBirth);
                                Patient patient = new Patient(hospitalId, fullName, defaultPassword, gender, dob, bloodType, email);
                                PatientStorage.savePatient(patient);
                                System.out.println("Registration successful!");
                                System.out.println("Your Hospital ID: " + hospitalId);
                                System.out.println("Your default password is: " + defaultPassword);
                                System.out.println("Please change your password after logging in.");
                                
                            }
                        }
                    }
                }
            }
        }
    }
    
    private String getNewPassword() {
        while(true) {
            System.out.print("Enter your new password: ");
            String password = sc.nextLine().trim();
            if (!this.isPasswordComplex(password)) {
                System.out.println(this.getComplexityErrorMessage());
            } else {
                System.out.print("Confirm your new password: ");
                String confirmPassword = sc.nextLine().trim();
                if (password.equals(confirmPassword)) {
                return password;
                }

                System.out.println("Passwords do not match. Please try again.");
            }
        }
    }

   private boolean isPasswordComplex(String password) {
        if (password.length() >= 6 && password.length() <= 20) {
            boolean hasUpper = false;
            boolean hasLower = false;
            boolean hasDigit = false;
            char[] var8;
            int var7 = (var8 = password.toCharArray()).length;

            for(int var6 = 0; var6 < var7; ++var6) {
                char c = var8[var6];
                if (Character.isUpperCase(c)) {
                hasUpper = true;
                } else if (Character.isLowerCase(c)) {
                hasLower = true;
                } else if (Character.isDigit(c)) {
                hasDigit = true;
                }
            }

            if (hasUpper && hasLower && hasDigit) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
   }

    private String getComplexityErrorMessage() {
        return "Password must be 6-20 characters and contain at least one digit, one lowercase letter, and one uppercase letter.";
    }

    private String getInputWithRetry(String prompt, Predicate<String> validator, String errorMessage) {
        for(int attempts = 0; attempts < 3; ++attempts) {
            System.out.println(prompt);
            String input = sc.nextLine().trim();
            if (validator.test(input)) {
                return input;
            }

            System.out.println(errorMessage);
        }

        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return null;
   }

    private String getInputWithPrompt(String prompt, Predicate<String> validator) {
        System.out.println(prompt);

        for(int attempts = 0; attempts < 3; ++attempts) {
        String input = sc.nextLine().trim();
        if (validator.test(input)) {
            return input;
        }

        System.out.println("Invalid input. Please try again.");
        }

        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return null;
    }

        public void startResetPassword() {
        System.out.println("Welcome to the HMS Password Reset Service");
        // Prompt the user for selection of user type
        System.out.println("Select User Type:");
        System.out.println("1. Patient");
        System.out.println("2. Staff");
        System.out.print("Enter your choice (1-2): ");
        String userTypeChoiceStr = this.getInputWithRetry("Enter your choice (1-2):", (input) -> {
            return input.matches("[1-2]");
        }, "Invalid input. Please enter a number between 1 and 2.");
        int userTypeChoice = Integer.parseInt(userTypeChoiceStr);
        
        String hospitalId = this.getInputWithRetry("Please enter your Hospital ID:", (input) -> {
            return !input.trim().isEmpty();
        }, "Hospital ID cannot be empty. Please enter a valid Hospital ID.");
        if (hospitalId != null) {

            User user = null;
            if (userTypeChoice == 1) {
                user= PatientStorage.fetchUserByHospitalId(hospitalId);
            } else if (userTypeChoice == 2) {
                user = StaffStorage.fetchUserByHospitalId(hospitalId);
            } 

            if (user == null ) {
                System.out.println("Hospital ID not found. Returning to the main menu...");
            } else {
                String id_name = user.getName();
                String fullName = this.getInputWithRetry("Please enter your full name for verification:", (input) -> {
                return input.equalsIgnoreCase(id_name);
                }, "Invalid or mismatched full name. Please try again.");
                if (fullName != null) {
                System.out.println("Verification successful.");
                String newPassword = this.getNewPassword();
                if (newPassword != null) {
                    if (userTypeChoice == 1) {
                        PatientStorage.updatePatientPassword(hospitalId, newPassword);
                    } else if (userTypeChoice == 2) {
                        StaffStorage.updateStaffPassword(hospitalId, newPassword);
                    }
                    System.out.println("Your password has been reset successfully.");
                    System.out.println("You can now log in with your new password.");
                }
                }
            }
        }
    }
}
