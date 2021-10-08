package th.ac.ku.app.models;

public class Order {
    private String orderID;
    private String customerName;
    private String orderDate;
    private String staffName;
    private String customerPhone;
    private Cloth cloth;
    private Bill bill;

    public Order(String orderID, String customerName, String orderDate, String staffName, String customerPhone, Cloth cloth, Bill bill) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.staffName = staffName;
        this.customerPhone = customerPhone;
        this.cloth = cloth;
        this.bill = bill;
    }

}
