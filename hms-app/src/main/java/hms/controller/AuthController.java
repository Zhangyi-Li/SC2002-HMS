package controller;

import java.util.Scanner;
import model.user.User;
import services.AuthService;

public class AuthController {
    
    private static final Scanner sc = new Scanner(System.in);
    private static AuthService authService;

    public static void Login() {
        authService = new AuthService();
        
        int choice;
        boolean authenticated = false;

        do {
            while (true) {
                System.out.println("<Enter 0 to shutdown system>\n");
                System.out.println("1. Login");
                // forget password option later

                String input = sc.nextLine();

                if (input.matches("[0-9]+")) { // If the input is an integer, proceed with the code
                    choice = Integer.parseInt(input);

                    if (choice < 0 || choice > 1) {
                        System.out.println("Invalid input. Please enter 0-1!");
                    } else {
                        break;
                    }
                } else { // If the input is not an integer, prompt the user to enter again
                    System.out.println("Invalid input. Please enter an integer.\n");
                }

            }

            switch (choice) {
                case 0:
                    System.out.println("Shutting down HMS...");
                    return;
                case 1:
                    if (authenticateUser()!=null) {
                        authenticated = true;
                        System.out.println("Welcome to the Hospital Management System!");
                    } else {
                        System.out.println("Authentication failed. Exiting...");
                    }
                    break;
            }

        } while (!authenticated);
    }

     private static User authenticateUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return authService.authenticate(username, password);
    }
}
