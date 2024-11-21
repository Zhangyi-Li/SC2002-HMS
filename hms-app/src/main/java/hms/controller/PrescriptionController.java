package controller;

import java.util.List;
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

    // Method to add a new prescription
    public void addPrescription(Prescription prescription) {
        StorageGlobal.PrescriptionStorage().addPrescription(prescription);
        System.out.println("Prescription added successfully!");
    }
    

    // Method to update prescription status
    public void updatePrescriptionStatus(Prescription prescription) {
        Prescription existingPrescription = StorageGlobal.PrescriptionStorage().findPresciption(prescription);
        if (existingPrescription != null) {
            existingPrescription.setStatus(prescription.getStatus());
            StorageGlobal.PrescriptionStorage().saveToFile();
            System.out.println("Prescription status updated successfully!");
        } else {
            System.out.println("Prescription not found!");
        }
    }

    // Method to display prescriptions
    public void displayPrescriptions(List<Prescription> prescriptions, String title) {
        System.out.println(title);
        System.out.printf("%-20s %-20s %-10s %-15s%n", "Appointment ID", "Medication Name", "Quantity", "Status");
        System.out.println("---------------------------------------------------------------");
        for (Prescription prescription : prescriptions) {
            System.out.printf("%-20s %-20s %-10d %-15s%n",
                    prescription.getAppointmentID(),
                    prescription.getMedicationName(),
                    prescription.getQuantity(),
                    prescription.getStatus());
        }
    }

}
