package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.user.Staff;
import model.user.User;
import storage.AppointmentStorage;
import storage.StaffStorage;

public class AppointmentService {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Method for patient to schedule an appointment
    public void scheduleAppointment(User patient) {
        try {
            List<Staff> doctors = StaffStorage.getData().stream()
                .filter(user -> user.getRole().name().equals("Doctor"))
                .collect(Collectors.toList());

            viewAvailableDoctors(doctors);

            if (doctors.isEmpty()) {
                System.out.println("No doctors available for appointment scheduling.");
                return;
            }

            final String[] doctorID = new String[1];
            do {
                System.out.println("Enter the Doctor ID you want to schedule an appointment with: ");
                doctorID[0] = System.console().readLine();
            } while (doctors.stream().noneMatch(doctor -> doctor.getHospitalID().equals(doctorID[0])));

            String date = this.getInputWithRetry("Enter the date of the appointment (yyyy-MM-dd):", (input) -> {
                return input.matches("\\d{4}-\\d{2}-\\d{2}") && LocalDate.parse(input, DATE_FORMATTER).isAfter(LocalDate.now());
            }, "Invalid format. Please enter in [YYYY-MM-DD] format and a future date.");

            Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            DoctorScheduleService doctorScheduleService = new DoctorScheduleService();
            List<String> slots = doctorScheduleService.getDoctorSlots(doctorID[0]);
            // for the slots check the current doctor appointment and see if the slot is available
            List<Appointment> doctorAppointments = AppointmentStorage.getData().stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorID[0]))
                .collect(Collectors.toList());

            List<String> availableSlots = slots.stream()
                .filter(slot -> doctorAppointments.stream()
                    .noneMatch(appointment -> new SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate()).equals(date) &&
                        appointment.getAppointmentTime().equals(slot) && !appointment.getAppointmentStatus().equals("REJECTED") && !appointment.getAppointmentStatus().equals("CANCELLED")))
                .collect(Collectors.toList());

            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for the selected date.");
                return;
            }
            System.out.println("Available slots for the doctor:");
            availableSlots.forEach(System.out::println);

            String time = this.getInputWithRetry("Enter the time of the appointment (HH:mm:ss):", availableSlots::contains, "Invalid slot. Please select an available slot.");

            Appointment appointment = new Appointment(patient.getHospitalID(), doctorID[0], appointmentDate, time, "PENDING", null);
            AppointmentStorage.addAppointment(appointment);
            System.out.println("Appointment scheduled successfully.");
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    // Method for Patient to view the available Doctors from StaffStorage
    public void viewAvailableDoctors(List<Staff> doctors) {
        System.out.println("Available Doctors");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s\n", "Index.", "Doctor ID", "Doctor Name", "Gender");
        System.out.println("-----------------------------------------------------------------------");

        final int[] index = {1};
        doctors.forEach(doctor -> {
            System.out.printf("%-10d %-15s %-15s %-15s\n", index[0], doctor.getHospitalID(), doctor.getName(), doctor.getGender());
            index[0]++;
        });

        System.out.println("-----------------------------------------------------------------------");
    }

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

    public void respondToAppointment( User user) {
        List<Appointment> appointments = AppointmentStorage.getData();
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
                        AppointmentStorage.updateAppointment(appointment);
                        System.out.println("Appointment confirmed.");
                    }
                    case 1 -> {
                        appointment.setAppointmentStatus("REJECTED");
                        AppointmentStorage.updateAppointment(appointment);
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

    // Method to get input with retry
    private String getInputWithRetry(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        do {
            System.out.println(prompt);
            input = System.console().readLine();
            if (!validator.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(input));
        return input;
    }
}