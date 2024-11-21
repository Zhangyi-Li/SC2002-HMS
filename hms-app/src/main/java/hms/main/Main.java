package main;

import controller.AdministratorMenuController;
import controller.AuthController;
import controller.DoctorMenuController;
import controller.PatientMenuController;
import controller.PharmacistMenuController;
import model.user.User;
import storage.AppointmentStorage;
import storage.DoctorScheduleStorage;
import storage.StorageGlobal;
import view.AdministratorMenuView;
import view.DoctorMenuView;
import view.PatientMenuView;
import view.PharmacistMenuView;

public class Main {
    
	public static void main(String[] args) {
		try {
			AuthController authController = new AuthController();

			while(true){
			
			User authenticatedUser = authController.login();

			if(authenticatedUser != null){
				switch (authenticatedUser.getRole().toString()) {
					case "Patient" -> {
						AppointmentStorage appointmentStorage = new AppointmentStorage();
						appointmentStorage.importData();
						DoctorScheduleStorage doctorScheduleStorage = new DoctorScheduleStorage();
						doctorScheduleStorage.importData();
						PatientMenuController controller = new PatientMenuController(authenticatedUser);
						PatientMenuView view = new PatientMenuView(controller);
						view.showMenu();
					}
					case "Doctor" -> {
						AppointmentStorage appointmentStorage = new AppointmentStorage();
						appointmentStorage.importData();
						DoctorScheduleStorage doctorScheduleStorage = new DoctorScheduleStorage();
						doctorScheduleStorage.importData();
						DoctorMenuController controller = new DoctorMenuController(authenticatedUser);
						DoctorMenuView view = new DoctorMenuView(controller);
						view.showMenu();
					}
					case "Pharmacist" -> {
						StorageGlobal.MedicationStorage().importData();
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
		}
			

        } catch (Exception e) {
			// Print error message
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please restart the hms application.");
		}
	}
}
