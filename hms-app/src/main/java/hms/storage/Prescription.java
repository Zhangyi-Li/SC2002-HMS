package storage;

import enums.Status;

public class Prescription {

    private int prescriptionID;
    private Status prescriptionStatus;
    public Prescription(int prescriptionID, Status prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }
    public int getPrescriptionID() {return prescriptionID;}
    public void setPrescriptionID(int prescriptionID) {this.prescriptionID = prescriptionID;}
    
    public Status getPrescriptionStatus() {return prescriptionStatus;}
    public void setPrescriptionStatus(Status prescriptionStatus) {this.prescriptionStatus = prescriptionStatus;}
    
    public void updatePrescriptionStatus(int prescriptionID, Status prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }
    
    @Override
    public String toString() {
        return prescriptionID + "," + prescriptionStatus;
    }
}
