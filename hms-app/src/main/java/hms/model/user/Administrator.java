package model.user;

import enums.UserRole;

public class Administrator extends Staff {

    public Administrator( String hospitalID, String name, String password, String gender, int age) {
        super(hospitalID, name, password, UserRole.Administrator, gender, age);
    }
    
}
