package th.ac.ku.app.models;

public class OrderBill {
//    private OrderInfo orderInfo;
    private int orderId;
    private int cost;
    private String cleanStatus;



    public String getCleanStatus() {
        return cleanStatus;
    }

    public void setCleanStatus(String cleanStatus) {
        this.cleanStatus = cleanStatus;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "OrderBill{" +
                "orderId=" + orderId +
                ", cost=" + cost +
                '}';
    }
}