package model.user; // Package declaration

import enums.UserRole; // Importing UserRole enum

// User class definition
public class User {
    // Private member variables
    private String name;
    private String hospitalID;
    private String password;
    private UserRole role;
    private String gender;
    private String email;

    // Constructor to initialize User object
    public User(String hospitalID, String name, String password, String email, UserRole role, String gender) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.gender = gender;
    }

    public User(User user){
        this.hospitalID = user.getHospitalID();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.gender = user.getGender();
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

}
