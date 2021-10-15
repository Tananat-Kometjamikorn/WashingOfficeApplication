package th.ac.ku.app.models;

public class OrderBill {
    private int orderID;
    private int cost;
    private String customer_name;
    private String billDate;

    public OrderBill(int orderID, int cost, String customer_name, String billDate) {
        this.orderID = orderID;
        this.cost = cost;
        this.customer_name = customer_name;
        this.billDate = billDate;
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

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }
}