package view;

import controller.AdministratorMenuController;
import java.util.Scanner;

public class AdministratorMenuView {

    private final AdministratorMenuController controller;

    public AdministratorMenuView(AdministratorMenuController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = -1;

            while (choice != 5) {
                System.out.println("Administrator Menu:");
                System.out.println("1. View and Manage Hospital Staff");
                System.out.println("2. View Appointment Details");
                System.out.println("3. View and Manage Medication Inventory");
                System.out.println("4. Approve Replenishment Requests");
                System.out.println("5. Logout");
                System.out.print("Enter your choice (1-5): ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    handleChoice(choice);
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                    scanner.next(); // Clear invalid input
                }
                System.out.println();
                
            }
        }
        controller.logout();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> controller.viewAndManageStaff();
            case 2 -> controller.viewAppointmentDetails();
            case 3 -> controller.viewAndManageInventory();
            case 4 -> controller.approveReplenishmentRequests();
            default -> System.out.println("Invalid choice. Please select a number between 1 and 5.");
        }
    }

}
