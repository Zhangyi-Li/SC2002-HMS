// package controller;

// public class PharmacistMenuController {

//     public void viewAppointmentOutcomeRecord() {
//         System.out.println("Viewing Appointment Outcome Record...");
//         // Implement logic here
//     }

//     public void updatePrescriptionStatus() {
//         System.out.println("Updating Prescription Status...");
//         // Implement logic here
//     }

//     public void viewMedicationInventory() {
//         System.out.println("Viewing Medication Inventory...");
//         // Implement logic here
//     }

//     public void submitReplenishmentRequest() {
//         System.out.println("Submitting Replenishment Request...");
//         // Implement logic here
//     }

//     public void logout() {
//         System.out.println("Logging out...");
//         // Implement logout logic here, if necessary
//     }
// }

package controller;
import java.util.Scanner;

public class PharmacistMenuController {

    private Pharmacist pharmacist;
    private Inventory inventory;
    private AppointmentOutcomeRecord appointmentOutcomeRecord;

    public PharmacistMenuController() {
        this.pharmacist = new Pharmacist();
        this.inventory = new Inventory();
        this.appointmentOutcomeRecord = new AppointmentOutcomeRecord();
    }

    // View Appointment Outcome Record
    public void viewAppointmentOutcomeRecord() {
        appointmentOutcomeRecord.displayRecords();
    }

    // Update Prescription Status
    public void updatePrescriptionStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Prescription ID to update: ");
        String prescriptionID = scanner.nextLine();
        System.out.print("Enter new status (e.g., dispensed, pending): ");
        String newStatus = scanner.nextLine();
        
        boolean updated = pharmacist.updatePrescriptionStatus(prescriptionID, newStatus);
        if (updated) {
            System.out.println("Prescription status updated successfully.");
        } else {
            System.out.println("Failed to update prescription status. Please check the Prescription ID.");
        }
    }

    // View Medication Inventory
    public void viewMedicationInventory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication ID to check stock: ");
        String medicationID = scanner.nextLine();
        
        int stockLevel = inventory.getStock(medicationID);
        System.out.println("Current stock for " + medicationID + ": " + stockLevel);
    }

    // Submit Replenishment Request
    public void submitReplenishmentRequest() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Medication ID for replenishment request: ");
        String medicationID = scanner.nextLine();
        System.out.print("Enter quantity to request: ");
        int quantity = scanner.nextInt();
        
        boolean requestSubmitted = pharmacist.submitReplenishmentRequest(medicationID, quantity);
        if (requestSubmitted) {
            System.out.println("Replenishment request submitted successfully.");
        } else {
            System.out.println("Failed to submit replenishment request. Please try again.");
        }
    }

    // Logout (optional)
    public void logout() {
        System.out.println("Logging out...");
    }
}
