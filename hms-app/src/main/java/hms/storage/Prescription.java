package storage;

import enums.UserRole;
import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.user.User;

public class Prescription {
    private int prescriptionID;
    private String prescriptionStatus;
    public Prescription(int prescriptionID, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }

    public int getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    public void setPrescriptionStatus(String prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    public void updatePrescriptionStatus(String newStatus) {
        this.prescriptionStatus = newStatus;
    }

    public void storePrescriptionDetails(int prescriptionID, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }

    public static void readPrescriptionsFromCSV(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length >= 2) {
                    int id = Integer.parseInt(values[0].trim());
                    String status = values[1].trim();
                    Prescription prescription = new Prescription(id, status);
                    System.out.println("Read Prescription ID: " + prescription.getPrescriptionID());
                    System.out.println("Read Prescription Status: " + prescription.getPrescriptionStatus());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Invalid number.");
        }
    }
}
