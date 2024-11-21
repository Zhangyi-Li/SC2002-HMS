package storage;

import enums.ReplenishmentStatus;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Replenishment;

public class ReplenishmentStorage {
    
    private static final String REPLENISHMENT_FILE_PATH = "hms-app/src/main/resources/data/replenishment.csv";
    private static final List<Replenishment> replenishments = new ArrayList<>();

    public void importData() {
        String absolutePath = Paths.get(REPLENISHMENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
            replenishments.clear();
            String line;
            br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 4) {
                    String ID = parts[0].trim();
                    String medicineName = parts[1].trim();
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(parts[2].trim());
                    int stock = Integer.parseInt(parts[3].trim());
                    ReplenishmentStatus status = ReplenishmentStatus.valueOf(parts[4].trim());

                    Replenishment replenishment = new Replenishment(ID, medicineName, date, stock, status);
                    replenishments.add(replenishment);
                }
            }
        } catch (java.text.ParseException e) {
            System.out.println("Error parsing date: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error loading patient: " + e.getMessage());
        }
    }

    public static List<Replenishment> getData() {
        return replenishments;
    }

    // Method to check for duplicate records
    public static boolean isDuplicateRecord(String medicineName, Date date) {
        // Check if a replenishment with the same medicine name and date already exists
        return replenishments.stream().anyMatch(replenishment ->
            replenishment.getMedicineName().equalsIgnoreCase(medicineName) &&
            replenishment.getDate().equals(date)
        );
    }

    // Method to generate a unique ID
    public static String generateID() {
        return String.format("R%03d", replenishments.size() + 1);
    }

    // Method to add or update a replenishment
    public void addReplenishment(Replenishment replenishment) {
        if (isDuplicateRecord(replenishment.getMedicineName(), replenishment.getDate())) {
            replenishments.set(replenishments.indexOf(replenishment), replenishment);
        } else {
            replenishments.add(replenishment);
        }
        saveToFile();
    }

    public void saveToFile(){
        String absolutePath = Paths.get(REPLENISHMENT_FILE_PATH).toAbsolutePath().toString();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(absolutePath))) {
            bw.write("ID,Medicine Name,Date,Stock,Status\n");
            for (Replenishment replenishment : replenishments) {
                bw.write(replenishment.getID() + "," + replenishment.getMedicineName() + "," + new SimpleDateFormat("yyyy-MM-dd").format(replenishment.getDate()) + "," + replenishment.getStock() + "," + replenishment.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving replenishment: " + e.getMessage());
        }
    }

}
