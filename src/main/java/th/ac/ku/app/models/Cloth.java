package th.ac.ku.app.models;

public class Cloth {
    private String status;
    private int clothQuantity;
    private int orderID;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(int clothQuantity) {
        this.clothQuantity = clothQuantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}