package th.ac.ku.app.models;

import java.util.HashSet;
import java.util.Set;

public class UserBranch extends User {

    public UserBranch(String userName, String password, String name) {
        super(userName, password, name);
    }
    private Set<OrderInfo> orderInfos = new HashSet<>();


}
