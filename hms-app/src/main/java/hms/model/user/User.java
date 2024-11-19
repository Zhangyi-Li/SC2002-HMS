package model.user; // Package declaration

import enums.UserRole; // Importing UserRole enum
import java.util.Date; 

// User class definition
public class User {
    // Private member variables
    private String name;
    private String hospitalID;
    private String password;
    private UserRole role;
    private String gender;
    private String email;
    private Date dob;
    private String bloodType;


    // Constructor to initialize User object
    public User(String hospitalID, String name, String password, String email, UserRole role, String gender) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }

    public User(String hospitalID, String name, String password, String email, UserRole role, String gender, Date dob, String bloodType) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.gender = gender;
        this.dob = dob;
        this.bloodType = bloodType;
    }

    public User(User user){
        this.hospitalID = user.getHospitalID();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.dob = user.getDob();
        this.bloodType = user.getBloodType();
    }

    // Getter method for name
    public String getName() {
        return name;
    }

    // Getter method for hospitalID
    public String getHospitalID() {
        return hospitalID;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }
    
    // Getter method for role
    public UserRole getRole() {
        return role;
    }
    
    // Getter method for gender
    public String getGender() {
        return gender;
    }
    
    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Getter for Date of Birth
    public Date getDob() {
        return dob;
    }

    // Getter for Blood Type
    public String getBloodType() {
        return bloodType;
    }

    // Setter for Name
    public void setName(String name) {
        this.name = name;
    }

    // Setter for Password
    public void setPassword(String password) {
        this.password = password;
    }

    // Setter for Email
    public void setEmail(String email) {
        this.email = email;
    }

    // Setter for Role
    public void setRole(UserRole role) {
        this.role = role;
    }

    // Setter for Gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Setter for Date of Birth
    public void setDob(Date dob) {
        this.dob = dob;
    }

    // Setter for Blood Type
    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

}
