package model;

import java.util.Date;

public class MedicalRecord {
    private String patientID;
    private String doctorID;
    private Date date;
    private String diagnose;
    private String treatment;

    public MedicalRecord(String patientID, String doctorID, Date date, String diagnose, String treatment) {
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.diagnose = diagnose;
        this.treatment = treatment;
    }

    public String getPatientID() {
        return patientID;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public Date getDate() {
        return date;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setPatientID(String newPatientID) {
        this.patientID = newPatientID;
    }

    public void setDoctorID(String newDoctorID) {
        this.doctorID = newDoctorID;
    }
    
    public void setDate(Date newDate) {
        this.date = newDate;
    }

    public void setDiagnose(String newDiagnose) {
        this.diagnose = newDiagnose;
    }

    public void setTreatment(String newTreatment) {
        this.treatment = newTreatment;
    }

}
