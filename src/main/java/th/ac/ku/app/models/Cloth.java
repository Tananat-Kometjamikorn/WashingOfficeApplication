package th.ac.ku.app.models;

public class Cloth {
//    private OrderInfo orderInfo;
    private String status;
    private int clothQuantity;
    private int orderId;

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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
    return status;
}

    public String getAllInfo() {
        return "status='" + status + '\'' +
                ", clothQuantity=" + clothQuantity +
                ", orderId=" + orderId;
    }
}