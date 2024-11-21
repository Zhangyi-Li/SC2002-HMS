package model.appointment;

import java.util.Date;
import java.util.UUID;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private Date appointmentDate;
    private String appointmentTime;
    private String appointmentStatus; // PENDING, CONFIRMED, COMPLETED, CANCELLED, REJECTED
    private String serviceType = "N/A";
    private String notes ="N/A";

    // Constructor
    public Appointment(String appointmentID, String patientID, String doctorID, Date appointmentDate, String appointmentTime, String appointmentStatus, String serviceType, String notes) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.serviceType = serviceType;
        this.notes = notes;
    }

    public Appointment(String appointmentID, String patientID, String doctorID, Date appointmentDate, String appointmentTime, String appointmentStatus) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
    }

    public Appointment(String patientID, String doctorID, Date appointmentDate, String appointmentTime, String appointmentStatus) {
        this.appointmentID = generateID();
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
    }

    // ID Generator
    private static String generateID() {
        return UUID.randomUUID().toString();
    }

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void display(){
        System.out.println("Appointment ID: " + this.appointmentID);
        System.out.println("Patient ID: " + this.patientID);
        System.out.println("Date: " + this.appointmentDate);
        System.out.println("Time: " + this.appointmentTime);
        System.out.println("Status: " + this.appointmentStatus);
        System.out.println();
    }

}
