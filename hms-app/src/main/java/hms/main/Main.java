package main;

import controller.AdministratorMenuController;
import controller.AuthController;
import controller.DoctorMenuController;
import controller.PatientMenuController;
import controller.PharmacistMenuController;
import model.user.User;
import storage.UserData;
import view.AdministratorMenuView;
import view.DoctorMenuView;
import view.PatientMenuView;
import view.PharmacistMenuView;

public class Main {
    
	public static void main(String[] args) {
		try {

            UserData userData = new UserData();
            userData.importData();
			AuthController authController = new AuthController();
			User authenticatedUser = authController.login();

			if(authenticatedUser != null){				
				switch (authenticatedUser.getRole().toString()) {
					case "Patient" -> {
						PatientMenuController controller = new PatientMenuController();
						PatientMenuView view = new PatientMenuView(controller);
						view.showMenu();
					}
					case "Doctor" -> {
						DoctorMenuController controller = new DoctorMenuController();
						DoctorMenuView view = new DoctorMenuView(controller);
						view.showMenu();
					}
					case "Pharmacist" -> {
						PharmacistMenuController controller = new PharmacistMenuController();
						PharmacistMenuView view = new PharmacistMenuView(controller);
						view.showMenu();
					}
					case "Administrator" -> {
						AdministratorMenuController controller = new AdministratorMenuController();
						AdministratorMenuView view = new AdministratorMenuView(controller);
						view.showMenu();
					}
					default -> System.out.println("Unknown role.");
				}

			}
			

        } catch (Exception e) {
			// Print error message
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please restart the hms application.");
		}
	}
}
