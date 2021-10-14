package th.ac.ku.app.models;

public class OrderBill {
    private String orderID;
    private int cost;
    private String customer_name;
    private String billDate;

    public OrderBill(String orderID, int cost, String customer_name, String billDate) {
        this.orderID = orderID;
        this.cost = cost;
        this.customer_name = customer_name;
        this.billDate = billDate;
    }

}