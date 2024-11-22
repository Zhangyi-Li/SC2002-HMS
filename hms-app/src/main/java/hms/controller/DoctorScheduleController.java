package controller;

import java.util.ArrayList;
import java.util.List;
import model.appointment.DoctorSchedule;
import storage.StorageGlobal;

public class DoctorScheduleController {
    
    // list of slot in 1hr interval base on the doctors schedule starttime and endtime
    public List<String> getDoctorSlots(String doctorID) {
        List<DoctorSchedule> doctorSchedules = StorageGlobal.DoctorScheduleStorage().getData();
        List<String> slots = new ArrayList<>();
        for (DoctorSchedule slot : doctorSchedules) {
            if (slot.getDoctorID().equals(doctorID)) {
                String startTime = slot.getStartTime();
                String endTime = slot.getEndTime();
                while (startTime.compareTo(endTime) < 0) {
                    slots.add(startTime);
                    startTime = addMinutes(startTime, 60);
                }
            }
        }
        return slots;
    }

    // add Minutes to time in HH:mm format
    private String addMinutes(String time, int minutes) {
        String[] parts = time.split(":");
        int hours = Integer.parseInt(parts[0]);
        int mins = Integer.parseInt(parts[1]);
        mins += minutes;
        if (mins >= 60) {
            hours++;
            mins = mins - 60;
        }
        return String.format("%02d:%02d", hours, mins);
    }
}
