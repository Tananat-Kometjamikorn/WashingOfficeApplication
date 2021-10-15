package th.ac.ku.app.models;

public class Cloth {
    private String status;
    private String clothQuantity;
    private int orderID;


    public Cloth(String status, String clothQuantity) {
        this.status = status;
        this.clothQuantity = clothQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(String clothQuantity) {
        this.clothQuantity = clothQuantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
}