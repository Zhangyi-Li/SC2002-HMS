package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.user.Patient;

public class PatientStorage{
    private static final String PATIENT_FILE_PATH = "hms-app/src/main/resources/data/Patient_List.csv";
    private static final List<Patient> patients = new ArrayList<>();

    public void importData() {
        String absolutePath = Paths.get(PATIENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            patients.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 5) {
                    String hospitalID = parts[0].trim();
                    String name = parts[1].trim();
                    Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2].trim());
                    String gender = parts[3].trim();
                    String bloodType = parts[4].trim();
                    String contactInfo = parts[5].trim();
                    String password = parts[6].trim();

                    Patient patient = new Patient(hospitalID, name, password, gender, dob, bloodType, contactInfo);
                    patients.add(patient);
                }
            }

        } catch (java.text.ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading patient: " + e.getMessage());
        }
    }

    public static List<Patient> getData() {
        return patients;
    }

    // Method to check for duplicate records
    public static boolean isDuplicateRecord(String name, String gender) {
        // Check if a patient with the same name and gender already exists
        return patients.stream().anyMatch(patient ->
            patient.getName().equalsIgnoreCase(name) &&
            patient.getGender().equalsIgnoreCase(gender)
        );
    }

    // Method to generate a unique hospital ID
    public static String generateHospitalId() {
        int id = 1; // Start with ID 1
        while (true) {
            String generatedId = String.format("P1%03d", id); // Format ID as P1XXX
            boolean exists = patients.stream()
                    .anyMatch(patient -> patient.getHospitalID().equals(generatedId));
            if (!exists) {
                return generatedId; // Return ID if it's not already used
            }
            id++;
        }
    }

    // Method to save or update a new patient
    public static void savePatient(Patient patient) {
        if (isDuplicateRecord(patient.getName(), patient.getGender())) {
            patients.replaceAll(operator -> operator.getName().equalsIgnoreCase(patient.getName()) && operator.getGender().equalsIgnoreCase(patient.getGender()) ? patient : operator);
        } else {
            patients.add(patient);
        }

        patients.add(patient); // Add patient to in-memory list
        saveToFile(); // Persist changes to the CSV file
        System.out.println("Patient saved successfully!");
    }

    // Method to update patient's password
    public static void updatePatientPassword(String hospitalId, String newPassword) {
        patients.stream()
                .filter(patient -> patient.getHospitalID().equals(hospitalId))
                .findFirst()
                .ifPresent(patient -> patient.setPassword(newPassword)); // Update the patient's password
        saveToFile(); // Persist changes to the CSV file
    }

    // Helper method to persist patients to the CSV file
    private static void saveToFile() {
        String absolutePath = Paths.get(PATIENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Password");
            bw.newLine();

            // Write each patient to the file
            for (Patient patient : patients) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s",
                        patient.getHospitalID(),
                        patient.getName(),
                        patient.getDateOfBirth() != null ? new SimpleDateFormat("yyyy-MM-dd").format(patient.getDateOfBirth()) : "null",
                        patient.getGender(),
                        patient.getBloodType(),
                        patient.getContactInfo(),
                        patient.getPassword()));
                        
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Method to fetch a patient by hospital ID
    public static Patient fetchUserByHospitalId(String hospitalId) {
        return patients.stream()
                .filter(Patient -> Patient.getHospitalID().equals(hospitalId))
                .findFirst()
                .orElse(null); // Return null if no match is found
    }
}
