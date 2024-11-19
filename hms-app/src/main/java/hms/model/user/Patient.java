package model.user;

import enums.UserRole;
import java.util.Date;

public class Patient extends User {
    
    public Patient(String hospitalID, String name, String password, String email, String gender, Date dateOfBirth, String bloodType) {
        super(hospitalID, name, password, email, UserRole.Patient, gender, dateOfBirth, bloodType);
    }

}
