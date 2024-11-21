package model;

import enums.ReplenishmentStatus;
public class Replenishment {
    private String ID;
    private String medicineName;
    private int stock;
    private ReplenishmentStatus status;

    
    public Replenishment(String ID, String medicineName, int stock) {
        this.ID = ID;
        this.medicineName = medicineName;
        this.stock = stock;
        this.status = ReplenishmentStatus.PENDING;
    }

    public Replenishment(String ID, String medicineName, int stock, ReplenishmentStatus status) {
        this.ID = ID;
        this.medicineName = medicineName;
        this.stock = stock;
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getStock() {
        return stock;
    }

    public ReplenishmentStatus getStatus() {
        return status;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStatus(ReplenishmentStatus status) {
        this.status = status;
    }

}
