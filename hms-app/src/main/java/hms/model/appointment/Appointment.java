package model.appointment;

import java.util.Date;
import java.util.UUID;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private Date appointmentDate;
    private String appointmentTime;
    private String appointmentStatus; // PENDING, CONFIRMED, COMPLETED, CANCELLED
    private String appointmentOutcomeRecordID;

    // Constructor
    public Appointment(String appointmentID, String patientID, String doctorID, Date appointmentDate, String appointmentTime, String appointmentStatus, String appointmentOutcomeRecordID) {
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
    }

    public Appointment(String patientID, String doctorID, Date appointmentDate, String appointmentTime, String appointmentStatus, String appointmentOutcomeRecordID) {
        this.appointmentID = generateID();
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
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

    public String getAppointmentOutcomeRecordID() {
        return appointmentOutcomeRecordID;
    }

    public void setAppointmentOutcomeRecordID(String appointmentOutcomeRecordID) {
        this.appointmentOutcomeRecordID = appointmentOutcomeRecordID;
    }

    // Method to update appointment status
    public void updateStatus(String status) {
        this.appointmentStatus = status;
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
