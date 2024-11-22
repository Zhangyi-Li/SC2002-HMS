package storage;

import enums.ReplenishmentStatus;
import interfaces.IStorage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.Replenishment;

public class ReplenishmentStorage implements IStorage<Replenishment> {
    
    private final String REPLENISHMENT_FILE_PATH = "hms-app/src/main/resources/data/replenishment.csv";
    private final List<Replenishment> replenishments = new ArrayList<>();

    @Override
    public void importData() {
        String absolutePath = Paths.get(REPLENISHMENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            replenishments.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 3) {
                    String ID = parts[0].trim();
                    String medicineName = parts[1].trim();
                    int stock = Integer.parseInt(parts[2].trim());
                    ReplenishmentStatus status = ReplenishmentStatus.valueOf(parts[3].trim());

                    Replenishment replenishment = new Replenishment(ID, medicineName, stock, status);
                    replenishments.add(replenishment);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading patient: " + e.getMessage());
        }
    }

    @Override
    public List<Replenishment> getData() {
        return replenishments;
    }

    // Method to check for duplicate records
    public boolean isDuplicateRecord(String ID) {
        // Check if a replenishment with the same ID
        return replenishments.stream().anyMatch(replenishment ->
            replenishment.getID().equals(ID)
        );
    }

    // Method to generate a unique ID
    public String generateID() {
        return String.format("R%03d", replenishments.size() + 1);
    }

    // Method to add or update a replenishment
    public void addReplenishment(Replenishment replenishment) {
        if (isDuplicateRecord(replenishment.getID())) {
            replenishments.set(replenishments.indexOf(replenishment), replenishment);
        } else {
            replenishments.add(replenishment);
        }
        saveToFile();
    }

    public void saveToFile(){
        String absolutePath = Paths.get(REPLENISHMENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            bw.write("ID,Medicine Name,Stock,Status\n");
            for (Replenishment replenishment : replenishments) {
                bw.write(replenishment.getID() + "," + replenishment.getMedicineName()  + "," + replenishment.getStock() + "," + replenishment.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving replenishment: " + e.getMessage());
        }
    }

}
