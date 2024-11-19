package model.user;

import enums.UserRole;

public class Staff extends User {
    private int age;
    
    public Staff(){}

    public Staff( String hospitalID, String name, String password, UserRole role, String gender, int age) {
        super(hospitalID, name, password, role, gender);
        this.age = age;
    }

    // Getter method for age
    public int getAge() {
        return age;
    }
    
    // Setter for age
    public void setAge(int age) {
        this.age = age;
    }
    
}
