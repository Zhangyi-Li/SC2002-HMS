package controller;

import enums.UserRole;
import java.util.List;
import java.util.Scanner;
import model.appointment.Appointment;
import model.user.Staff;
import services.AdminService;
import storage.StorageGlobal;


public class AdministratorMenuController {

    private final AdminService service;
    private final AppointmentController appointmentController = new AppointmentController();

    public AdministratorMenuController() {
        this.service = new AdminService(); // Initialize AdminService
    }

    public void viewAndManageStaff() {
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        while (choice != 5) {
            System.out.println("=== Viewing and Managing Hospital Staff ===");
            System.out.println("1. View Hospital Staff");
            System.out.println("2. Add Hospital Staff");
            System.out.println("3. Update Hospital Staff");
            System.out.println("4. Remove Hospital Staff");
            System.out.println("5. Back to Administrator Menu");
            System.out.print("Enter your choice (1-5): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                System.out.println();

                switch (choice) {
                    case 1 -> {
                        System.out.println("Viewing Hospital Staff...");
                        try {
                            service.displayStaff();
                        } catch (Exception e) {
                            System.out.println("Error retrieving staff data: " + e.getMessage());
                        }
                        System.out.println();
                    }
                    case 2 -> {
                        try {
                            // Get validated inputs from the user
                            String name = getInput(scanner, "Enter Name: ");
                            UserRole role = getUserRole(scanner);
                            String hospitalID = StorageGlobal.StaffStorage().generateHospitalId(role);
                            String gender = getValidatedGender(scanner); // Validated gender input
                            int age = getIntInput(scanner, "Enter Age: ");

                            // Add the new staff to the system
                            service.addStaff(hospitalID, name, role, gender, age);
                            System.out.println("Staff member added successfully.\n");

                        } catch (Exception e) {
                            // Catch and display any errors during the addition process
                            System.out.println("Error adding staff: " + e.getMessage());
                        }
                    }

                    case 3 -> {
                        try {
                            String hospitalID = getInput(scanner, "Enter Hospital ID of staff to modify: ");
                            String newName = getInput(scanner, "Enter New Name: ");
                            UserRole role = getUserRole(scanner);
                            String newGender = getInput(scanner, "Enter New Gender: ");
                            int newAge = getIntInput(scanner, "Enter New Age: ");

                            service.updateStaff(hospitalID, newName, newGender, newAge, role);
                            System.out.println("Staff member updated successfully.\n");
                        } catch (Exception e) {
                            System.out.println("Error updating staff: " + e.getMessage());
                        }
                    }
                    case 4 -> {
                        try {
                            String hospitalID = getInput(scanner, "Enter Hospital ID of staff to remove: ");
                            // Fetch staff information
                            Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(hospitalID);
                            
                            if (staff == null) {
                                System.out.println("Error: No staff member found with ID: " + hospitalID);
                            } else {
                                // Display staff information
                                System.out.println("Staff Information:");
                                System.out.println("Hospital ID: " + staff.getHospitalID());
                                System.out.println("Name: " + staff.getName());
                                System.out.println("Role: " + staff.getRole());
                                System.out.println("Gender: " + staff.getGender());
                                System.out.println("Age: " + staff.getAge());

                                // Ask for confirmation
                                System.out.print("Are you sure you want to remove this staff member? (yes/no): ");
                                String confirmation = scanner.nextLine().trim().toLowerCase();
                                if (confirmation.equals("yes")) {
                                    service.removeStaff(hospitalID);
                                    System.out.println("Staff member removed successfully.\n");
                                } else {
                                    System.out.println("Operation cancelled. Staff member was not removed.\n");
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Error removing staff: " + e.getMessage());
                        }
                    }

                    case 5 -> {
                    	System.out.println("====================================");
                    	System.out.println("Returning to Administrator Menu...\n");
                    	System.out.println("====================================");
                    }
                    default -> System.out.println("Invalid choice. Please select a number between 1 and 5.\n");
                }
            } else {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    private String getValidatedGender(Scanner scanner) {
        while (true) {
            System.out.print("Enter Gender (Male/Female): ");
            String gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                return gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase(); // Standardize to "Male" or "Female"
            }
            System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
        }
    }

    // Helper method for string input
    private String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    // Helper method for integer input
    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                return value;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Clear the invalid input
            }
        }
    }

    // Helper method for user role selection
    private UserRole getUserRole(Scanner scanner) {
        while (true) {
            System.out.println("Select Role:");
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.print("Enter your choice (1-2): ");
            if (scanner.hasNextInt()) {
                int roleChoice = scanner.nextInt();
                scanner.nextLine(); // Clear the buffer
                if (roleChoice >= 1 && roleChoice <= 2) {
                    return UserRole.values()[roleChoice]; // Adjust for 0-based indexing
                }
            }
            System.out.println("Invalid role selection. Please choose 1 or 2.");
        }
    }


    public void viewAppointmentDetails() {
        System.out.println("=== Viewing Appointment Details ===");

        // Fetch appointments
        List<Appointment> appointments = StorageGlobal.AppointmentStorage().getData();
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }
        
        appointmentController.displayAppointments(appointments, "View all appointments");
    }

