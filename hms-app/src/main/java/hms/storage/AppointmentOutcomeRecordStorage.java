package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.appointment.AppointmentOutcomeRecord;

public class AppointmentOutcomeRecordStorage{
    private static List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private static final String APPOINTMENT_OUTCOME_RECORDS_FILE_PATH = "hms-app/src/main/resources/data/appointmentOutcomeRecords.csv";

    public AppointmentOutcomeRecordStorage() {
        appointmentOutcomeRecords = new ArrayList<>();
    }

    public static List<AppointmentOutcomeRecord> getData() {
        return appointmentOutcomeRecords;
    }

    public void importData() {
        String absolutePath = Paths.get(APPOINTMENT_OUTCOME_RECORDS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String serviceType = fields[1];
                List<String> medicationIDs = Arrays.asList(fields[2].split(";"));
                String notes = fields[3];

                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                         appointmentID, serviceType, medicationIDs, notes
                );
                appointmentOutcomeRecords.add(record);
            }
        } catch (IOException e) {
            System.err.println("Error importing data: " + e.getMessage());
        }
    }

    public static void editAppointmentOutcomeRecord(AppointmentOutcomeRecord appointmentOutcomeRecord) {
        appointmentOutcomeRecords.removeIf(record -> record.getAppointmentID().equals(appointmentOutcomeRecord.getAppointmentID()));
        appointmentOutcomeRecords.add(appointmentOutcomeRecord);
        String newAppointmentOutcomeRecord = String.join(",", appointmentOutcomeRecord.getAppointmentID(), appointmentOutcomeRecord.getServiceType(),
                String.join(";", appointmentOutcomeRecord.getMedicationIDs()), appointmentOutcomeRecord.getNotes());
        try {
            String absolutePath = Paths.get(APPOINTMENT_OUTCOME_RECORDS_FILE_PATH).toAbsolutePath().toString();
            java.nio.file.Files.write(java.nio.file.Paths.get(absolutePath), (newAppointmentOutcomeRecord + "\n").getBytes(),
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error editing data: " + e.getMessage());
        }
    }
    
}
