package model.user;

import enums.UserRole;

public class Administrator extends User {

    public Administrator( String hospitalID, String name, String password, String email) {
        super(hospitalID, name, password, email, UserRole.Administrator);
    }
    
}
