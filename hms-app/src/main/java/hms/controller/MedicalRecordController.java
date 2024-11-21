package controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.MedicalRecord;
import model.user.Patient;
import storage.StorageGlobal;

public class MedicalRecordController {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    
    public MedicalRecordController() {}

    // Method for Doctor to view the available Patients
    public void viewPatient(List<Patient> patients) {
        System.out.println("Available Patients");
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-10s %-15s %-15s %-15s %-15s %-15s %-15s\n", "Index", "Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Contact Info");
        System.out.println("-----------------------------------------------------------------------");

        final int[] index = {1};
        patients.forEach(patient -> {
            System.out.printf("%-10d %-15s %-15s %-15s %-15s %-15s %-15s\n", index[0], patient.getHospitalID(), patient.getName(), patient.getDateOfBirth(), patient.getGender(), patient.getBloodType(), patient.getContactInfo());
            index[0]++;
        });

        System.out.println("-----------------------------------------------------------------------");
    }

    // Method for doctor to input patientId and view patient medical records
    public void viewPatientRecords() {
        viewPatient(StorageGlobal.PatientStorage().getData());
        System.out.println();
        System.out.println("Enter Patient ID: ");
        String patientID = System.console().readLine();
        
        List <MedicalRecord> medicalRecords = fetchMedicalRecordByPatientID(patientID);
        if (!medicalRecords.isEmpty()) {
            displayMedicalRecords(medicalRecords);
        } else {
            System.out.println("Medical Record Not Found!");
        }
    }

    // Method to view patient medical records
    public void viewPatientRecords(String patientID) {
        List <MedicalRecord> medicalRecords = fetchMedicalRecordByPatientID(patientID);
        if (!medicalRecords.isEmpty()) {
            displayMedicalRecords(medicalRecords);
        } else {
            System.out.println("Medical Record Not Found!");
        }
    }

    // Method to save a new medical record
    public void addMedicalRecord(String doctorID) {
        try{
            String patientID = getInputWithRetry("Enter Patient ID:", (input) -> {
                return StorageGlobal.PatientStorage().fetchUserByHospitalId(input) != null;
            }, "Patient not found. Please enter a valid Patient ID.");

            String date = getInputWithRetry("Enter the date of record (yyyy-MM-dd):", (input) -> {
                return input.matches("\\d{4}-\\d{2}-\\d{2}");
            }, "Invalid format. Please enter in [YYYY-MM-DD] format.");
            Date recordDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

            System.out.println("Enter Diagnose: ");
            String diagnose = System.console().readLine();
            System.out.println("Enter Treatment: ");
            String treatment = System.console().readLine();
            MedicalRecord medicalRecord = new MedicalRecord(patientID, doctorID, recordDate, diagnose, treatment);
            StorageGlobal.MedicalRecordStorage().getData().add(medicalRecord); // Add medical record to in-memory list
            StorageGlobal.MedicalRecordStorage().saveToFile(); // Persist changes to the CSV file
            System.out.println("Medical record saved successfully!");
        } catch (ParseException e) {
            System.out.println("Error parsing medical record: " + e.getMessage());
        }
    }

    // Method to fetch lists of medical record by patient ID
    public static List <MedicalRecord> fetchMedicalRecordByPatientID(String patientID) {
        return StorageGlobal.MedicalRecordStorage().getData().stream()
                .filter(medicalRecord -> medicalRecord.getPatientID().equalsIgnoreCase(patientID))
                .collect(Collectors.toList());
    }

    // Method to update medical record's treatment
    public static void updateMedicalRecordTreatment(String patientID, String newTreatment) {
        StorageGlobal.MedicalRecordStorage().getData().stream()
                .filter(medicalRecord -> medicalRecord.getPatientID().equalsIgnoreCase(patientID))
                .findFirst()
                .ifPresent(medicalRecord -> medicalRecord.setTreatment(newTreatment)); // Update the medical record's treatment
        StorageGlobal.MedicalRecordStorage().saveToFile(); // Persist changes to the CSV file
    }

    // Method to update medical record's diagnose
    public static void updateMedicalRecordDiagnose(String patientID, String newDiagnose) {
        StorageGlobal.MedicalRecordStorage().getData().stream()
                .filter(medicalRecord -> medicalRecord.getPatientID().equalsIgnoreCase(patientID))
                .findFirst()
                .ifPresent(medicalRecord -> medicalRecord.setDiagnose(newDiagnose)); // Update the medical record's diagnose
        StorageGlobal.MedicalRecordStorage().saveToFile(); // Persist changes to the CSV file
    }

    // Method to display List of Medical Records
    public static void displayMedicalRecords(List<MedicalRecord> medicalRecords) {
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s\n", "Patient ID", "Date", "Diagnose", "Treatment");
        System.out.println("-----------------------------------------------------------------------");
        medicalRecords.forEach(medicalRecord -> {
            System.out.printf("%-15s %-15s %-15s %-15s\n",
                    medicalRecord.getPatientID(),
                    medicalRecord.getDate(),
                    medicalRecord.getDiagnose(),
                    medicalRecord.getTreatment());
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
