package controller;

import model.Medication;
import storage.StorageGlobal;

public class MedicationController {

    public MedicationController() {
    }

    // Method to check for duplicate records
    public static boolean isDuplicateRecord(String name) {
        // Check if a medication with the same name already exists
        return StorageGlobal.MedicationStorage().getData().stream()
                .anyMatch(medication -> medication.getMedicineName().equalsIgnoreCase(name));
    }

    // Method to save a new medication
    public static void addMedication(Medication medication) {
        StorageGlobal.MedicationStorage().getData().add(medication); // Add medication to in-memory list
        StorageGlobal.MedicationStorage().saveToFile(); // Persist changes to the CSV file
        System.out.println("Medication saved successfully!");
    }

    // Method to update medication's stock
    public static void updateMedicineStock(String medicineName, int newStock) {
        StorageGlobal.MedicationStorage().getData().stream()
                .filter(medication -> medication.getMedicineName().equalsIgnoreCase(medicineName))
                .findFirst()
                .ifPresent(medication -> medication.setStock(newStock)); // Update the medication's password
        StorageGlobal.MedicationStorage().saveToFile(); // Persist changes to the CSV file
    }

    // Method to fetch a medication by medication Name
    public static Medication fetchMedicationByName(String medicationName) {
        return StorageGlobal.MedicationStorage().getData().stream()
                .filter(medication -> medication.getMedicineName().equalsIgnoreCase(medicationName))
                .findFirst()
                .orElse(null); // Return null if no match is found
    }

    // Display all medications
    public void displayMedications() {
        System.out.println("List of Medications:");
        System.out.printf("%-20s %-10s %-20s %-15s%n", "Name", "Stock", "Low Stock Level Alert", "Stock Status");
        System.out.println("---------------------------------------------------------------");
        for (Medication med : StorageGlobal.MedicationStorage().getData()) {
            System.out.printf("%-20s %-10d %-20s %-15s%n", 
                med.getMedicineName(), 
                med.getStock(), 
                med.getLowStockLevelAlert(), 
                med.getStockStatus());
        }

    }
}
