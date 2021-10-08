package th.ac.ku.app.models;

public class Bill {
    private String orderID;
    private double cost;
    private String customer_name;
    private String billDate;

    public Bill(String orderID, double cost, String customer_name, String billDate) {
        this.orderID = orderID;
        this.cost = cost;
        this.customer_name = customer_name;
        this.billDate = billDate;
    }

}
