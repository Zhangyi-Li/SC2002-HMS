

public class Prescription {
    // Private member variables
    private int prescriptionID;
    private String prescriptionStatus;

    // Constructor
    public Prescription(int prescriptionID, String prescriptionStatus) {
        this.prescriptionID = prescriptionID;
        this.prescriptionStatus = prescriptionStatus;
    }

    // Getter for prescriptionID
    public int getPrescriptionID() {
        return prescriptionID;
    }

    // Setter for prescriptionID
    public void setPrescriptionID(int prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    // Getter for prescriptionStatus
    public String getPrescriptionStatus() {
        return prescriptionStatus;
    }

    // Setter for prescriptionStatus
    public void setPrescriptionStatus(String prescriptionStatus) {
        this.prescriptionStatus = prescriptionStatus;
    }

    // Method to display prescription details
    public void displayPrescriptionDetails() {
        System.out.println("Prescription ID: " + prescriptionID);
        System.out.println("Prescription Status: " + prescriptionStatus);
    }
}
