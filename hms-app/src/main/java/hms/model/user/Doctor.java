package model.user;

import enums.UserRole;
import java.util.List;
import java.util.stream.Collectors;
import model.appointment.Appointment;
import model.appointment.DoctorSchedule;
import storage.StorageGlobal;

public class Doctor extends Staff {
    private List<Appointment> appointments;
    private DoctorSchedule schedule;

    public Doctor(Staff staff){
        super(staff.getHospitalID(), staff.getName(), staff.getPassword(), UserRole.Doctor, staff.getGender(), staff.getAge());
        this.appointments = StorageGlobal.AppointmentStorage().getData().stream()
        .filter(a -> a.getDoctorID().equals(this.getHospitalID()))
        .collect(Collectors.toList());
        this.schedule = StorageGlobal.DoctorScheduleStorage().getData().stream()
        .filter(s -> s.getDoctorID().equals(this.getHospitalID()))
        .findFirst()
        .orElse(null);
    }

    public Doctor(String hospitalID, String name, String password, String gender, int age) {
        super(hospitalID, name, password, UserRole.Doctor, gender, age);
        this.appointments = StorageGlobal.AppointmentStorage().getData().stream()
        .filter(a -> a.getDoctorID().equals(this.getHospitalID()))
        .collect(Collectors.toList());
        this.schedule = StorageGlobal.DoctorScheduleStorage().getData().stream()
        .filter(s -> s.getDoctorID().equals(this.getHospitalID()))
        .findFirst()
        .orElse(null);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments.stream()
        .filter(a -> a.getDoctorID().equals(this.getHospitalID()))
        .collect(Collectors.toList());
    }

    public DoctorSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(DoctorSchedule schedule) {
        this.schedule = schedule;
    }


}
