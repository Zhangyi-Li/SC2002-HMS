package controller;

import model.user.User;
import services.AppointmentService;
import storage.AppointmentOutcomeRecordStorage;
import storage.AppointmentStorage;
import storage.DoctorScheduleStorage;

public class DoctorMenuController {
    private static AppointmentService appointmentService = new AppointmentService();
    private User authenticatedUser;
    private AppointmentStorage appointmentStorage;
    private AppointmentOutcomeRecordStorage appointmentOutcomeRecordStorage;
    private DoctorScheduleStorage doctorAvailableSlotStorage;

    public DoctorMenuController(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
        this.appointmentStorage = new AppointmentStorage();
        this.appointmentStorage.importData();
        this.appointmentOutcomeRecordStorage = new AppointmentOutcomeRecordStorage();
        this.appointmentOutcomeRecordStorage.importData();
        this.doctorAvailableSlotStorage = new DoctorScheduleStorage();
        this.doctorAvailableSlotStorage.importData();
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

        appointmentService.respondToAppointment(appointmentStorage, authenticatedUser);
    }

    public void viewUpcomingAppointments() {
        System.out.println("Viewing Upcoming Appointments...");
        // Implement logic here

        appointmentService.viewUpcomingAppointment(appointmentStorage.getData(), authenticatedUser);
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
