package controller;

import model.user.User;
import services.AuthService;
import view.LoginView;


public class AuthController {
    
    private static AuthService authService;
    private static final LoginView loginView = new LoginView();

    public User login() {
        authService = new AuthService();
        
        boolean exit = false;
        User authenticatedUser = null;

        while (!exit) {
            int choice = loginView.showLoginMenu();

            switch (choice) {
                case 0 -> {
                    loginView.displayMessage("\nShutting down HMS...");
                    exit = true;
                    break;
                }
                case 1 -> {
                    authenticatedUser = authenticateUser();
                    if (authenticatedUser!=null) {
                        exit = true;
                        loginView.displayMessage("\nWelcome " + authenticatedUser.getName() + "!");
                    } else {
                        loginView.displayMessage("Authentication failed. Please select a valid option...");
                    }
                    break;
                }
                default -> loginView.displayMessage("Invalid input. Please enter 0-1!");
            }
        }

        return authenticatedUser;
    }

    private User authenticateUser() {
        String id = loginView.getInput("Enter hospitalID: ");
        String password = loginView.getInput("Enter password: ");
        return authService.authenticate(id, password);
    }
}
