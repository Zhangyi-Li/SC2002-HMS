package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.DoctorSchedule;
import model.user.Doctor;
import model.user.Staff;
import model.user.User;
import storage.StorageGlobal;

public class DoctorMenuController {
    private static final AppointmentController appointmentController = new AppointmentController();
    private static final DoctorScheduleController doctorScheduleController = new DoctorScheduleController();
    private static final MedicalRecordController medicalRecordController = new MedicalRecordController();
    private static final PrescriptionController prescriptionController = new PrescriptionController();

    private final Doctor authenticatedUser;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public DoctorMenuController(User authenticatedUser) {
        Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(authenticatedUser.getHospitalID());
        Doctor doctor = new Doctor(staff);
        this.authenticatedUser = doctor;
    }

    public void viewPatientRecords() {
        System.out.println("Viewing Patient Medical Records...");
        medicalRecordController.viewPatientRecords();
    }

    public void updatePatientRecords() {
        System.out.println("Updating Patient Medical Records...");
        medicalRecordController.addMedicalRecord(authenticatedUser.getHospitalID());
    }

    public void viewPersonalSchedule() {
        System.out.println("Viewing Personal Schedule...");
        DoctorSchedule schedule = authenticatedUser.getSchedule();

        if(schedule != null) {
            schedule.display();
            List<String> slots = doctorScheduleController.getDoctorSlots(authenticatedUser.getHospitalID());
            System.out.println("Available slots for the doctor:");
            slots.forEach(System.out::println);
        } else {
            System.out.println("Doctor's schedule is not set.");
        }
    }

    public void setAvailability() {
        System.out.println("Setting Availability for Appointments...");
        appointmentController.updateDoctorSchedule(authenticatedUser);
    }

    public void manageAppointments() {
        System.out.println("Accepting or Declining Appointment Requests...");
        appointmentController.respondToAppointment(authenticatedUser);
    }

    public void viewUpcomingAppointments() {
        System.out.println("Viewing Upcoming Appointments...");
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> upcomingAppointments = authenticatedUser.getAppointments().stream()
            .filter(appointment -> appointment.getAppointmentStatus().equals("CONFIRMED"))
            .filter(appointment -> {
                LocalDate appointmentDate = appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalTime appointmentTime = LocalTime.parse(appointment.getAppointmentTime(), TIME_FORMATTER);
                return appointmentDate.isAfter(today) || (appointmentDate.isEqual(today) && appointmentTime.isAfter(now));
            })
            .sorted((a1, a2) -> {
                int dateComparison = a1.getAppointmentDate().compareTo(a2.getAppointmentDate());
                if (dateComparison == 0) {
                    return a1.getAppointmentTime().compareTo(a2.getAppointmentTime());
                }
                return dateComparison;
            })
            .collect(Collectors.toList());

        appointmentController.displayAppointments(upcomingAppointments, "Upcoming Appointments");
    }

    public void recordOutcome() {
        System.out.println("Recording Appointment Outcome...");
        appointmentController.recordAppointmentOutcome();
    }

    public void logout() {
        System.out.println("Logging out...");
        // Implement logout logic here, if necessary
    }
}
