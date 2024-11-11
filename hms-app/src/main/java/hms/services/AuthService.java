package services;

import java.util.List;
import model.user.User;
import storage.ReadCsv;

public class AuthService {
    //load users from csv file and loop through them to authenticate
    public User authenticate(String name, String password) {
        ReadCsv readCsv = new ReadCsv();
        List<User> users = readCsv.getUsers();
        for (User user : users) {
            if (user.getName().equals(name)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

}