package model.user;

import enums.UserRole;
import java.util.ArrayList;
import java.util.List;
import model.appointment.Appointment;
import model.appointment.AppointmentOutcomeRecord;

public class Doctor extends User {
    private List<Appointment> appointments;

    public Doctor(String hospitalID, String name, String password, String email, String gender) {
        super(hospitalID, name, password, email, UserRole.Doctor, gender);
        this.appointments = new ArrayList<>();
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void removeAppointment(Appointment appointment) {
        appointments.remove(appointment);
    }

    public void viewSchedule() {
        for (Appointment appointment : appointments) {
            appointment.display();
        }
    }

    public void setAvailability(String date, String time) {
        // Implement logic to set availability
    }

    public void acceptAppointment(Appointment appointment) {
        appointment.setAppointmentStatus("CONFIRMED");
    }

    public void declineAppointment(Appointment appointment) {
        appointment.setAppointmentStatus("REJECTED");
    }

    public void recordAppointmentOutcome(Appointment appointment, String serviceType, List<String> medicationIDs, String notes) {
        AppointmentOutcomeRecord outcomeRecord = new AppointmentOutcomeRecord(
            appointment.getAppointmentID(),
            serviceType,
            medicationIDs,
            notes
        );
    }
}
