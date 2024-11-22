package storage;

import interfaces.IStorage;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Medication;

public class MedicationStorage implements IStorage<Medication> {
    private final String MEDICATION_FILE_PATH = "hms-app/src/main/resources/data/Medicine_List.csv";
    private final List<Medication> medicineList = new ArrayList<>();

    public void importData() {
        String absolutePath = Paths.get(MEDICATION_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            medicineList.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 2) {
                    String medicineName = parts[0].trim();
                    int stock = Integer.parseInt(parts[1].trim());
                    int lowStockLevelAlert = Integer.parseInt(parts[2].trim());

                    Medication medication = new Medication(medicineName, stock, lowStockLevelAlert);
                    medicineList.add(medication);
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Error parsing stock or lowStockLevelAlert: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading medicine: " + e.getMessage());
        }
    }

    @Override
    public List<Medication> getData() {
        return medicineList;
    }

    // Helper method to persist medicinelist to the CSV file
    public void saveToFile() {
        String absolutePath = Paths.get(MEDICATION_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            // Write header to the CSV file
            bw.write("Medicine Name,Stock,Low Stock Level Alert");
            bw.newLine();

            // Write each medication to the file
            for (Medication medication : medicineList) {
                bw.write(String.format("%s,%s,%s",
                        medication.getMedicineName(),
                        medication.getStock(),
                        medication.getLowStockLevelAlert())
                 );
                        
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving medications: " + e.getMessage());
        }
    }
}
