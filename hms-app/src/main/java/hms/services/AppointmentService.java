package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.user.User;
import storage.AppointmentData;

public class AppointmentService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Method for Doctor to reject or accept appointment
    public int viewPendingAppointment(List<Appointment> appointments, User user) {
        List<Appointment> pendingAppointments = appointments.stream()
            .filter(appointment -> appointment.getDoctorID().equals(user.getHospitalID()) && appointment.getAppointmentStatus().equals("PENDING"))
            .sorted((a1, a2) -> {
                int dateComparison = a1.getAppointmentDate().compareTo(a2.getAppointmentDate());
                if (dateComparison == 0) {
                    return a1.getAppointmentTime().compareTo(a2.getAppointmentTime());
                }
                return dateComparison;
            })
            .collect(Collectors.toList());

        displayAppointments(pendingAppointments, "Pending Appointments");
        return pendingAppointments.size();
    }

    public void respondToAppointment(AppointmentData appointmentData, User user) {
        List<Appointment> appointments = appointmentData.getData();
        int index = viewPendingAppointment(appointments, user);
        if (index == 0) {
            System.out.println("No pending appointments.");
        } else {
            // allow user to select an index and respond to the appointment with that index
            System.out.println("Enter the index of the appointment you want to respond to: ");
            int appointmentIndex = Integer.parseInt(System.console().readLine());
            if (appointmentIndex < 1 || appointmentIndex > index) {
                System.out.println("Invalid index.");
                return;
            }
            Appointment appointment = appointments.stream()
                .filter(a -> a.getDoctorID().equals(user.getHospitalID()) && a.getAppointmentStatus().equals("PENDING"))
                .skip(appointmentIndex - 1)
                .findFirst()
                .orElse(null);

            if (appointment != null) {
                // prompt user to accept or reject the appointment
                System.out.println("Do you want to accept or reject the appointment?");
                // use number, 0 for accept, 1 for reject, 2 for exit when asking user to input
                System.out.println("0. Accept");
                System.out.println("1. Reject");
                System.out.println("2. Back");
                int response = Integer.parseInt(System.console().readLine());
                switch (response) {
                    case 0 -> {
                        appointment.setAppointmentStatus("CONFIRMED");
                        appointmentData.updateAppointment(appointment);
                        System.out.println("Appointment confirmed.");
                    }
                    case 1 -> {
                        appointment.setAppointmentStatus("REJECTED");
                        appointmentData.updateAppointment(appointment);
                        System.out.println("Appointment rejected.");
                    }
                    case 2 -> System.out.println("");
                    default -> System.out.println("Invalid response.");
                }

            }
        }
    }

    public void viewUpcomingAppointment(List<Appointment> appointments, User user) {
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Appointment> upcomingAppointments = appointments.stream()
            .filter(appointment -> appointment.getDoctorID().equals(user.getHospitalID()) && appointment.getAppointmentStatus().equals("CONFIRMED"))
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

        displayAppointments(upcomingAppointments, "Upcoming Appointments");
    }

    private void displayAppointments(List<Appointment> appointments, String title) {
        System.out.println(title);
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s\n", "Index.", "Patient ID", "Date", "Time", "Status");
        System.out.println("-----------------------------------------------------------------------");

        final int[] index = {1};
        appointments.forEach(appointment -> {
            System.out.printf("%-10d %-15s %-15s %-15s %-15s \n",
                index[0],
                appointment.getPatientID(),
                DATE_FORMATTER.format(appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()),
                appointment.getAppointmentTime(),
                appointment.getAppointmentStatus());
            index[0]++;
        });

        System.out.println("-----------------------------------------------------------------------");
    }
}