package model.user;

import enums.UserRole;
import java.util.Date;

public class Patient extends User {
    private Date dateOfBirth;
    private String bloodType;
    private String contactInfo;

    public Patient(String hospitalID, String name, String password, String gender, Date dateOfBirth, String bloodType, String contactInfo) {
        super(hospitalID, name, password, UserRole.Patient, gender);
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
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

}
