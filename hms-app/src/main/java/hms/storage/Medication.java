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

public class Medication {
    private String medicationID;
    private String medicationName;
    private String medicationCompany;
    private double medicationCost;
    private String medicationDescription;

    public Medication(String medicationID, String medicationName, String medicationCompany, double medicationCost, String medicationDescription) {
        this.medicationID = medicationID;
        this.medicationName = medicationName;
        this.medicationCompany = medicationCompany;
        this.medicationCost = medicationCost;
        this.medicationDescription = medicationDescription;
    }

    public String getMedicationID() { return medicationID; }
    public String getMedicationName() { return medicationName; }
    public String getMedicationCompany() { return medicationCompany; }
    public double getMedicationCost() { return medicationCost; }
    public String getMedicationDescription() { return medicationDescription; }

    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }
    public void setMedicationCompany(String medicationCompany) { this.medicationCompany = medicationCompany; }
    public void setMedicationCost(double medicationCost) { this.medicationCost = medicationCost; }
    public void setMedicationDescription(String medicationDescription) { this.medicationDescription = medicationDescription; }

    @Override
    public String toString() {
        return medicationID + "," + medicationName + "," + medicationCompany + "," + medicationCost + "," + medicationDescription;
    }
}
