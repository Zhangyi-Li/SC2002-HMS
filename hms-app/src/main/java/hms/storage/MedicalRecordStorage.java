package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.MedicalRecord;

public class MedicalRecordStorage {
    private static final String MEDICAL_RECORD_FILE_PATH = "hms-app/src/main/resources/data/Medical_Record.csv";
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();
        
    public void importData() {
        String absolutePath = Paths.get(MEDICAL_RECORD_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            medicalRecords.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String patientID = parts[0].trim();
                    String doctorID = parts[1].trim();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2]);
                    String diagnose = parts[3].trim();
                    String treatment = parts[4].trim();

                    MedicalRecord medicalRecord = new MedicalRecord(patientID, doctorID, date, diagnose, treatment);
                    medicalRecords.add(medicalRecord);
                }
            }

        }catch (java.text.ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading medical record: " + e.getMessage());
        }
    }

    public List<MedicalRecord> getData() {
        return medicalRecords;
    }

    public MedicalRecord findMedicalRecord(MedicalRecord medicalRecord) {
        for (MedicalRecord record : medicalRecords) {
            if (record.getPatientID().equals(medicalRecord.getPatientID()) && record.getDate().equals(medicalRecord.getDate())) {
                return record;
            }
        }

        return null;
    }

    // add new medical record to the list or update existing one
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (findMedicalRecord(medicalRecord) != null) {
            medicalRecords.replaceAll(record -> record.getPatientID().equals(medicalRecord.getPatientID()) && record.getDate().equals(medicalRecord.getDate()) ? medicalRecord : record);
        } else {
            medicalRecords.add(medicalRecord);
        }
        saveToFile();
    }

    public void saveToFile(){
        String absolutePath = Paths.get(MEDICAL_RECORD_FILE_PATH).toAbsolutePath().toString();
        try (java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("Patient ID,Date,Diagnose,Treatment");
            bw.newLine();

            // Write each medical record to the file
            for (MedicalRecord medicalRecord : medicalRecords) {
                bw.write(String.format("%s,%s,%s,%s",
                        medicalRecord.getPatientID(),
                        new SimpleDateFormat("yyyy-MM-dd").format(medicalRecord.getDate()),
                        medicalRecord.getDiagnose(),
                        medicalRecord.getTreatment())
                );
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medical record: " + e.getMessage());
        }
    }

}
