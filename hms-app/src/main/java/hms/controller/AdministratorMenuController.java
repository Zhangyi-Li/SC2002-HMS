package controller;

import enums.UserRole;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.Medication;
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
                            String name = getInput(scanner, "Enter Name: ");
                            UserRole role = getUserRole(scanner);
                            String hospitalID = StorageGlobal.StaffStorage().generateHospitalId(role);
                            String gender = getValidatedGender(scanner);
                            int age = getIntInput(scanner, "Enter Age: ");

                            service.addStaff(hospitalID, name, role, gender, age);
                            System.out.println("Staff member added successfully.\n");
                        } catch (Exception e) {
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
                            Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(hospitalID);

                            if (staff == null) {
                                System.out.println("Error: No staff member found with ID: " + hospitalID);
                            } else {
                                System.out.println("Staff Information:");
                                System.out.println("Hospital ID: " + staff.getHospitalID());
                                System.out.println("Name: " + staff.getName());
                                System.out.println("Role: " + staff.getRole());
                                System.out.println("Gender: " + staff.getGender());
                                System.out.println("Age: " + staff.getAge());

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

    public void viewAppointmentDetails() {
        System.out.println("=== Viewing Appointment Details ===");

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
    Scanner scanner = new Scanner(System.in);

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
            scanner.next();
        }
        choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                try {
                    System.out.println("=== Adding a New Medication ===");

                    System.out.print("Enter Medication Name: ");
                    String medicineName = scanner.nextLine().trim();

                    // Check if medication already exists
                    if (MedicationController.isDuplicateRecord(medicineName)) {
                        System.out.println("Error: Medication already exists with the name \"" + medicineName + "\".");
                        break;
                    }

                    System.out.print("Enter Stock: ");
                    while (!scanner.hasNextInt()) {
                        System.out.println("Invalid input. Please enter a valid integer for stock.");
                        scanner.next();
                    }
                    int stock = scanner.nextInt();

                    int lowStockLevelAlert;
                    do {
                        System.out.print("Enter Low Stock Level Alert (must not exceed stock): ");
                        while (!scanner.hasNextInt()) {
                            System.out.println("Invalid input. Please enter a valid integer.");
                            scanner.next();
                        }
                        lowStockLevelAlert = scanner.nextInt();

                        if (lowStockLevelAlert >= stock) {
                            System.out.println("Error: Low Stock Level Alert cannot be higher than Stock. Try again.");
                        }
                    } while (lowStockLevelAlert >= stock);
                    scanner.nextLine(); // Clear buffer

                    Medication newMedication = new Medication(medicineName, stock, lowStockLevelAlert);
                    MedicationController.addMedication(newMedication);
                    System.out.println("Medication added successfully.");

                } catch (Exception e) {
                    System.out.println("Error adding medication: " + e.getMessage());
                }
            }
            case 2 -> {
                try {
                    System.out.println("=== Updating an Existing Medication ===");

                    // Display all existing medications
                    System.out.println("Displaying all Medications:");
                    medicationController.displayMedications();

                    System.out.print("Enter Medication Name to update: ");
                    String medicineName = scanner.nextLine().trim();

                    // Fetch the existing medication
                    Medication existingMedication = MedicationController.fetchMedicationByName(medicineName);
                    if (existingMedication == null) {
                        System.out.println("Error: Medication not found with the name \"" + medicineName + "\".");
                        break;
                    }

                    // Display current details
                    System.out.println("Current Details:");
                    System.out.println("Name: " + existingMedication.getMedicineName());
                    System.out.println("Stock: " + existingMedication.getStock());
                    System.out.println("Low Stock Level Alert: " + existingMedication.getLowStockLevelAlert());

                    // Input for new stock
                    System.out.print("Enter New Stock (or press Enter to keep current value): ");
                    String stockInput = scanner.nextLine().trim();
                    int newStock = stockInput.isEmpty() ? existingMedication.getStock() : Integer.parseInt(stockInput);

                    // Input for new low stock level alert
                    int newLowStockLevelAlert;
                    do {
                        System.out.print("Enter New Low Stock Level Alert (or press Enter to keep current value): ");
                        String lowStockInput = scanner.nextLine().trim();
                        newLowStockLevelAlert = lowStockInput.isEmpty()
                                ? existingMedication.getLowStockLevelAlert()
                                : Integer.parseInt(lowStockInput);

                        if (newLowStockLevelAlert >= newStock) {
                            System.out.println("Error: Low Stock Level Alert cannot be higher than Stock. Try again.");
                        }
                    } while (newLowStockLevelAlert >= newStock);

                    // Update medication
                    existingMedication.setStock(newStock);
                    existingMedication.setLowStockLevelAlert(newLowStockLevelAlert);
                    StorageGlobal.MedicationStorage().saveToFile();

                    System.out.println("Medication updated successfully.");

                } catch (Exception e) {
                    System.out.println("Error updating medication: " + e.getMessage());
                }
            }
            case 3 -> {
                System.out.println("Displaying all Medications...");
                medicationController.displayMedications();
            }
            case 4 -> System.out.println("Exiting Medication Management System.");
            default -> System.out.println("Invalid option. Please choose a number between 1 and 4.");
        }
    } while (choice != 4);
}

    public void approveReplenishmentRequests() {
        System.out.println("=== Approving Replenishment Requests ===");
        
        // Fetch low-stock or out-of-stock medications
        List<Medication> medications = StorageGlobal.MedicationStorage().getData();
        List<Medication> lowStockMedications = new ArrayList<>();

        for (Medication med : medications) {
            if (med.getStock() <= med.getLowStockLevelAlert()) {
                lowStockMedications.add(med);
            }
        }

        // Check if there are any medications to replenish
        if (lowStockMedications.isEmpty()) {
            System.out.println("No medications require replenishment at the moment.");
            return;
        }

        // Display medications that need replenishment
        System.out.println("The following medications require replenishment:");
        System.out.printf("%-20s %-10s %-20s%n", "Name", "Stock", "Low Stock Level Alert");
        for (int i = 0; i < lowStockMedications.size(); i++) {
            Medication med = lowStockMedications.get(i);
            System.out.printf("%d. %-20s %-10d %-20d%n", 
                              (i + 1), 
                              med.getMedicineName(), 
                              med.getStock(), 
                              med.getLowStockLevelAlert());
        }

        Scanner scanner = new Scanner(System.in);
        for (Medication med : lowStockMedications) {
            System.out.println("\nApprove replenishment for: " + med.getMedicineName());
            System.out.println("Current Stock: " + med.getStock());
            System.out.print("Enter new stock level (or press Enter to skip): ");

            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                try {
                    int newStock = Integer.parseInt(input);
                    if (newStock <= med.getStock()) {
                        System.out.println("Error: New stock level must be greater than the current stock.");
                    } else {
                        med.setStock(newStock);
                        System.out.println("Replenishment approved. Stock updated to: " + newStock);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Skipping replenishment for this medication.");
                }
            }
        }

        // Save updated medication data to file
        StorageGlobal.MedicationStorage().saveToFile();
        System.out.println("All approved replenishments have been saved.");
    }


    public void logout() {
        System.out.println("Logging out...");
        // pending implementation code

    }


    private String getValidatedGender(Scanner scanner) {
        while (true) {
            System.out.print("Enter Gender (Male/Female): ");
            String gender = scanner.nextLine().trim();
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                return gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
            }
            System.out.println("Invalid input. Please enter 'Male' or 'Female'.");
        }
    }

    private String getInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();
            }
        }
    }

    private UserRole getUserRole(Scanner scanner) {
        while (true) {
            System.out.println("Select Role:");
            System.out.println("1. Doctor");
            System.out.println("2. Pharmacist");
            System.out.print("Enter your choice (1-2): ");
            if (scanner.hasNextInt()) {
                int roleChoice = scanner.nextInt();
                scanner.nextLine();
                if (roleChoice >= 1 && roleChoice <= 2) {
                    return UserRole.values()[roleChoice];
                }
            }
            System.out.println("Invalid role selection. Please choose 1 or 2.");
        }
    }
}