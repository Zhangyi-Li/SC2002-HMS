package controller;

public class UserController {
    // private static final String FILE_PATH = "users.csv";
    private List<User> userList;

    public UserController() {
        userList = new ArrayList<>();
        loadUser();
    }

    // Load user from CSV file
    private void loadUser() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String hospitalID = data[0];
                    String name = data[1];
                    String password = data[2];
                    String email = data[3];
                    String role = data[4];
                    String gender = data[5];
                    userList.add(new User(hospitalID, name, password, email, role, gender));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading user: " + e.getMessage());
        }
    }

    // Save user to CSV file
    private void saveUser() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : userList) {
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }

    // Add new user member
    public void addUser(User user) {
        userList.add(user);
        saveUser();
        System.out.println("User added successfully.");
    }

    // Update existing user member
    public void updateUser(String hospitalID, String name, String password, String email, String role, String gender) {
        for (User user : userList) {
            if (user.getHospitalID().equals(hospitalID)) {
                user.setName(name);
                user.setPassword(password);
                user.setEmail(email);
                user.setRole(role);
                user.setGender(gender);
                saveUser();
                System.out.println("User updated successfully.");
                return;
            }
        }
        System.out.println("User member not found.");
    }

    // Remove user member
    public void removeUser(String hospitalID) {
        Iterator<User> iterator = userList.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getHospitalID().equals(hospitalID)) {
                iterator.remove();
                saveUser();
                System.out.println("User member removed successfully.");
                return;
            }
        }
        System.out.println("User member not found.");
    }

    // Display user filtered by role or gender
    public void displayUser(String filterType, String filterValue) {
        System.out.println("Filtered List of User:");
        for (User user : userList) {
            if (filterType.equalsIgnoreCase("role") && user.getRole().equalsIgnoreCase(filterValue)) {
                System.out.println(user);
            } else if (filterType.equalsIgnoreCase("gender") && user.getGender().equalsIgnoreCase(filterValue)) {
                System.out.println(user);
            }
        }
    }

    // Display all user
    public void displayAllUser() {
        System.out.println("List of All User:");
        for (User user : userList) {
            System.out.println("ID: " + user.getHospitalID() +
                               ", Name: " + user.getName() +
                               ", Email: " + user.getEmail() +
                               ", Role: " + user.getRole() +
                               ", Gender: " + user.getGender());
        }
    }
}
