package storage;

import enums.UserRole;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.user.User;

public class ReadCsv {
    private static final String USERS_FILE_PATH = "hms-app0/src/main/resources/data/users.csv";
    private List<User> users = new ArrayList<>();

    public void loadUsers() {
        String absolutePath = Paths.get(USERS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String hospitalID = parts[0].trim();
                    String name = parts[1].trim();
                    String password = parts[2].trim();
                    String email = parts[3].trim();
                    UserRole role = UserRole.valueOf(parts[4].trim());
                    User user = new User(hospitalID, name, password, email, role);
                    users.add(user);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public List<User> getUsers() {
        if (users.isEmpty()) {
            loadUsers();
        }
        return users;
    }
}
