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
    public void updatePrescriptionStatus() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Prescription Status: " + prescriptionStatus);
    }
}
