package controller;

import model.user.User;
import services.AppointmentService;
import storage.AppointmentData;
import storage.AppointmentOutcomeRecordData;
import storage.DoctorScheduleData;

public class DoctorMenuController {
    private static AppointmentService appointmentService = new AppointmentService();
    private User authenticatedUser;
    private AppointmentData appointmentData;
    private AppointmentOutcomeRecordData appointmentOutcomeRecordData;
    private DoctorScheduleData doctorAvailableSlotData;

    public DoctorMenuController(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        this.appointmentData = new AppointmentData();
        this.appointmentData.importData();
        this.appointmentOutcomeRecordData = new AppointmentOutcomeRecordData();
        this.appointmentOutcomeRecordData.importData();
        this.doctorAvailableSlotData = new DoctorScheduleData();
        this.doctorAvailableSlotData.importData();
    }

    public void viewPatientRecords() {
        System.out.println("Viewing Patient Medical Records...");
        // Implement logic here
    }

    public void updatePatientRecords() {
        System.out.println("Updating Patient Medical Records...");
        // Implement logic here
    }

    public void viewPersonalSchedule() {
        System.out.println("Viewing Personal Schedule...");
        // Implement logic here
    }

    public void setAvailability() {
        System.out.println("Setting Availability for Appointments...");
        // Implement logic here
    }

    public void manageAppointments() {
        System.out.println("Accepting or Declining Appointment Requests...");
        // Implement logic here

        appointmentService.respondToAppointment(appointmentData, authenticatedUser);
    }

    public void viewUpcomingAppointments() {
        System.out.println("Viewing Upcoming Appointments...");
        // Implement logic here

        appointmentService.viewUpcomingAppointment(appointmentData.getData(), authenticatedUser);
    }

    public void recordOutcome() {
        System.out.println("Recording Appointment Outcome...");
        // Implement logic here
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
}
