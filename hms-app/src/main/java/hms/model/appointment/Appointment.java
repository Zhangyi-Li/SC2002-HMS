package model.appointment;

import java.util.Date;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private Date appointmentDate;
    private String appointmentTime;
    private String appointmentStatus;
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

    // Getters and Setters
    public String getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(String appointmentID) {
        this.appointmentID = appointmentID;
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

}
