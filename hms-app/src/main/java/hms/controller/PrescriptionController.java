package controller;

import enums.PrescriptionStatus;
import java.util.List;
import java.util.stream.Collectors;
import model.Prescription;
import storage.StorageGlobal;

public class PrescriptionController {
    private static final MedicationController medicationController = new MedicationController();

    public PrescriptionController() {
    }

    // Method to view prescriptions by appointment ID
    public void viewPrescriptionsByAppointmentID(String appointmentID) {
        System.out.println("Prescriptions for Appointment ID: " + appointmentID);
        System.out.printf("%-20s %-20s %-10s %-15s%n", "Medication Name", "Quantity", "Status", "Stock Status");
        System.out.println("---------------------------------------------------------------");
        for (Prescription prescription : StorageGlobal.PrescriptionStorage().getData()) {
            if (prescription.getAppointmentID().equalsIgnoreCase(appointmentID)) {
                System.out.printf("%-20s %-20d %-10s %-15s%n",
                        prescription.getMedicationName(),
                        prescription.getQuantity(),
                        prescription.getStatus(),
                        medicationController.fetchMedicationByName(prescription.getMedicationName()).getStockStatus());
            }
        }
    }

    // Method to contain the addition of a new prescription
    public void addPrescription(String appointmentID) {
        medicationController.displayMedications();
        while(true){
            // have 2 menu options: add prescriptions and back
            System.out.println("");
            System.out.println("Add Prescription");
            System.out.println("Select an option:");
            System.out.println("0. Add Prescription");
            System.out.println("1. Back");
            String choice = getInputWithRetry("Enter choice: ",
                    input -> input.matches("[0-1]"),
                    "Invalid choice! Please try again.");
            if (choice.equals("0")) {
                String medicationName = getInputWithRetry("Enter Medication Name: ",
                        input -> medicationController.fetchMedicationByName(input) != null && !isPrescriptionExists(appointmentID, input),
                        "Invalid Medication Name! Please try again.");
                
                int quantity = Integer.parseInt(getInputWithRetry("Enter Quantity: ",
                        input -> input.matches("\\d+") && Integer.parseInt(input) > 0,
                        "Invalid Quantity! Please try again."));
                        
                Prescription prescription = new Prescription(appointmentID, medicationName, quantity);
                addPrescription(prescription);
            } else {
                return;
            }
        }
    }

    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
        StorageGlobal.PrescriptionStorage().addPrescription(prescription);
        System.out.println("Prescription added successfully!");
    }
    

    // Method to update prescription status
    public void updatePrescriptionStatus() {

        List<Prescription> prescriptions = StorageGlobal.PrescriptionStorage().getData().stream()
                .filter(prescription -> prescription.getStatus().equals(PrescriptionStatus.PENDING))
                .collect(Collectors.toList());

        displayPrescriptions(prescriptions, "Prescriptions Pending Approval");

        if (prescriptions.isEmpty()) {
            System.out.println("No prescriptions pending approval!");
            return;
        }

        while(true){
            System.out.println("");
            System.out.println("Update Prescription Status");
            System.out.println("Select an option:");
            System.out.println("0. Approve Prescription");
            System.out.println("1. Back");
            String choice = getInputWithRetry("Enter choice: ",
                    input -> input.matches("[0-1]"),
                    "Invalid choice! Please try again.");
            if (choice.equals("0")) {
                Prescription prescription = prescriptions.get(Integer.parseInt(getInputWithRetry("Enter Prescription Index: ",
                        input -> input.matches("\\d+") && Integer.parseInt(input) >= 0 && Integer.parseInt(input) < prescriptions.size(),
                        "Invalid Prescription Index! Please try again.")));

                //check if medication is in stock
                if (MedicationController.fetchMedicationByName(prescription.getMedicationName()).getStock() < prescription.getQuantity()) {
                    System.out.println("Medication does not have enough stock!");
                    return;
                }

                MedicationController.updateMedicineStock(prescription.getMedicationName(),
                        MedicationController.fetchMedicationByName(prescription.getMedicationName()).getStock() - prescription.getQuantity());

                prescription.setStatus(PrescriptionStatus.DISPENSE);

                StorageGlobal.PrescriptionStorage().addPrescription(prescription);

            } else {
                return;
            }
        }
    }

    // Method to display prescriptions
    public void displayPrescriptions(List<Prescription> prescriptions, String title) {
        System.out.println(title);
        System.out.printf("%10s %-20s %-10s %-15s%n", "Index", "Medication Name", "Quantity", "Status");
        System.out.println("-----------------------------------------------------------------------------");
        int index = 0;
        for (Prescription prescription : prescriptions) {
            System.out.printf("%10s %-20s %-10d %-15s%n",
                    index++,
                    prescription.getMedicationName(),
                    prescription.getQuantity(),
                    prescription.getStatus());
        }
    }

    // Method to check if a prescription exists
    public boolean isPrescriptionExists(String appointmentID, String medicationName) {
        return StorageGlobal.PrescriptionStorage().getData().stream()
                .anyMatch(prescription -> prescription.getAppointmentID().equalsIgnoreCase(appointmentID)
                        && prescription.getMedicationName().equalsIgnoreCase(medicationName));
    }

    // Method to get input with retry
    private String getInputWithRetry(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        do {
            System.out.println(prompt);
            input = System.console().readLine();
            if (!validator.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(input));
        return input;
    }

}
