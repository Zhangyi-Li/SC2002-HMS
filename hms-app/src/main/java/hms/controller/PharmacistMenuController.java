package controller;

import model.user.Pharmacist;
import model.user.Staff;
import model.user.User;
import storage.StorageGlobal;

public class PharmacistMenuController {
    private static final MedicationController medicationController = new MedicationController();
    private static final AppointmentController appointmentController = new AppointmentController();
    private static final PrescriptionController prescriptionController = new PrescriptionController();
    private static final ReplenishmentController replenishmentController = new ReplenishmentController();
    private final Pharmacist authenticatedUser;

    public PharmacistMenuController(User authenticatedUser) {
        Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(authenticatedUser.getHospitalID());
        Pharmacist pharmacist = new Pharmacist(staff);
        this.authenticatedUser = pharmacist;
    }

    public void viewAppointmentOutcomeRecord() {
        System.out.println("Viewing Appointment Outcome Record...");
        appointmentController.displayAppointmentOutcomeRecords(StorageGlobal.AppointmentStorage().getData(),"Appointment Outcome Records");    
    }

    public void updatePrescriptionStatus() {
        System.out.println("Updating Prescription Status...");
        prescriptionController.updatePrescriptionStatus();
    }

    public void viewMedicationInventory() {
        System.out.println("Viewing Medication Inventory...");
        medicationController.displayMedications();
    }

    public void submitReplenishmentRequest() {
        System.out.println("Submitting Replenishment Request...");
        replenishmentController.submitReplenishmentRequest();
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
    
}
