package th.ac.ku.app.models;

public class Cloth {
    private String status;
    private String clothQuantity;
    private OrderInfo order_id;


    public Cloth(String status, String clothQuantity) {
        this.status = status;
        this.clothQuantity = clothQuantity;
    }
}