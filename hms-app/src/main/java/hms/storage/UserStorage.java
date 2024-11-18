package storage;

import enums.UserRole;
import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.user.User;

public class UserStorage implements IDataService<User> {
    private static final String USERS_FILE_PATH = "hms-app/src/main/resources/data/users.csv";
    private static List<User> users = new ArrayList<>();

    @Override
    public void importData() {
        String absolutePath = Paths.get(USERS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            users.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String hospitalID = parts[0].trim();
                    String name = parts[1].trim();
                    String password = parts[2].trim();
                    String email = parts[3].trim();
                    UserRole role = UserRole.valueOf(parts[4].trim());
                    String gender = parts[5].trim();
                    User user = new User(hospitalID, name, password, email, role, gender);
                    users.add(user);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    @Override
    public List<User> getData() {
        return users;
    }
}
