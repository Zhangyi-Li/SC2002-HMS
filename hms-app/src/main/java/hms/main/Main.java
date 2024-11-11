package main;

import controller.AuthController;
import storage.ReadCsv;

public class Main {
    
	public static void main(String[] args) {
		try {

            ReadCsv readCsv = new ReadCsv();
            readCsv.loadUsers();
			AuthController.Login();

        } catch (Exception e) {
			// Print error message
			System.out.println("Error: " + e.getMessage());
			System.out.println("Please restart the hms application.");
		}
	}
}
