// package view;

// import controller.PharmacistMenuController;
// import java.util.Scanner;

// public class PharmacistMenuView {

//     private final PharmacistMenuController controller;

//     public PharmacistMenuView(PharmacistMenuController controller) {
//         this.controller = controller;
//     }

//     public void showMenu() {
//         try (Scanner scanner = new Scanner(System.in)) {
//             int choice = -1;

//             while (choice != 5) {
//                 System.out.println("Pharmacist Menu:");
//                 System.out.println("1. View Appointment Outcome Record");
//                 System.out.println("2. Update Prescription Status");
//                 System.out.println("3. View Medication Inventory");
//                 System.out.println("4. Submit Replenishment Request");
//                 System.out.println("5. Logout");
//                 System.out.print("Enter your choice (1-5): ");

//                 if (scanner.hasNextInt()) {
//                     choice = scanner.nextInt();
//                     handleChoice(choice);
//                 } else {
//                     System.out.println("Invalid input. Please enter a number between 1 and 5.");
//                     scanner.next(); // Clear invalid input
//                 }
//                 System.out.println();

//             }
//         }
//         controller.logout();
//     }

//     private void handleChoice(int choice) {
//         switch (choice) {
//             case 1 -> controller.viewAppointmentOutcomeRecord();
//             case 2 -> controller.updatePrescriptionStatus();
//             case 3 -> controller.viewMedicationInventory();
//             case 4 -> controller.submitReplenishmentRequest();
//             case 5 -> System.out.println("");
//             default -> System.out.println("Invalid choice. Please select a number between 1 and 5.");
//         }
//     }

// }
package view;

import controller.PharmacistMenuController;
import java.util.Scanner;

public class PharmacistMenuView {

    private final PharmacistMenuController controller;

    public PharmacistMenuView(PharmacistMenuController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = -1;

            while (choice != 5) {
                System.out.println("Pharmacist Menu:");
                System.out.println("1. View Appointment Outcome Record");
                System.out.println("2. Update Prescription Status");
                System.out.println("3. View Medication Inventory");
                System.out.println("4. Submit Replenishment Request");
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
            case 1 -> controller.viewAppointmentOutcomeRecord();
            case 2 -> controller.updatePrescriptionStatus();
            case 3 -> controller.viewMedicationInventory();
            case 4 -> controller.submitReplenishmentRequest();
            case 5 -> System.out.println("Exiting pharmacist menu...");
            default -> System.out.println("Invalid choice. Please select a number between 1 and 5.");
        }
    }
}

