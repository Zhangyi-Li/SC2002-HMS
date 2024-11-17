package model.user;

import enums.UserRole;

public class Patient extends User {

    public Patient( String hospitalID, String name, String password, String email, String gender) {
        super(hospitalID, name, password, email, UserRole.Patient, gender);
    }
}
