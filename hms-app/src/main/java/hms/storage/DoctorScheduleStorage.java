package storage;

import interfaces.IStorage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.appointment.DoctorSchedule;

public class DoctorScheduleStorage implements IStorage<DoctorSchedule> {
    private List<DoctorSchedule> doctorSchedules;
    private final String DOCTER_SCHEDULE_FILE_PATH = "hms-app/src/main/resources/data/doctorSchedule.csv";

    public DoctorScheduleStorage() {
        doctorSchedules = new ArrayList<>();
    }

    @Override
    public List<DoctorSchedule> getData() {
        return doctorSchedules;
    }

    @Override
    public void importData() {
        String absolutePath = Paths.get(DOCTER_SCHEDULE_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String doctorID = fields[0];
                String startTime = fields[1];
                String endTime = fields[2];

                DoctorSchedule slot = new DoctorSchedule(doctorID, startTime, endTime);
                doctorSchedules.add(slot);
            }
        } catch (IOException e) {
            System.err.println("Error importing data: " + e.getMessage());
        }
    }

    public void editDoctorSchedule(DoctorSchedule doctorSchedule) {

        doctorSchedules.removeIf(slot -> slot.getDoctorID().equals(doctorSchedule.getDoctorID()));
        doctorSchedules.add(doctorSchedule);
        String newDoctorSchedule = String.join(",", doctorSchedule.getDoctorID(), doctorSchedule.getStartTime(),
                doctorSchedule.getEndTime());
        try {
            String absolutePath = Paths.get(DOCTER_SCHEDULE_FILE_PATH).toAbsolutePath().toString();
            java.nio.file.Files.write(java.nio.file.Paths.get(absolutePath), (newDoctorSchedule + "\n").getBytes(),
                    java.nio.file.StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Error editing data: " + e.getMessage());
        }
    }
    
}