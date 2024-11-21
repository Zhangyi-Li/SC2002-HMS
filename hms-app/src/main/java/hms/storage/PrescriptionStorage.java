package storage;

import enums.PrescriptionStatus;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Prescription;

public class PrescriptionStorage {
    private static List<Prescription> prescriptions;
    private static final String APPOINTMENT_OUTCOME_RECORDS_FILE_PATH = "hms-app/src/main/resources/data/prescription.csv";

    public PrescriptionStorage() {
        prescriptions = new ArrayList<>();
    }

    public static List<Prescription> getData() {
        return prescriptions;
    }

    public void importData() {
        String absolutePath = Paths.get(APPOINTMENT_OUTCOME_RECORDS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String medicineName = fields[1];
                int quantity = Integer.parseInt(fields[2]);
                PrescriptionStatus status = PrescriptionStatus.valueOf(fields[3]);

                Prescription prescription = new Prescription(appointmentID, medicineName, quantity, status);
                prescriptions.add(prescription);


            }
        } catch (IOException e) {
            System.err.println("Error importing data: " + e.getMessage());
        }
    }

    // find prescription by appointment id and medicine name
    public static Prescription findPresciption(Prescription prescription) {
        for (Prescription p : prescriptions) {
            if (p.getAppointmentID().equals(prescription.getAppointmentID()) && p.getMedicationName().equals(prescription.getMedicationName())) {
                return p;
            }
        }

        return null;
    }

    public static void addPrescription(Prescription prescription) {
        if(findPresciption(prescription) != null) {
            prescriptions.replaceAll(p -> p.getAppointmentID().equals(prescription.getAppointmentID()) &&  p.getMedicationName().equals(prescription.getMedicationName()) ? prescription : p);
        }else{
            prescriptions.add(prescription);
        }
        saveToFile();
    }

    public static void saveToFile(){
        String absolutePath = Paths.get(APPOINTMENT_OUTCOME_RECORDS_FILE_PATH).toAbsolutePath().toString();
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("Appointment ID,Medicine Name,Quantity,Status");
            bw.newLine();

            // Write each prescription to the file
            for (Prescription prescription : prescriptions) {
                bw.write(String.format("%s,%s,%s,%s",
                        prescription.getAppointmentID(),
                        prescription.getMedicationName(),
                        prescription.getQuantity(),
                        prescription.getStatus())
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving prescription: " + e.getMessage());
        }
    }
}
