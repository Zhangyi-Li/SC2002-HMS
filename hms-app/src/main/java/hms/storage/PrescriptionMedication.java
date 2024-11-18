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

public class PrescriptionMedication extends Medication {

    private int prescriptionID;
    private String medicationName;
    private int quantity;

    // Constructor
    public PrescriptionMedication(int prescriptionID, String medicationName, int quantity) {
        this.prescriptionID = prescriptionID;
        this.medicationName = medicationName;
        this.quantity = quantity;
    }
    public int getPrescriptionID() {return prescriptionID;}
    public void setPrescriptionID(int prescriptionID) {this.prescriptionID = prescriptionID;}
  
    public String getMedicationName() {return medicationName;}
    public void setMedicationName(String medicationName) {this.medicationName = medicationName;}
  
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
