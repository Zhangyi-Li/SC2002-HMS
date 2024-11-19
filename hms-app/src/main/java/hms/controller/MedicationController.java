package controller;

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
import storage.Medication;

public class MedicationController {
    // private static final String FILE_PATH = "medications.csv";
    private static final String MEDICATIONS_FILE_PATH = "hms-app/src/main/resources/data/medications.csv";
    private final String FILE_PATH = Paths.get(MEDICATIONS_FILE_PATH).toAbsolutePath().toString();
    private List<Medication> medications;

    public MedicationController() {
        medications = new ArrayList<>();
        loadMedications();
    }

    // Load medications from CSV file
    private void loadMedications() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String id = data[0];
                    String name = data[1];
                    String company = data[2];
                    double cost = Double.parseDouble(data[3]);
                    String description = data[4];
                    medications.add(new Medication(id, name, company, cost, description));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading medications: " + e.getMessage());
        }
    }

    // Save medications to CSV file
    private void saveMedications() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Medication med : medications) {
                writer.write(med.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medications: " + e.getMessage());
        }
    }

    // Add new medication
    public void addMedication(Medication medication) {
        medications.add(medication);
        saveMedications();
        System.out.println("Medication added successfully.");
    }

    // Update existing medication
    public void updateMedication(String medicationID, String name, String company, double cost, String description) {
        for (Medication med : medications) {
            if (med.getMedicationID().equals(medicationID)) {
                med.setMedicationName(name);
                med.setMedicationCompany(company);
                med.setMedicationCost(cost);
                med.setMedicationDescription(description);
                saveMedications();
                System.out.println("Medication updated successfully.");
                return;
            }
        }
        System.out.println("Medication not found.");
    }

    // Display all medications
    public void displayMedications() {
        System.out.println("List of Medications:");
        for (Medication med : medications) {
            System.out.println("ID: " + med.getMedicationID() +
                               ", Name: " + med.getMedicationName() +
                               ", Company: " + med.getMedicationCompany() +
                               ", Cost: " + med.getMedicationCost() +
                               ", Description: " + med.getMedicationDescription());
        }
    }
}
