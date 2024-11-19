package storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import model.appointment.DoctorSchedule;

public class DoctorScheduleStorage {
    private static List<DoctorSchedule> doctorSchedules;
    private static final String DOCTER_SCHEDULE_FILE_PATH = "hms-app/src/main/resources/data/doctorSchedule.csv";

    public DoctorScheduleStorage() {
        doctorSchedules = new ArrayList<>();
    }

    public static List<DoctorSchedule> getData() {
        return doctorSchedules;
    }

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
            e.printStackTrace();
        }
    }

    // // list of slot in 1hr interval base on the doctors schedule starttime and endtime
    // public List<String> getDoctorSlots(String doctorID) {
    //     List<String> slots = new ArrayList<>();
    //     for (DoctorSchedule slot : doctorSchedules) {
    //         if (slot.getDoctorID().equals(doctorID)) {
    //             String startTime = slot.getStartTime();
    //             String endTime = slot.getEndTime();
    //             while (startTime.compareTo(endTime) < 0) {
    //                 slots.add(startTime);
    //                 startTime = addMinutes(startTime, 60);
    //             }
    //         }
    //     }
    //     return slots;
    // }

    // // add Minutes to time in HH:mm format
    // private String addMinutes(String time, int minutes) {
    //     String[] parts = time.split(":");
    //     int hours = Integer.parseInt(parts[0]);
    //     int mins = Integer.parseInt(parts[1]);
    //     mins += minutes;
    //     if (mins >= 60) {
    //         hours++;
    //         mins = mins - 60;
    //     }
    //     return String.format("%02d:%02d", hours, mins);
    // }
}