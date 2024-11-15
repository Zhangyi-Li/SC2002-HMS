package view;

import controller.PatientMenuController;
import java.util.Scanner;

public class PatientMenuView {

    private final PatientMenuController controller;

    public PatientMenuView(PatientMenuController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        try (Scanner scanner = new Scanner(System.in)) {
            int choice = -1;

            while (choice != 10) {
                System.out.println("Patient Menu:");
                System.out.println("1. View Medical Record");
                System.out.println("2. Update Personal Information");
                System.out.println("3. View Available Appointment Slots");
                System.out.println("4. Schedule an Appointment");
                System.out.println("5. Reschedule an Appointment");
                System.out.println("6. Cancel an Appointment");
                System.out.println("7. View Scheduled Appointments");
                System.out.println("8. View Past Appointment Outcome Records");
                System.out.println("9. Logout");
                System.out.print("Enter your choice (1-9): ");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    handleChoice(choice);
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    scanner.next(); // Clear invalid input
                }
                System.out.println();

            }
        }
        controller.logout();
    }

    private void handleChoice(int choice) {
        switch (choice) {
            case 1 -> controller.viewMedicalRecord();
            case 2 -> controller.updatePersonalInfo();
            case 3 -> controller.viewAvailableAppointmentSlots();
            case 4 -> controller.scheduleAppointment();
            case 5 -> controller.rescheduleAppointment();
            case 6 -> controller.cancelAppointment();
            case 7 -> controller.viewScheduledAppointments();
            case 8 -> controller.viewPastAppointmentRecords();
            default -> System.out.println("Invalid choice. Please select a number between 1 and 9.");
        }
    }

}
