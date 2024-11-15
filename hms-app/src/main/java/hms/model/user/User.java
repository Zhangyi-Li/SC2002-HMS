package model.user; // Package declaration

import enums.UserRole; // Importing UserRole enum

// User class definition
public class User {
    // Private member variables
    private String name;
    private String hospitalID;
    private String password;
    private UserRole role;

    // Constructor to initialize User object
    public User(String hospitalID, String name, String password, String email, UserRole role) {
        this.hospitalID = hospitalID;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(User user){
        this.hospitalID = user.getHospitalID();
        this.name = user.getName();
        this.password = user.getPassword();
        this.role = user.getRole();
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
}
