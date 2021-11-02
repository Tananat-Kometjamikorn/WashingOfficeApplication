package th.ac.ku.app.models;

public class Cloth {
//    private OrderInfo orderInfo;
    private String currentStatus;
    private int clothQuantity;
    private int orderId;

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public int getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(int clothQuantity) {
        this.clothQuantity = clothQuantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return currentStatus;
}

    public String getAllInfo() {
        return "status='" + currentStatus + '\'' +
                ", clothQuantity=" + clothQuantity +
                ", orderId=" + orderId;
    }
}