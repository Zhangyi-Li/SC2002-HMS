public class PrescriptionMedication extends Medication {

    private int prescriptionID;
    private String medicationName;
    private int quantity;

    // Constructor
    public PrescriptionMedication(int prescriptionID, String medicationName, int quantity) {
        this.prescriptionID = prescriptionID;
        this.medicationName = medicationName;
        this.quantity = quantity;
    }
    public int getPrescriptionID() {return prescriptionID;}
    public void setPrescriptionID(int prescriptionID) {this.prescriptionID = prescriptionID;}
  
    public String getMedicationName() {return medicationName;}
    public void setMedicationName(String medicationName) {this.medicationName = medicationName;}
  
    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}
}
