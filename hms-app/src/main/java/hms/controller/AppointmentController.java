package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.Prescription;
import model.appointment.Appointment;
import model.appointment.DoctorSchedule;
import model.user.Doctor;
import model.user.Patient;
import model.user.Staff;
import storage.StorageGlobal;

public class AppointmentController {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private DoctorScheduleController doctorScheduleController = new DoctorScheduleController();
    private PrescriptionController prescriptionController = new PrescriptionController();
    
    // Method for patient to cancel appointment
    public void cancelAppointment(Patient patient) {
        List<Appointment> appointments = patient.getAppointments().stream()
            .filter(appointment -> {
                LocalDate appointmentDate = appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalTime appointmentTime = LocalTime.parse(appointment.getAppointmentTime(), TIME_FORMATTER);
                return appointmentDate.isAfter(LocalDate.now()) || (appointmentDate.isEqual(LocalDate.now()) && appointmentTime.isAfter(LocalTime.now()));
            })
            .collect(Collectors.toList());

        displayAppointments(appointments, "Scheduled Appointments");

        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return;
        }

        System.out.println("Enter the index of the appointment you want to cancel: ");
        int appointmentIndex = Integer.parseInt(System.console().readLine());
        if (appointmentIndex < 1 || appointmentIndex > appointments.size()) {
            System.out.println("Invalid index.");
            return;
        }

        Appointment appointment = appointments.get(appointmentIndex - 1);
        if (appointment.getAppointmentStatus().equals("CONFIRMED")) {
            System.out.println("Cannot cancel confirmed appointment.");
            return;
        }

