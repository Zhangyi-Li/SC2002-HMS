package services;

import java.util.List;
import model.user.User;

public class AuthService {
    public User authenticate(List<User> users,String id, String password) {
        for (User user : users) {
            if (user.getHospitalID().equals(id)) {
                if (user.getPassword().equals(password)) {
                    return user;
                }
            }
        }
        return null;
    }

}