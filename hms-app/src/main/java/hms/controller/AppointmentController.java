package controller;

import model.appointment.Appointment;
import model.user.Doctor;
import model.user.User;

public class AppointmentController {

    // Method restricted to Doctor
    // Method for Doctor to reject or accept appointment
    public void respondToAppointment(Appointment appointment, User user, boolean accept) throws IllegalAccessException {
        if (user instanceof Doctor) {
            if (accept) {
                appointment.setAppointmentStatus("CONFIRMED");
            } else {
                appointment.setAppointmentStatus("REJECTED");
            }
        } else {
            throw new IllegalAccessException("Only doctors can accept/reject  appointment requests.");
        }
    }

   
}
