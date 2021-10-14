package th.ac.ku.app.models;

import java.util.HashSet;
import java.util.Set;

public class UserHeadQuarter extends User {

    private Set<OrderBill> orderBills = new HashSet<>();
    public UserHeadQuarter(String userName, String password, String name) {
        super(userName, password, name);
    }

}