package storage;

public class StorageGlobal {
    private static PatientStorage patientStorage;
    private static StaffStorage staffStorage;
    private static AppointmentStorage appointmentStorage;
    private static DoctorScheduleStorage doctorScheduleStorage;
    private static PrescriptionStorage prescriptionStorage;
    private static MedicationStorage medicationStorage;
    private static MedicalRecordStorage medicalRecordStorage;
    private static ReplenishmentStorage replenishmentStorage;

    public static PatientStorage PatientStorage() {
        if (patientStorage == null) {
            patientStorage = new PatientStorage();
            patientStorage.importData();
        }
        return patientStorage;
    }

    public static StaffStorage StaffStorage() {
        if (staffStorage == null) {
            staffStorage = new StaffStorage();
            staffStorage.importData();
        }
        return staffStorage;
    }

    public static AppointmentStorage AppointmentStorage() {
        if (appointmentStorage == null) {
            appointmentStorage = new AppointmentStorage();
            appointmentStorage.importData();
        }
        return appointmentStorage;
    }

    public static DoctorScheduleStorage DoctorScheduleStorage() {
        if (doctorScheduleStorage == null) {
            doctorScheduleStorage = new DoctorScheduleStorage();
            doctorScheduleStorage.importData();
        }
        return doctorScheduleStorage;
    }

    public static PrescriptionStorage PrescriptionStorage() {
        if (prescriptionStorage == null) {
            prescriptionStorage = new PrescriptionStorage();
            prescriptionStorage.importData();
        }
        return prescriptionStorage;
    }

    public static MedicationStorage MedicationStorage() {
        if (medicationStorage == null) {
            medicationStorage = new MedicationStorage();
            medicationStorage.importData();
        }
        return medicationStorage;
    }

    public static MedicalRecordStorage MedicalRecordStorage() {
        if (medicalRecordStorage == null) {
            medicalRecordStorage = new MedicalRecordStorage();
            medicalRecordStorage.importData();
        }
        return medicalRecordStorage;
    }

    public static ReplenishmentStorage ReplenishmentStorage() {
        if (replenishmentStorage == null) {
            replenishmentStorage = new ReplenishmentStorage();
            replenishmentStorage.importData();
        }
        return replenishmentStorage;
    }

}
