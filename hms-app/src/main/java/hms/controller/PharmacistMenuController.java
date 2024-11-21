package controller;

public class PharmacistMenuController {
    private static final MedicationController medicationController = new MedicationController();

    public void viewAppointmentOutcomeRecord() {
        System.out.println("Viewing Appointment Outcome Record...");
        // Implement logic here
    }

    public void updatePrescriptionStatus() {
        System.out.println("Updating Prescription Status...");
        // Implement logic here
    }

    public void viewMedicationInventory() {
        System.out.println("Viewing Medication Inventory...");
        medicationController.displayMedications();
    }

    public void submitReplenishmentRequest() {
        System.out.println("Submitting Replenishment Request...");
        
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
    
}
