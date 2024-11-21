package controller;

import enums.UserRole;
import java.util.Scanner;
import services.AdminService;

public class AdministratorMenuController {

    private final AdminService service;

    public AdministratorMenuController() {
        this.service = new AdminService(); // Initialize AdminService
    }

    public void viewAndManageStaff() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;
        while (choice != 5) {
            System.out.println("Viewing and Managing Hospital Staff...");
            System.out.println("1. View Hospital Staff");
            System.out.println("2. Add Hospital Staff");
            System.out.println("3. Update Hospital Staff");
            System.out.println("4. Remove Hospital Staff");
            System.out.println("5. Back to Adminstrator Menu");
            System.out.print("Enter your choice (1-5): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                System.out.println();
                switch (choice) {
                    case 1 -> {
                        System.out.println("Viewing Hospital Staff...");
                        service.displayStaff();
                        System.out.println();
                    }
                    case 2 -> {
                        System.out.print("Enter Hospital ID: ");
                        String hospitalID = scanner.next();
                        System.out.print("Enter Name: ");
                        String name = scanner.next();
                    
                        System.out.println("Select Role:");
                        System.out.println("1. Doctor");
                        System.out.println("2. Pharmacist");
                        System.out.print("Enter your choice (1-2): ");
                        int roleChoice = scanner.nextInt();
                        UserRole role = UserRole.values()[roleChoice];

                        System.out.print("Enter Gender: ");
                        String gender = scanner.next();
                        
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();

                    
                        service.addStaff(hospitalID, name, role, gender, age);
                        System.out.println();
                    }
                    case 3 -> {
                        System.out.print("Enter Hospital ID of staff to modify: ");
                        String hospitalID = scanner.next();
                        System.out.print("Enter New Name: ");
                        String newName = scanner.next();

                        System.out.println("Select New Role:");
                        System.out.println("1. Doctor");
                        System.out.println("2. Pharmacist");
                        System.out.print("Enter your choice (1-2): ");
                        int roleChoice = scanner.nextInt();
                        UserRole role = UserRole.values()[roleChoice];

                        System.out.print("Enter New Gender: ");
                        String newGender = scanner.next();

                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();

                    
                        service.updateStaff(hospitalID, newName, newGender, age, role);
                        System.out.println();
                    }
                    case 4 -> {
                        System.out.print("Enter Hospital ID of staff to remove: ");
                        String hospitalID = scanner.next();
                        service.removeStaff(hospitalID);
                        System.out.println();
                    }
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 5.");
                }
            }
        }
    }

    public void viewAppointmentDetails() {
        System.out.println("Viewing Appointment Details...");
        // Implement logic here
    }

    public void viewAndManageInventory() {
        System.out.println("Viewing and Managing Medication Inventory...");
        // Implement logic here
        MedicationController medicationController = new MedicationController();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMedication Management System:");
            System.out.println("1. Add Medication");
            System.out.println("2. Update Medication");
            System.out.println("3. Display Medications");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // switch (choice) {
            //     case 1:
            //         System.out.print("Enter Medication ID: ");
            //         String id = scanner.nextLine();
            //         System.out.print("Enter Medication Name: ");
            //         String name = scanner.nextLine();
            //         System.out.print("Enter Medication Company: ");
            //         String company = scanner.nextLine();
            //         System.out.print("Enter Medication Cost: ");
            //         double cost = scanner.nextDouble();
            //         scanner.nextLine(); // Consume newline
            //         System.out.print("Enter Medication Description: ");
            //         String description = scanner.nextLine();
            //         Medication newMed = new Medication(id, name, company, cost, description);
            //         medicationController.addMedication(newMed);
            //         break;

            //     case 2:
            //         System.out.print("Enter Medication ID to update: ");
            //         String updateID = scanner.nextLine();
            //         System.out.print("Enter New Medication Name: ");
            //         String updateName = scanner.nextLine();
            //         System.out.print("Enter New Medication Company: ");
            //         String updateCompany = scanner.nextLine();
            //         System.out.print("Enter New Medication Cost: ");
            //         double updateCost = scanner.nextDouble();
            //         scanner.nextLine(); // Consume newline
            //         System.out.print("Enter New Medication Description: ");
            //         String updateDescription = scanner.nextLine();
            //         medicationController.updateMedication(updateID, updateName, updateCompany, updateCost, updateDescription);
            //         break;

            //     case 3:
            //         medicationController.displayMedications();
            //         break;

            //     case 4:
            //         System.out.println("Exiting Medication Management System.");
            //         return;

            //     default:
            //         System.out.println("Invalid option. Please try again.");
            //         break;
            // }
        }
    }

    public void approveReplenishmentRequests() {
        System.out.println("Approving Replenishment Requests...");
        // Implement logic here
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }

}
