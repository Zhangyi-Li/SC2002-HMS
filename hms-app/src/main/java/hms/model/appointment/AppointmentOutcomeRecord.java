package model.appointment;

import java.util.List;

public class AppointmentOutcomeRecord {
    private String appointmentID;
    private String serviceType;
    private List<String> medicationIDs;
    private String notes;
    public AppointmentOutcomeRecord() {
    }
    // Constructor
    public AppointmentOutcomeRecord(String appointmentID, String serviceType, List<String> medicationIDs, String notes) {
        this.appointmentID = appointmentID;
        this.serviceType = serviceType;
        this.medicationIDs = medicationIDs;
        this.notes = notes;
    }

    // Getters and Setters

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
