package main;

import controller.AdministratorMenuController;
import controller.AuthController;
import controller.DoctorMenuController;
import controller.PatientMenuController;
import controller.PharmacistMenuController;
import model.user.User;
import storage.StorageGlobal;
import view.AdministratorMenuView;
import view.DoctorMenuView;
import view.LoginView;
import view.PatientMenuView;
import view.PharmacistMenuView;


public class Main {
    
	public static void main(String[] args) {
		AuthController authController = new AuthController();
		LoginView loginView = new LoginView();
		User authenticatedUser;
		try {
			while(true){
				authenticatedUser = null;

				int choice = loginView.showLoginMenu(); // Display the login menu and get user choice

				switch (choice) {
					case 0 -> {
						// Handle exit choice
						loginView.displayMessage("\nShutting down HMS...");
						return;
					}
					case 1 -> {
						// Handle login choice
						authenticatedUser = authController.login();
					}
					case 2 -> {
						// Call the registerNewPatient method from the registerNewPatient class
						authController.registerNewPatient();
					}
					case 3 -> {
						// Call the resetPassword method from the resetPassword class
						authController.startResetPassword();
					}
					default -> {
						// Handle invalid input
						loginView.displayMessage("Invalid input. Please enter a valid option (0-3)!");
					}
				}

				if(authenticatedUser != null){
					switch (authenticatedUser.getRole().toString()) {
						case "Patient" -> {
							PatientMenuController controller = new PatientMenuController(authenticatedUser);
							PatientMenuView view = new PatientMenuView(controller);
							view.showMenu();
						}
						case "Doctor" -> {
							DoctorMenuController controller = new DoctorMenuController(authenticatedUser);
							DoctorMenuView view = new DoctorMenuView(controller);
							view.showMenu();
						}
						case "Pharmacist" -> {
							StorageGlobal.MedicationStorage().importData();
							PharmacistMenuController controller = new PharmacistMenuController(authenticatedUser);
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
			}
			

        } catch (Exception e) {
			// Print error message
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please restart the hms application.");
		}
	}
}
