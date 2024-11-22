package storage;

import enums.UserRole;
import interfaces.IStorage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.user.Staff;

public class StaffStorage implements IStorage<Staff> {
    
    private final String STAFF_FILE_PATH = "hms-app/src/main/resources/data/Staff_List.csv";
    private final List<Staff> staffs = new ArrayList<>();

    @Override
    public void importData() {
        String absolutePath = Paths.get(STAFF_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            staffs.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 5) {
                    String hospitalID = parts[0].trim();
                    String name = parts[1].trim();
                    UserRole role = UserRole.valueOf(parts[2].trim());
                    String gender = parts[3].trim();
                    int age = Integer.parseInt(parts[4].trim());
                    String password = parts[5].trim();

                    Staff staff = new Staff(hospitalID, name, password, role, gender, age);
                    staffs.add(staff);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading patient: " + e.getMessage());
        }
    }

    @Override
    public List<Staff> getData() {
        return staffs;
    }

    // Method to check for duplicate records
    public boolean isDuplicateRecord(String name, int age) {
        // Check if a staff with the same name and gender already exists
        return staffs.stream().anyMatch(staff ->
            staff.getName().equalsIgnoreCase(name) &&
            staff.getAge() == age
        );
    }

    // Method to generate a unique hospital ID
    public String generateHospitalId(UserRole role) {
        String prefix;
        switch (role) {
            case Administrator -> prefix = "A";
            case Doctor -> prefix = "D";
            case Pharmacist -> prefix = "P";
            default -> {
                System.out.println("Error There is no such staff role: "+role);
                return null;
            }
        }

        // loop through the staffs list taht start with prefix and get the incremental id that wasnt used
        int id = 1;
        while (true) {
            String generatedId = prefix + String.format("%03d", id); // Format ID as P1XXX
            boolean exists = staffs.stream()
                    .anyMatch(staff -> staff.getHospitalID().equals(generatedId));
            if (!exists) {
                return generatedId; // Return ID if it's not already used
            }
            id++;
        }
     
    }

    // Method to save a new staff
    public void saveStaff(Staff staff) {
        staffs.add(staff); // Add staff to in-memory list
        saveToFile(); // Persist changes to the CSV file
        System.out.println("Staff saved successfully!");
    }

    // Method to update staff's password
    public void updateStaffPassword(String hospitalId, String newPassword) {
        staffs.stream()
                .filter(staff -> staff.getHospitalID().equals(hospitalId))
                .findFirst()
                .ifPresent(staff -> staff.setPassword(newPassword)); // Update the staff's password
        saveToFile(); // Persist changes to the CSV file
    }

    // Method to update an existing staff
    public void updateStaff(Staff staff) {
        Staff existingStaff = staffs.stream()
                .filter(s -> s.getHospitalID().equals(staff.getHospitalID()))
                .findFirst()
                .orElse(null); // Return null if no match is found

        if (existingStaff != null) {
            existingStaff.setName(staff.getName());
            existingStaff.setRole(staff.getRole());
            existingStaff.setGender(staff.getGender());
            existingStaff.setAge(staff.getAge());
            saveToFile(); // Persist changes to the CSV file
            System.out.println("Staff updated successfully!");
        } else {
            System.out.println("Staff not found.");
        }
    }

    // Method to remove a staff
    public void removeStaff(Staff staff) {
        staffs.remove(staff); // Remove staff from in-memory list
        saveToFile(); // Persist changes to the CSV file
        System.out.println("Staff removed successfully!");
    }

    // Helper method to persist staffs to the CSV file
    private void saveToFile() {
        String absolutePath = Paths.get(STAFF_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("Staff ID,Name,Role,Gender,Age,Password");
            bw.newLine();

            // Write each staff to the file
            for (Staff staff : staffs) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s",
                    staff.getHospitalID(),
                    staff.getName(),
                    staff.getRole().name(),
                    staff.getGender(),
                    staff.getAge(),
                    staff.getPassword()));
                        
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Method to fetch a staff by hospital ID
    public Staff fetchUserByHospitalId(String hospitalId) {
        return staffs.stream()
                .filter(staff -> staff.getHospitalID().equals(hospitalId))
                .findFirst()
                .orElse(null); // Return null if no match is found
    }
}