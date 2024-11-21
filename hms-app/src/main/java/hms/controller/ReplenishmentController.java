package controller;

import enums.ReplenishmentStatus;
import java.util.List;
import java.util.stream.Collectors;
import model.Replenishment;
import storage.StorageGlobal;

public class ReplenishmentController {

    // Method to view replenishment requests
    public void viewReplenishmentRequests() {
        System.out.println("Viewing Replenishment Requests...");
        List<Replenishment> replenishments = StorageGlobal.ReplenishmentStorage().getData().stream()
            .filter(replenishment -> ReplenishmentStatus.PENDING.equals(replenishment.getStatus()))
            .collect(Collectors.toList());
        displayReplenishmentRequests(replenishments, "Pending Replenishment Requests");
    }

    // Method to display replenishment requests
    public void displayReplenishmentRequests(List<Replenishment> replenishments, String title) {
        System.out.println(title);
        System.out.printf("%-10s %-20s %-20s %-10s %-10s %-15s%n", "Index","ID", "Medicine Name", "Date", "Stock", "Status");
        System.out.println("---------------------------------------------------------------");
        int index = 1;
        for (Replenishment replenishment : replenishments) {
            System.out.printf("%-20s %-20s %-10s %-10d %-15s%n",
                    index++,
                    replenishment.getID(),
                    replenishment.getMedicineName(),
                    replenishment.getDate(),
                    replenishment.getStock(),
                    replenishment.getStatus());
        }
    }

    // Method to update replenishment status
    public void updateReplenishmentStatus() {
        List<Replenishment> replenishments = StorageGlobal.ReplenishmentStorage().getData().stream()
            .filter(replenishment -> ReplenishmentStatus.PENDING.equals(replenishment.getStatus()))
            .collect(Collectors.toList());
        displayReplenishmentRequests(replenishments, "Replenishment Requests Pending Approval");

        if (replenishments.isEmpty()) {
            System.out.println("No replenishment requests pending approval!");
            return;
        }

        while(true){
            System.out.println("");
            System.out.println("Update Replenishment Status");
            System.out.println("Select an option:");
            System.out.println("0. Approve Replenishment");
            System.out.println("1. Back");
            String choice = getInputWithRetry("Enter choice: ",
                    input -> input.matches("[0-1]"),
                    "Invalid choice! Please try again.");
            if (choice.equals("0")) {
                Replenishment replenishment = replenishments.get(Integer.parseInt(getInputWithRetry("Enter Replenishment Index: ",
                        input -> input.matches("\\d+") && Integer.parseInt(input) >= 0 && Integer.parseInt(input) < replenishments.size(),
                        "Invalid Replenishment Index! Please try again.")));

                replenishment.setStatus(ReplenishmentStatus.APPROVED);

                StorageGlobal.ReplenishmentStorage().addReplenishment(replenishment);

                MedicationController.updateMedicineStock(replenishment.getMedicineName(),
                        MedicationController.fetchMedicationByName(replenishment.getMedicineName()).getStock() + replenishment.getStock());

            } else {
                return;
            }
        }
    }

     // Method to get input with retry
     private String getInputWithRetry(String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
        String input;
        do {
            System.out.println(prompt);
            input = System.console().readLine();
            if (!validator.test(input)) {
                System.out.println(errorMessage);
            }
        } while (!validator.test(input));
        return input;
    }
    
}
