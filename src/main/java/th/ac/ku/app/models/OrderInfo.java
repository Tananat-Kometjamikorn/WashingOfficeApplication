package th.ac.ku.app.models;

public class OrderInfo {
    private int orderId;
    private String customerName;
    private String orderDate;
    private String branchName;
    private String customerPhone;
    private String closedDate;
    private Cloth cloth;
    private OrderBill orderBill;

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(String closedDate) {
        this.closedDate = closedDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Cloth getCloth() {
        return cloth;
    }

    public void setCloth(Cloth cloth) {
        this.cloth = cloth;
    }

    public OrderBill getOrderBill() {
        return orderBill;
    }

    public void setOrderBill(OrderBill orderBill) {
        this.orderBill = orderBill;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "orderId=" + orderId +
                ", customerName='" + customerName + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", branchName='" + branchName + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", cloth=" + cloth.getAllInfo() +
                ", closedDate=" + closedDate +
                '}';
    }
}