package controller;

import java.util.List;
import model.appointment.Appointment;
import model.user.Patient;
import model.user.User;
import storage.StorageGlobal;

public class PatientMenuController {
    private static final AppointmentController appointmentController = new AppointmentController();
    private static final MedicalRecordController medicalRecordController = new MedicalRecordController();
    private final Patient authenticatedUser;

    public PatientMenuController(User authenticatedUser) {
        this.authenticatedUser = StorageGlobal.PatientStorage().fetchUserByHospitalId(authenticatedUser.getHospitalID());
    }

    public void viewMedicalRecord() {
        System.out.println("Viewing Medical Record...");
        medicalRecordController.viewPatientRecords(authenticatedUser.getHospitalID());
    }

    public void updatePersonalInfo() {
        System.out.println("Updating Personal Information...");
        System.out.println("Enter new contact info: ");
        String contactInfo = System.console().readLine();
        authenticatedUser.setContactInfo(contactInfo);
        StorageGlobal.PatientStorage().savePatient(authenticatedUser);

    }

    public void viewAvailableAppointmentSlots() {
        System.out.println("Viewing Available Appointment Slots...");
        appointmentController.viewAvailableAppointmentSlots(authenticatedUser);
    }

    public void scheduleAppointment() {
        System.out.println("Scheduling an Appointment...");
        appointmentController.scheduleAppointment(authenticatedUser);
    }

    public void rescheduleAppointment() {
        System.out.println("Rescheduling an Appointment...");
        appointmentController.rescheduleAppointment(authenticatedUser);
    }

    public void cancelAppointment() {
        System.out.println("Canceling an Appointment...");
        appointmentController.cancelAppointment(authenticatedUser);
    }

    public void viewScheduledAppointments() {
        System.out.println("Viewing Scheduled Appointments...");
        List<Appointment> appointments = authenticatedUser.getAppointments().stream()
                .filter(a -> a.getAppointmentStatus().equals("PENDING") || a.getAppointmentStatus().equals("CONFIRMED"))
                .toList();
        appointmentController.displayAppointments(appointments, "Scheduled Appointments");
    }

    public void viewPastAppointmentRecords() {
        System.out.println("Viewing Past Appointment Outcome Records...");
        appointmentController.displayAppointmentOutcomeRecords(authenticatedUser.getAppointments(),"Past Appointment Outcome Records");
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
}
