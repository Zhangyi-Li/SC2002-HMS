package storage;

import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.appointment.Appointment;

public class AppointmentData implements IDataService<Appointment> {
    private List<Appointment> appointments;
    private static final String APPOINTMENTS_FILE_PATH = "hms-app/src/main/resources/data/appointments.csv";
    private final String absolutePath = Paths.get(APPOINTMENTS_FILE_PATH).toAbsolutePath().toString();

    public AppointmentData() {
        this.appointments = new ArrayList<>();
    }

    @Override
    public List<Appointment> getData() {
        return appointments;
    }

    @Override
    public void importData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 7) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }
                String appointmentID = fields[0];
                String patientID = fields[1];
                String doctorID = fields[2];
                Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(fields[3]);
                String appointmentTime = fields[4];
                String appointmentStatus = fields[5];
                String appointmentOutcomeRecordID = fields[6];

                Appointment appointment = new Appointment(
                        appointmentID, patientID, doctorID, appointmentDate, appointmentTime, appointmentStatus, appointmentOutcomeRecordID
                );
                appointments.add(appointment);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);

        // Append new appointment to file
        String newAppointment = String.join(",", appointment.getAppointmentID(), appointment.getPatientID(), appointment.getDoctorID(),
            new SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate()), appointment.getAppointmentTime(),
            appointment.getAppointmentStatus(), appointment.getAppointmentOutcomeRecordID());
        try {
            java.nio.file.Files.write(Paths.get(absolutePath), (newAppointment + System.lineSeparator()).getBytes(), java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAppointment(Appointment appointment){
        // Update appointment in list
        for (int i = 0; i < appointments.size(); i++) {
            if (appointments.get(i).getAppointmentID().equals(appointment.getAppointmentID())) {
                appointments.set(i, appointment);
                break;
            }
        }

        // Update appointment in file
        try {
            List<String> fileContent = new ArrayList<>();
            fileContent.add("AppointmentID,PatientID,DoctorID,AppointmentDate,AppointmentTime,AppointmentStatus,AppointmentOutcomeRecordID");
            for (Appointment a : appointments) {
                fileContent.add(String.join(",", a.getAppointmentID(), a.getPatientID(), a.getDoctorID(),
                    new SimpleDateFormat("yyyy-MM-dd").format(a.getAppointmentDate()), a.getAppointmentTime(),
                    a.getAppointmentStatus(), a.getAppointmentOutcomeRecordID()));
            }
            java.nio.file.Files.write(Paths.get(absolutePath), String.join(System.lineSeparator(), fileContent).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
