package storage;

import enums.UserRole;
import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.user.User;

public class UserStorage implements IDataService<User> {
    private static final String USERS_FILE_PATH = "hms-app/src/main/resources/data/users.csv";
    private static List<User> users = new ArrayList<>();

    @Override
    public void importData() {
        String absolutePath = Paths.get(USERS_FILE_PATH).toAbsolutePath().toString();
        try {
            BufferedReader br = new BufferedReader(new FileReader(absolutePath));
            users.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 5) {
                    String hospitalID = parts[0].trim();
                    String name = parts[1].trim();
                    String password = parts[2].trim();
                    String email = parts[3].trim();
                    UserRole role = UserRole.valueOf(parts[4].trim());
                    String gender = parts[5].trim();
                    User user;                    
                    if(role==UserRole.Patient){
                        Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(parts[6].trim());
                        String bloodType = parts[7].trim();
                        user = new User(hospitalID, name, password, email, role, gender, dob, bloodType);
                    }else{
                        user = new User(hospitalID, name, password, email, role, gender);
                    }
                    users.add(user);
                }
            }

        } catch (java.text.ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    @Override
    public List<User> getData() {
        return users;
    }

    // Method to check for duplicate records
    public static boolean isDuplicateRecord(String name, String dateOfBirth, String gender) {
        // Check if a user with the same name and gender already exists
        return users.stream().anyMatch(user ->
                user.getName().equalsIgnoreCase(name) &&
                        user.getGender().equalsIgnoreCase(gender) &&
                        user.getHospitalID().startsWith(dateOfBirth)
        );
    }

    // Method to generate a unique hospital ID
    public static String generateHospitalId() {
        int id = 1; // Start with ID 1
        while (true) {
            String generatedId = String.format("H%03d", id); // Format ID as HXXX
            boolean exists = users.stream()
                    .anyMatch(user -> user.getHospitalID().equals(generatedId));
            if (!exists) {
                return generatedId; // Return ID if it's not already used
            }
            id++;
        }
    }

    // Method to save a new user
    public static void saveUser(User user) {
        users.add(user); // Add user to in-memory list
        saveToFile(); // Persist changes to the CSV file
        System.out.println("User saved successfully!");
    }

    // Method to update a user's password
    public static void updateUserPassword(String hospitalId, String newPassword) {
        users.stream()
                .filter(user -> user.getHospitalID().equals(hospitalId))
                .findFirst()
                .ifPresent(user -> user.setPassword(newPassword)); // Update the user's password
        saveToFile(); // Persist changes to the CSV file
    }

    // Helper method to persist users to the CSV file
    private static void saveToFile() {
        String absolutePath = Paths.get(USERS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("HospitalID,Name,Password,Email,Role,Gender,DOB,BloodType");
            bw.newLine();

            // Write each user to the file
            for (User user : users) {
                bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s",
                        user.getHospitalID(),
                        user.getName(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getRole().name(),
                        user.getGender(),
                        user.getDob() != null ? new SimpleDateFormat("yyyy-MM-dd").format(user.getDob()) : "null",
                        user.getBloodType()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    // Method to fetch a user by hospital ID
    public static User fetchUserByHospitalId(String hospitalId) {
        return users.stream()
                .filter(user -> user.getHospitalID().equals(hospitalId))
                .findFirst()
                .orElse(null); // Return null if no match is found
    }
}
