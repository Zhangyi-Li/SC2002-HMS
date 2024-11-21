package model;

import enums.ReplenishmentStatus;
import java.util.Date;
public class Replenishment {
    private String ID;
    private String medicineName;
    private Date date;
    private int stock;
    private ReplenishmentStatus status;

    public Replenishment(String ID, String medicineName, Date date, int stock, ReplenishmentStatus status) {
        this.ID = ID;
        this.medicineName = medicineName;
        this.date = date;
        this.stock = stock;
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public Date getDate() {
        return date;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStatus(ReplenishmentStatus status) {
        this.status = status;
    }

}
