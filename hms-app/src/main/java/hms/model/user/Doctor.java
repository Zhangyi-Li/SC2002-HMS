package model.user;

import enums.UserRole;

public class Doctor extends User {

    public Doctor( String hospitalID, String name, String password, String email) {
        super(hospitalID, name, password, email, UserRole.Doctor);
    }
}