        appointment.setAppointmentStatus("CANCELLED");
        StorageGlobal.AppointmentStorage().updateAppointment(appointment);
        patient.setAppointment(StorageGlobal.AppointmentStorage().getData());
        System.out.println("Appointment cancelled successfully.");
    }

    // Method for patient to schedule an appointment
    public void scheduleAppointment(Patient patient) {
        try {
            List<Staff> doctors = StorageGlobal.StaffStorage().getData().stream()
                .filter(user -> user.getRole().name().equals("Doctor"))
                .collect(Collectors.toList());

            viewAvailableDoctors(doctors);

            if (doctors.isEmpty()) {
                System.out.println("No doctors available for appointment scheduling.");
                return;
            }

            String doctorID = getInputWithRetry(
                "Enter the Doctor ID you want to schedule an appointment with: ",
                input -> doctors.stream().anyMatch(doctor -> doctor.getHospitalID().equals(input)),
                "Invalid Doctor ID. Please enter a valid Doctor ID."
            );

            String date = this.getInputWithRetry("Enter the date of the appointment (yyyy-MM-dd):", (input) -> {
                return input.matches("\\d{4}-\\d{2}-\\d{2}") && LocalDate.parse(input, DATE_FORMATTER).isAfter(LocalDate.now());
            }, "Invalid format. Please enter in [YYYY-MM-DD] format and a future date.");

            Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            List<String> slots = doctorScheduleController.getDoctorSlots(doctorID);
            // for the slots check the current doctor appointment and see if the slot is available
            List<Appointment> doctorAppointments = StorageGlobal.AppointmentStorage().getData().stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorID))
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

            Appointment appointment = new Appointment(patient.getHospitalID(), doctorID, appointmentDate, time, "PENDING");
            StorageGlobal.AppointmentStorage().addAppointment(appointment);
            System.out.println("Appointment scheduled successfully.");
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    // Method for patient to rescedule an appointment
    public void rescheduleAppointment(Patient patient) {
        List<Appointment> appointments = patient.getAppointments().stream()
            .filter(appointment -> {
                LocalDate appointmentDate = appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalTime appointmentTime = LocalTime.parse(appointment.getAppointmentTime(), TIME_FORMATTER);
                return appointmentDate.isAfter(LocalDate.now()) || (appointmentDate.isEqual(LocalDate.now()) && appointmentTime.isAfter(LocalTime.now()));
            })
            .collect(Collectors.toList());

        displayAppointments(appointments, "Scheduled Appointments");

        if (appointments.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return;
        }

        System.out.println("Enter the index of the appointment you want to reschedule: ");
        int appointmentIndex = Integer.parseInt(System.console().readLine());
        if (appointmentIndex < 1 || appointmentIndex > appointments.size()) {
            System.out.println("Invalid index.");
            return;
        }

        Appointment current_appointment = appointments.get(appointmentIndex - 1);
        if (current_appointment.getAppointmentStatus().equals("CONFIRMED")) {
            System.out.println("Cannot reschedule confirmed appointment.");
            return;
        }

        try {
            List<Staff> doctors = StorageGlobal.StaffStorage().getData().stream()
                .filter(user -> user.getRole().name().equals("Doctor"))
                .collect(Collectors.toList());

            viewAvailableDoctors(doctors);

            if (doctors.isEmpty()) {
                System.out.println("No doctors available for appointment scheduling.");
                return;
            }

            String doctorID = getInputWithRetry(
                "Enter the Doctor ID you want to reschedule an appointment with: ",
                input -> doctors.stream().anyMatch(doctor -> doctor.getHospitalID().equals(input)),
                "Invalid Doctor ID. Please enter a valid Doctor ID."
            );

            String date = this.getInputWithRetry("Enter the date of the appointment (yyyy-MM-dd):", (input) -> {
                return input.matches("\\d{4}-\\d{2}-\\d{2}") && LocalDate.parse(input, DATE_FORMATTER).isAfter(LocalDate.now());
            }, "Invalid format. Please enter in [YYYY-MM-DD] format and a future date.");

            Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            List<String> slots = doctorScheduleController.getDoctorSlots(doctorID);
            // for the slots check the current doctor appointment and see if the slot is available
            List<Appointment> doctorAppointments = StorageGlobal.AppointmentStorage().getData().stream()
                .filter(appointment -> appointment.getDoctorID().equals(doctorID))
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

            current_appointment.setAppointmentStatus("PENDING");
            current_appointment.setDoctorID(doctorID);
            current_appointment.setAppointmentDate(appointmentDate);
            current_appointment.setAppointmentTime(time);

            StorageGlobal.AppointmentStorage().updateAppointment(current_appointment);
            System.out.println("Appointment rescheduled successfully.");
        } catch (ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        }
    }

    // Method for Patient to view available appointment slots
    public void viewAvailableAppointmentSlots(Patient patient) {
        List<Staff> doctors = StorageGlobal.StaffStorage().getData().stream()
            .filter(user -> user.getRole().name().equals("Doctor"))
            .collect(Collectors.toList());

        viewAvailableDoctors(doctors);

        if (doctors.isEmpty()) {
            System.out.println("No doctors available for appointment scheduling.");
            return;
        }

        String doctorID = getInputWithRetry(
            "Enter the Doctor ID you want to schedule an appointment with: ",
            input -> doctors.stream().anyMatch(doctor -> doctor.getHospitalID().equals(input)),
            "Invalid Doctor ID. Please enter a valid Doctor ID."
        );

        String date = this.getInputWithRetry("Enter the date of the appointment (yyyy-MM-dd):", (input) -> {
            return input.matches("\\d{4}-\\d{2}-\\d{2}") && LocalDate.parse(input, DATE_FORMATTER).isAfter(LocalDate.now());
        }, "Invalid format. Please enter in [YYYY-MM-DD] format and a future date.");

        List<String> slots = doctorScheduleController.getDoctorSlots(doctorID);
        // for the slots check the current doctor appointment and see if the slot is available
        List<Appointment> doctorAppointments = StorageGlobal.AppointmentStorage().getData().stream()
            .filter(appointment -> appointment.getDoctorID().equals(doctorID))
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
    }

    // Method for Patient to view the available Doctors from StaffStorage
    public void viewAvailableDoctors(List<Staff> doctors) {
        System.out.println("Available Doctors");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s\n", "Index.", "Doctor ID", "Doctor Name", "Gender", "Age");
        System.out.println("-----------------------------------------------------------------------");

        final int[] index = {1};
        doctors.forEach(doctor -> {
            System.out.printf("%-10d %-15s %-15s %-15s %-15s\n", index[0], doctor.getHospitalID(), doctor.getName(), doctor.getGender(), doctor.getAge());
            index[0]++;
        });

        System.out.println("-----------------------------------------------------------------------");
    }

    // Method for Doctor to view pending appointments
    public void respondToAppointment(Doctor doctor) {
        List<Appointment> appointments = doctor.getAppointments().stream()
            .filter(appointment -> appointment.getDoctorID().equals(doctor.getHospitalID()) && appointment.getAppointmentStatus().equals("PENDING"))
            .collect(Collectors.toList());
        
        int index = appointments.size();
        if (index == 0) {
            System.out.println("No pending appointments.");
        } else {
            displayAppointments(appointments, "Pending Appointments");

            // allow doctor to select an index and respond to the appointment with that index
            System.out.println("Enter the index of the appointment you want to respond to: ");
            int appointmentIndex = Integer.parseInt(System.console().readLine());
            if (appointmentIndex < 1 || appointmentIndex > index) {
                System.out.println("Invalid index.");
                return;
            }
            Appointment appointment = appointments.stream()
                .filter(a -> a.getDoctorID().equals(doctor.getHospitalID()) && a.getAppointmentStatus().equals("PENDING"))
                .skip(appointmentIndex - 1)
                .findFirst()
                .orElse(null);

            if (appointment != null) {
                // prompt doctor to accept or reject the appointment
                System.out.println("Do you want to accept or reject the appointment?");
                System.out.println("0. Accept");
                System.out.println("1. Reject");
                System.out.println("2. Back");
                int response = Integer.parseInt(System.console().readLine());
                switch (response) {
                    case 0 -> {
                        appointment.setAppointmentStatus("CONFIRMED");
                        StorageGlobal.AppointmentStorage().updateAppointment(appointment);
                        System.out.println("Appointment confirmed.");
                    }
                    case 1 -> {
                        appointment.setAppointmentStatus("REJECTED");
                        StorageGlobal.AppointmentStorage().updateAppointment(appointment);
                        System.out.println("Appointment rejected.");
                    }
                    case 2 -> System.out.println("");
                    default -> System.out.println("Invalid response.");
                }

            }
        }
    }

    // Method to display appointments
    public void displayAppointments(List<Appointment> appointments, String title) {
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

    // Method to update docter schedule
    public void updateDoctorSchedule(Doctor doctor) {
        // print doctor schedule
        if(doctor.getSchedule() != null) {
            System.out.println("Current Doctor Schedule");
            System.out.println("-----------------------------------------------------------------------");
            doctor.getSchedule().display();
        }else{
            System.out.println("Doctor schedule not setup.");
        }

        // prompt doctor to input starttime and endtime on HH:mm format
        String startTime = this.getInputWithRetry("Enter the start time of the doctor schedule (HH:mm):", (input) -> {
            return input.matches("\\d{2}:\\d{2}");
        }, "Invalid format. Please enter in [HH:mm] format.");

        String endTime = this.getInputWithRetry("Enter the end time of the doctor schedule (HH:mm):", (input) -> {
            return input.matches("\\d{2}:\\d{2}");
        }, "Invalid format. Please enter in [HH:mm] format.");

        DoctorSchedule schedule = new DoctorSchedule(doctor.getHospitalID(), startTime, endTime);

        StorageGlobal.DoctorScheduleStorage().editDoctorSchedule(schedule);
        doctor.setSchedule(schedule);
        System.out.println("Doctor schedule updated successfully.");
    }


    public void recordAppointmentOutcome(){
        // get all appointments
        List<Appointment> appointments = StorageGlobal.AppointmentStorage().getData();
        // filter appointments with status confirmed
        List<Appointment> confirmedAppointments = appointments.stream()
            .filter(appointment -> appointment.getAppointmentStatus().equals("CONFIRMED"))
            .collect(Collectors.toList());

        displayAppointments(confirmedAppointments, "Select the appointment to record appointment outcome");
        if(confirmedAppointments.isEmpty()){
            System.out.println("No confirmed appointments.");
            return;
        }

        // select the index of the appointment
        String appointmentIndexStr = getInputWithRetry(
            "Enter the index of the appointment you want to record outcome: ",
            input -> {
            try {
                int index = Integer.parseInt(input);
                return index >= 1 && index <= confirmedAppointments.size();
            } catch (NumberFormatException e) {
                return false;
            }
            },
            "Invalid index. Please enter a valid index."
        );
        int appointmentIndex = Integer.parseInt(appointmentIndexStr);

        Appointment appointment = confirmedAppointments.get(appointmentIndex - 1);
       // ask user to input service type and notes
        String serviceType = getInputWithRetry("Enter the service type:", (input) -> {
            return !input.isEmpty();
        }, "Service type cannot be empty.");

        String notes = getInputWithRetry("Enter the Consulation notes:", (input) -> {
            return !input.isEmpty();
        }, "Notes cannot be empty.");


        // doctor to view medication 
        prescriptionController.addPrescription(appointment.getAppointmentID());

        // update the appointment status to completed
        appointment.setAppointmentStatus("COMPLETED");
        appointment.setServiceType(serviceType);
        appointment.setNotes(notes);

        StorageGlobal.AppointmentStorage().updateAppointment(appointment);

        System.out.println("Appointment outcome recorded successfully.");

    }

    public void viewPastAppointments(List<Appointment> appointments){
        List<Appointment> _appointments = appointments.stream()
        .filter((appointment) -> {
            return appointment.getAppointmentStatus().equals("COMPLETED");
        }).collect(Collectors.toList());

        displayAppointments(_appointments, "Passed Appointments");
    }

    // display appointments outcome record
    public void displayAppointmentOutcomeRecords(List<Appointment> appointments, String title) {
        System.out.println(title);
        System.out.println("------------------------------------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s %-15s \n", "Index.", "Patient ID", "Date", "Time", "Status", "Service Type", "Notes", "Medicine Name", "Quantity", "Status");
        System.out.println("------------------------------------------------------------------------------------------------");

        final int[] index = {1};
        appointments.forEach(appointment -> {
            List<Prescription> prescriptions = StorageGlobal.PrescriptionStorage().getData().stream()
                .filter(prescription -> prescription.getAppointmentID().equals(appointment.getAppointmentID()))
                .collect(Collectors.toList());

            prescriptions.forEach(prescription -> {
                System.out.printf("%-10d %-15s %-15s %-15s %-15s %-15s %-15s %-15s \n",
                    index[0],
                    appointment.getPatientID(),
                    DATE_FORMATTER.format(appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()),
                    appointment.getAppointmentTime(),
                    appointment.getAppointmentStatus(),
                    appointment.getServiceType(),
                    appointment.getNotes(),
                    prescription.getMedicationName(),
                    prescription.getQuantity(),
                    prescription.getStatus());
            });

            if (prescriptions.isEmpty()) {
                System.out.printf("%-10d %-15s %-15s %-15s %-15s %-15s %-15s %-15s \n",
                    index[0],
                    appointment.getPatientID(),
                    DATE_FORMATTER.format(appointment.getAppointmentDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate()),
                    appointment.getAppointmentTime(),
                    appointment.getAppointmentStatus(),
                    appointment.getServiceType(),
                    appointment.getNotes(),
                    "N/A",
                    0,
                    "N/A");
            }  
            index[0]++;
        });

        System.out.println("------------------------------------------------------------------------------------------------");
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
