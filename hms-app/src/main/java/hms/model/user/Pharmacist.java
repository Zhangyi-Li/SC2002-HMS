package model.user;

import enums.UserRole;

public class Pharmacist extends Staff {

    public Pharmacist( String hospitalID, String name, String password, String gender, int age) {
        super(hospitalID, name, password, UserRole.Pharmacist, gender, age);
    }
    
    public Pharmacist(Staff staff){
        super(staff.getHospitalID(), staff.getName(), staff.getPassword(), UserRole.Doctor, staff.getGender(), staff.getAge());
    }
}