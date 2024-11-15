package services;

import java.util.List;
import model.user.User;
import storage.UserData;

public class AuthService {
    //load users from csv file and loop through them to authenticate
    public User authenticate(String id, String password) {
        UserData userData = new UserData();
        List<User> users = userData.getData();
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