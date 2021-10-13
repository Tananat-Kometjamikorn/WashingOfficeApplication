package th.ac.ku.app.models;

import java.util.HashSet;
import java.util.Set;

public class UserBranch extends User {

    private Set<OrderInfo> orderInfos = new HashSet<>();
}
