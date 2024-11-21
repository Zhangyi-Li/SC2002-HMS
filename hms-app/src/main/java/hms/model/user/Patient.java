package model.user;

import enums.UserRole;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import storage.StorageGlobal;

public class Patient extends User {
    private Date dateOfBirth;
    private String bloodType;
    private String contactInfo;
    private List<Appointment> appointments;

    public Patient(String hospitalID, String name, String password, String gender, Date dateOfBirth, String bloodType, String contactInfo) {
        super(hospitalID, name, password, UserRole.Patient, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = StorageGlobal.AppointmentStorage().getData().stream()
        .filter(a -> a.getPatientID().equals(this.getHospitalID()))
        .collect(Collectors.toList());
    }

    // Getter method for dateOfBirth
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Setter for dateOfBirth
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Getter method for bloodType
    public String getBloodType() {
        return bloodType;
    }

    // Setter for bloodType
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    // Getter method for contactInfo
    public String getContactInfo() {
        return contactInfo;
    }

    // Setter for contactInfo
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Getter method for appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Setter for appointments
    public void setAppointment(List<Appointment>  appointments) {
        this.appointments = appointments.stream()
        .filter(a -> a.getPatientID().equals(this.getHospitalID()))
        .collect(Collectors.toList());
    }

    
}
