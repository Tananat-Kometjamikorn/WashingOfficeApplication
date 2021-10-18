package th.ac.ku.app.models;

public class OrderBill {
    private int orderID;
    private int cost;
    private String customerName;
    private String orderDate;

    public OrderBill(int orderID, int cost, String customerName, String orderDate) {
        this.orderID = orderID;
        this.cost = cost;
        this.customerName = customerName;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
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
}