package view;

import java.util.Scanner;

/**
 * Handles the user interface for login-related actions in the Hospital Management System (HMS).
 */
public class LoginView {

    private static final Scanner sc = new Scanner(System.in); // Scanner instance for reading user input

    /**
     * Displays the login menu to the user and captures their choice.
     * Includes options for exiting, logging in, registering as a new patient, or resetting the password.
     *
     * @return The user's choice as an integer between 0 and 3.
     */
    public int showLoginMenu() {

        // Display the welcome message and menu options
        System.out.println("Welcome to Hospital Management System\n");
        System.out.println("Login Menu:");
        System.out.println("0. Exit HMS");
        System.out.println("1. Login");
        System.out.println("2. Register as a New Patient");
        System.out.println("3. Reset Password"); // Option for resetting the password

        // Prompt the user for input
        System.out.print("Enter your choice (0-3): ");
        
        // Loop until the user provides valid input
        while (true) {
            if (sc.hasNextLine()) {
                String input = sc.nextLine().trim(); // Read and trim user input
                
                // Check if the input is numeric
                if (input.matches("[0-9]+")) { 
                    int choice = Integer.parseInt(input); // Convert input to an integer
                    
                    // Validate that the choice is within the acceptable range
                    if (choice >= 0 && choice <= 3) {
                        return choice; // Return the valid choice
                    } else {
                        System.out.println("Invalid input. Please enter a number between 0 and 3!");
                    }
                } else {
                    // Display error message if the input is not numeric
                    System.out.println("Invalid input. Please enter a valid number (0-3).\n");
                }
            } else {
                return 0;
            }
        }
    }

    /**
     * Displays the user type menu to the user and captures their choice.
     * Includes options for going back, selecting patient, or selecting staff.
     *
     * @return The user's choice as an integer between 0 and 2.
     */
    public int showUserTypeMenu() {
        // Display the user type menu options
        System.out.println("");
        System.out.println("Select User Type:");
        System.out.println("0. Back");
        System.out.println("1. Patient");
        System.out.println("2. Staff");

        // Prompt the user for input
        System.out.print("Enter user type (0-2): ");
        
        // Loop until the user provides valid input
        while (true) {
            if (sc.hasNextLine()) {
                String input = sc.nextLine().trim(); // Read and trim user input
                
                // Check if the input is numeric
                if (input.matches("[0-9]+")) {
                    int choice = Integer.parseInt(input); // Convert input to an integer
                    
                    // Validate that the choice is within the acceptable range
                    if (choice >= 0 && choice <= 2) {
                        return choice; // Return the valid choice
                    } else {
                        System.out.println("Invalid input. Please enter a number between 0 and 2!");
                    }
                } else {
                    // Display error message if the input is not numeric
                    System.out.println("Invalid input. Please enter a valid number (0-2).\n");
                }
            } else {
                System.out.println("No input available. Please try again.");
            }
        }

    }

    /**
     * Prompts the user with a specific message and captures their input.
     *
     * @param prompt The message to display to the user.
     * @return The user's input as a string.
     */
    public String getInput(String prompt) {
        System.out.print(prompt); // Display the prompt message
        if (sc.hasNextLine()) {
            return sc.nextLine().trim(); // Read and return trimmed user input
        } else {
            return ""; // Return an empty string if no input is available
        }
    }

    /**
     * Displays a message to the user.
     * Used for showing feedback, instructions, or errors.
     *
     * @param message The message to display.
     */
    public void displayMessage(String message) {
        System.out.println(message); // Print the provided message to the console
    }
}
