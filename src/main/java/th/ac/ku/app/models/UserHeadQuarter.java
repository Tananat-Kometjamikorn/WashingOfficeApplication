package th.ac.ku.app.models;

import java.util.HashSet;
import java.util.Set;

public class UserHeadQuarter extends User {

    private Set<OrderBill> orderBills = new HashSet<>();

    public Set<OrderBill> getOrderBills() {
        return orderBills;
    }

    public void setOrderBills(Set<OrderBill> orderBills) {
        this.orderBills = orderBills;
    }
}