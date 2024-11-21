package model;

public class Medication {
    private String medicineName;
    private int stock;
    private int lowStockLevelAlert;
    private String stockStatus;

    public Medication() {
    }

    public Medication(String medicineName, int stock, int lowStockLevelAlert) {
        this.medicineName = medicineName;
        this.stock = stock;
        this.lowStockLevelAlert = lowStockLevelAlert;
        if (stock == 0) {
            this.stockStatus = "Out of Stock";
        } else if (stock <= lowStockLevelAlert) {
            this.stockStatus = "Low Stock";
        } else {
            this.stockStatus = "In Stock";
        }
    }

    public String getMedicineName() {
        return medicineName;
    }

    public int getStock() {
        return stock;
    }

    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    public void setMedicineName(String newMedName) {
        this.medicineName = newMedName;
    }

    public void setStock(int newStock) {
        this.stock = newStock;
        if (newStock < this.lowStockLevelAlert) {
            this.stockStatus = "Low Stock";
        } else {
            this.stockStatus = "In Stock";
        }
    }

    public void setLowStockLevelAlert(int newLowStockLevelAlert) {
        this.lowStockLevelAlert = newLowStockLevelAlert;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    @Override
    public String toString() {
        return medicineName + "," + stock + "," + lowStockLevelAlert + "," + stockStatus;
    }
}
