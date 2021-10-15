package th.ac.ku.app.models;

public class OrderInfo {
    private int orderID;
    private String customerName;
    private String orderDate;
    private String staffName;
    private String customerPhone;
    private Cloth cloth;
    private OrderBill orderBill;

    public OrderInfo(int orderID, String customerName, String orderDate, String staffName, String customerPhone, Cloth cloth, OrderBill orderBill) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.staffName = staffName;
        this.customerPhone = customerPhone;
        this.cloth = cloth;
        this.orderBill = orderBill;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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
}