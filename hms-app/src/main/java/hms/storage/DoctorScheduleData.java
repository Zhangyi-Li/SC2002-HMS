package storage;

import interfaces.IDataService;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.appointment.DoctorSchedule;

public class DoctorScheduleData implements IDataService<DoctorSchedule> {
    private List<DoctorSchedule> availableSlots;
    private static final String AVAILABLE_SLOTS_FILE_PATH = "hms-app/src/main/resources/data/doctorSchedule.csv";

    public DoctorScheduleData() {
        this.availableSlots = new ArrayList<>();
    }

    @Override
    public List<DoctorSchedule> getData() {
        return availableSlots;
    }

    @Override
    public void importData() {
        String absolutePath = Paths.get(AVAILABLE_SLOTS_FILE_PATH).toAbsolutePath().toString();
        try (BufferedReader reader = new BufferedReader(new FileReader(absolutePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                String doctorID = fields[0];
                String startTime = fields[1];
                String endTime = fields[2];

                DoctorSchedule slot = new DoctorSchedule(doctorID, startTime, endTime);
                availableSlots.add(slot);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}