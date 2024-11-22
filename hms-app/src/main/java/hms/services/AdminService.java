package services;

import enums.UserRole;
import java.util.List;
import model.user.Staff;
import storage.StorageGlobal;

public class AdminService {

    public AdminService() {
    }

    // Method to display all hospital staff
    public void displayStaff() {
        List<Staff> staffList = StorageGlobal.StaffStorage().getData();
        System.out.println("-----------------------------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", "Hospital ID", "Name", "Gender", "Age", "Role");
        System.out.println("-----------------------------------------------------------------------");
        staffList.forEach(staff -> {
            System.out.printf("%-15s %-15s %-15s %-15s %-15s\n",
                    staff.getHospitalID(),
                    staff.getName(),
                    staff.getGender(),
                    staff.getAge(),
                    staff.getRole().name());
        });
        System.out.println("-----------------------------------------------------------------------");
    }

    // Method to add a new hospital staff
    public void addStaff(String hospitalID, String name, UserRole role, String gender, int age) {
        Staff newStaff = new Staff(hospitalID, name, "password", role, gender, age);
        StorageGlobal.StaffStorage().saveStaff(newStaff);
        System.out.println("Staff added successfully.");
    }

    // Method to update an existing hospital staff
    public void updateStaff(String hospitalID, String name, String gender, int age, UserRole role) {
        Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(hospitalID);
        if (staff != null) {
            staff.setName(name);
            staff.setGender(gender);
            staff.setRole(role);
            staff.setAge(age);
            StorageGlobal.StaffStorage().updateStaff(staff);
            System.out.println("Staff updated successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }

    // Method to remove a hospital staff
    public void removeStaff(String hospitalID) {
        Staff staff = StorageGlobal.StaffStorage().fetchUserByHospitalId(hospitalID);
        if (staff != null) {
            StorageGlobal.StaffStorage().removeStaff(staff);
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }
}