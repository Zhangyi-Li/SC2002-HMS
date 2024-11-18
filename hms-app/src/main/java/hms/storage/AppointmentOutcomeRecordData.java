package storage;

import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.appointment.AppointmentOutcomeRecord;

public class AppointmentOutcomeRecordData implements IDataService<AppointmentOutcomeRecord>{
    private List<AppointmentOutcomeRecord> appointmentOutcomeRecords;
    private static final String APPOINTMENT_OUTCOME_RECORDS_FILE_PATH = "hms-app/src/main/resources/data/appointmentOutcomeRecords.csv";

    public AppointmentOutcomeRecordData() {
        this.appointmentOutcomeRecords = new ArrayList<>();
    }

    @Override
    public List<AppointmentOutcomeRecord> getData() {
        return appointmentOutcomeRecords;
    }

    @Override
    public void importData() {
        String absolutePath = Paths.get(APPOINTMENT_OUTCOME_RECORDS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentOutcomeRecordID = fields[0];
                String appointmentID = fields[1];
                String serviceType = fields[2];
                List<String> medicationIDs = Arrays.asList(fields[3].split(";"));
                String notes = fields[4];

                AppointmentOutcomeRecord record = new AppointmentOutcomeRecord(
                        appointmentOutcomeRecordID, appointmentID, serviceType, medicationIDs, notes
                );
                appointmentOutcomeRecords.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}