package controller;

import model.user.User;
import services.AppointmentService;

public class PatientMenuController {
    private static AppointmentService appointmentService = new AppointmentService();
    private User authenticatedUser;

    public PatientMenuController(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public void viewMedicalRecord() {
        System.out.println("Viewing Medical Record...");
        // Implement logic here
    }

    public void updatePersonalInfo() {
        System.out.println("Updating Personal Information...");
        // Implement logic here
    }

    public void viewAvailableAppointmentSlots() {
        System.out.println("Viewing Available Appointment Slots...");
        // Implement logic here
    }

    public void scheduleAppointment() {
        System.out.println("Scheduling an Appointment...");
        // Implement logic here
        appointmentService.scheduleAppointment(authenticatedUser);
    }

    public void rescheduleAppointment() {
        System.out.println("Rescheduling an Appointment...");
        // Implement logic here
    }

    public void cancelAppointment() {
        System.out.println("Canceling an Appointment...");
        // Implement logic here
    }

    public void viewScheduledAppointments() {
        System.out.println("Viewing Scheduled Appointments...");
        // Implement logic here
    }

    public void viewPastAppointmentRecords() {
        System.out.println("Viewing Past Appointment Outcome Records...");
        // Implement logic here
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
}
