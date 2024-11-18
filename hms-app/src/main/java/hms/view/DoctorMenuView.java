package view;

import controller.DoctorMenuController;
import java.util.Scanner;

public class DoctorMenuView {

    private final DoctorMenuController controller;

    public DoctorMenuView(DoctorMenuController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        int choice = -1;

        try (Scanner scanner = new Scanner(System.in)) {
            while (choice != 8) {
                System.out.println("Doctor Menu:");
                System.out.println("1. View Patient Medical Records");
                System.out.println("2. Update Patient Medical Records");
                System.out.println("3. View Personal Schedule");
                System.out.println("4. Set Availability for Appointments");
                System.out.println("5. Accept or Decline Appointment Requests");
                System.out.println("6. View Upcoming Appointments");
                System.out.println("7. Record Appointment Outcome");
                System.out.println("8. Logout");
                System.out.print("Enter your choice (1-8): ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    handleChoice(choice);
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 8.");
                    scanner.next(); // Clear invalid input
                }
                System.out.println();

            }
        }
        controller.logout();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> controller.viewPatientRecords();
            case 2 -> controller.updatePatientRecords();
            case 3 -> controller.viewPersonalSchedule();
            case 4 -> controller.setAvailability();
            case 5 -> controller.manageAppointments();
            case 6 -> controller.viewUpcomingAppointments();
            case 7 -> controller.recordOutcome();
            case 8 -> System.out.println("");
            default -> System.out.println("Invalid choice. Please select a number between 1 and 8.");
        }
    }
}
