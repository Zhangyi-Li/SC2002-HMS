package view;

import java.util.Scanner;

public class LoginView {
    private static final Scanner sc = new Scanner(System.in);

    public int showLoginMenu() {
        System.out.println("Welcome to Hospital Management System\n");
        System.out.println("Login Menu:");
        System.out.println("0. Exit HMS");
        System.out.println("1. Login");
        // forget password option later

        System.out.print("Enter your choice (0-1): ");
        while (true) {
            String input = sc.nextLine();
            if (input.matches("[0-9]+")) { // Check if input is an integer
                int choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 1) {
                    return choice;
                } else {
                    System.out.println("Invalid input. Please enter 0-1!");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.\n");
            }
        }
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return sc.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }
}
