package model.user;

import enums.UserRole;

public class Doctor extends Staff {

    public Doctor(String hospitalID, String name, String password, String gender, int age) {
        super(hospitalID, name, password, UserRole.Doctor, gender, age);
    }

}
