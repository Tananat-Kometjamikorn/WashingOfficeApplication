package th.ac.ku.app.models;

public class OrderInfo {
    private String orderID;
    private String customerName;
    private String orderDate;
    private String staffName;
    private String customerPhone;
    private Cloth cloth;
    private OrderBill orderBill;

    public OrderInfo(String orderID, String customerName, String orderDate, String staffName, String customerPhone, Cloth cloth, OrderBill orderBill) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.orderDate = orderDate;
        this.staffName = staffName;
        this.customerPhone = customerPhone;
        this.cloth = cloth;
        this.orderBill = orderBill;
    }

}
