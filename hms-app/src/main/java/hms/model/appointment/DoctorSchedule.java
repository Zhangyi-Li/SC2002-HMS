package model.appointment;

public class DoctorSchedule {
    private String doctorID;
    private String startTime;
    private String endTime;

    // Constructor
    public DoctorSchedule(String doctorID, String startTime, String endTime) {
        this.doctorID = doctorID;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void display(){
        System.out.println("Doctor ID: " + doctorID + ", Start Time: " + startTime + ", End Time: " + endTime);
    }
}