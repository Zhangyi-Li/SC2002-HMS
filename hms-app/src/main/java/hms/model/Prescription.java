package model;

import enums.PrescriptionStatus;

public class Prescription {

    private String appointmentID;
    private String medicationName;
    private int quantity;
    private PrescriptionStatus status;
    
    public Prescription(String appointmentID, String medicationName, int quantity) {
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.status = PrescriptionStatus.PENDING;
    }
    
    public Prescription(String appointmentID, String medicationName, int quantity, PrescriptionStatus status) {
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.status = status;
    }
   
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setAppointmentID(String newAppointmentID) {
        this.appointmentID = newAppointmentID;
    }

    public void setMedicationName(String newMedicationName) {
        this.medicationName = newMedicationName;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    public void setStatus(PrescriptionStatus newStatus) {
        this.status = newStatus;
    }
}
