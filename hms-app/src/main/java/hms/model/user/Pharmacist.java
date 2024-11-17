package model.user;

import enums.UserRole;

public class Pharmacist extends User {

    public Pharmacist( String hospitalID, String name, String password, String email, String gender) {
        super(hospitalID, name, password, email, UserRole.Pharmacist, gender);
    }
    
}