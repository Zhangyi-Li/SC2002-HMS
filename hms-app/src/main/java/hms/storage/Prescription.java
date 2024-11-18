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
    private Status prescriptionStatus;
    public Prescription(int prescriptionID, Status prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }
    public int getPrescriptionID() {
        return prescriptionID;
    }
    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }
    public Status getPrescriptionStatus() {
        return prescriptionStatus;
    }
    public void setPrescriptionStatus(Status prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }
    public void updatePrescriptionStatus(Status newStatus) {
        this.prescriptionStatus = newStatus;
    }
    public void storePrescriptionDetails(int prescriptionID, Status prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }
}
