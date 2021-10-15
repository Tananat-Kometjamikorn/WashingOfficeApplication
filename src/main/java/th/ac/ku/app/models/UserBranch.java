package th.ac.ku.app.models;

import java.util.HashSet;
import java.util.Set;

public class UserBranch extends User {


    private Set<OrderInfo> orderInfos = new HashSet<>();

    public Set<OrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(Set<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }
}
