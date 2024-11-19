package model.appointment;

import java.util.List;
import java.util.UUID;

public class AppointmentOutcomeRecord {
    private String appointmentOutcomeRecordID;
    private String appointmentID;
    private String serviceType;
    private List<String> medicationIDs;
    private String notes;
    public AppointmentOutcomeRecord() {
    }
    // Constructor
    public AppointmentOutcomeRecord(String appointmentOutcomeRecordID, String appointmentID, String serviceType, List<String> medicationIDs, String notes) {
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
        this.appointmentID = appointmentID;
        this.serviceType = serviceType;
        this.medicationIDs = medicationIDs;
        this.notes = notes;
    }
    
    public AppointmentOutcomeRecord(String appointmentID, String serviceType, List<String> medicationIDs, String notes) {
        this.appointmentOutcomeRecordID = generateID();
        this.appointmentID = appointmentID;
        this.serviceType = serviceType;
        this.medicationIDs = medicationIDs;
        this.notes = notes;
    }

    // ID Generator
    private static String generateID() {
        return UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getAppointmentOutcomeRecordID() {
        return appointmentOutcomeRecordID;
    }

    public void setAppointmentOutcomeRecordID(String appointmentOutcomeRecordID) {
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<String> getMedicationIDs() {
        return medicationIDs;
    }

    public void setMedicationIDs(List<String> medicationIDs) {
        this.medicationIDs = medicationIDs;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