    public void viewAndManageInventory() {
        System.out.println("Viewing and Managing Medication Inventory...");
        MedicationController medicationController = new MedicationController();
        try (Scanner scanner = new Scanner(System.in)) {

            int choice;
            do {
                System.out.println("\nMedication Management System:");
                System.out.println("1. Add Medication");
                System.out.println("2. Update Medication");
                System.out.println("3. Display Medications");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    System.out.print("Choose an option: ");
                    scanner.next(); // Consume invalid input
                }
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1 -> {
                        // System.out.println("Adding a new Medication...");
                        // System.out.print("Enter Medication ID: ");
                        // String id = scanner.nextLine();
                        // System.out.print("Enter Medication Name: ");
                        // String name = scanner.nextLine();
                        // System.out.print("Enter Medication Company: ");
                        // String company = scanner.nextLine();
                        // System.out.print("Enter Medication Cost: ");
                        
                        // while (!scanner.hasNextDouble()) {
                        //     System.out.println("Invalid input. Please enter a valid cost.");
                        //     System.out.print("Enter Medication Cost: ");
                        //     scanner.next(); // Consume invalid input
                        // }
                        // double cost = scanner.nextDouble();
                        // scanner.nextLine(); // Consume newline
                        
                        // System.out.print("Enter Medication Description: ");
                        // String description = scanner.nextLine();

                        // // Assuming Medication class and addMedication method are implemented in MedicationController
                        // Medication newMedication = new Medication(id, name, company, cost, description);
                        // medicationController.addMedication(newMedication);
                        // System.out.println("Medication added successfully.");
                    }
                    case 2 -> {
                        // System.out.println("Updating an existing Medication...");
                        // System.out.print("Enter Medication ID to update: ");
                        // String id = scanner.nextLine();
                        // System.out.print("Enter New Medication Name: ");
                        // String newName = scanner.nextLine();
                        // System.out.print("Enter New Medication Company: ");
                        // String newCompany = scanner.nextLine();
                        // System.out.print("Enter New Medication Cost: ");
                        
                        // while (!scanner.hasNextDouble()) {
                        //     System.out.println("Invalid input. Please enter a valid cost.");
                        //     System.out.print("Enter New Medication Cost: ");
                        //     scanner.next(); // Consume invalid input
                        // }
                        // double newCost = scanner.nextDouble();
                        // scanner.nextLine(); // Consume newline
                        
                        // System.out.print("Enter New Medication Description: ");
                        // String newDescription = scanner.nextLine();

                        // medicationController.updateMedication(id, newName, newCompany, newCost, newDescription);
                        // System.out.println("Medication updated successfully.");
                    }
                    case 3 -> {
                        System.out.println("Displaying all Medications...");
                        medicationController.displayMedications();
                    }
                    case 4 -> {
                        System.out.println("Exiting Medication Management System.");
                    }
                    default -> System.out.println("Invalid option. Please choose a number between 1 and 4.");
                }
            } while (choice != 4);
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