package storage;

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

public class AppointmentStorage  {
    private static List<Appointment> appointments;
    private static final String APPOINTMENTS_FILE_PATH = "hms-app/src/main/resources/data/appointments.csv";
    private static final String absolutePath = Paths.get(APPOINTMENTS_FILE_PATH).toAbsolutePath().toString();

    public AppointmentStorage() {
        appointments = new ArrayList<>();
    }

    public static List<Appointment> getData() {
        return appointments;
    }

    public void importData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 8) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }
                String appointmentID = fields[0];
                String patientID = fields[1];
                String doctorID = fields[2];
                Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(fields[3]);
                String appointmentTime = fields[4];
                String appointmentStatus = fields[5];
                String serviceType = fields[6];
                String notes = fields[7];

                Appointment appointment = new Appointment(
                        appointmentID, patientID, doctorID, appointmentDate, appointmentTime, appointmentStatus, serviceType, notes
                );
                appointments.add(appointment);
            }
            } catch (IOException | ParseException e) {
                System.err.println("Error importing data: " + e.getMessage());
            }
        }
    
    public static void addAppointment(Appointment appointment) {
            appointments.add(appointment);
            saveToFile();
        }

    public static void updateAppointment(Appointment appointment) {
        // Update appointment in list
        appointments.replaceAll(a -> a.getAppointmentID().equals(appointment.getAppointmentID()) ? appointment : a);
        saveToFile();
    }

    public static void saveToFile() {
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("AppointmentID,PatientID,DoctorID,Appointment Date,Appointment Time,Appointment Status,Service Type,Consultation Notes");
            bw.newLine();

            // Write each appointment to the file
            for (Appointment appointment : appointments) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s",
                    appointment.getAppointmentID(), appointment.getPatientID(), appointment.getDoctorID(),
                    new SimpleDateFormat("yyyy-MM-dd").format(appointment.getAppointmentDate()), appointment.getAppointmentTime(),
                    appointment.getAppointmentStatus(), appointment.getServiceType(), appointment.getNotes()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving appointment: " + e.getMessage());
        }
    }

}
