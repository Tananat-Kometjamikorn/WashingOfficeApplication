package th.ac.ku.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.app.models.*;

import java.util.Arrays;
import java.util.List;

public class WashingOrderServiceAPI {

    private RestTemplate restTemplate;

    public WashingOrderServiceAPI(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //----------------------------------------------------------------------------------------------------------------------
    public List<UserBranch> getAllBranch() {
        String url = "http://localhost:8090/branch";
        ResponseEntity<UserBranch[]> responseEntity = restTemplate.getForEntity(url, UserBranch[].class);
        UserBranch[] userBranches = responseEntity.getBody();
        return Arrays.asList(userBranches);
    }

    public UserBranch getUserBranch(String username) {
        String url = "http://localhost:8090/branch/" + username;
        ResponseEntity<UserBranch> responseEntity = restTemplate.getForEntity(url, UserBranch.class);
        return responseEntity.getBody();
    }

    public void updateUserBranch(UserBranch userBranch) {
        String url = "http://localhost:8090/branch/" + userBranch.getUsername();
        restTemplate.put(url, userBranch, UserBranch.class);
    }

    //----------------------------------------------------------------------------------------------------------------------
    public List<Cloth> getAllCloth() {
        String url = "http://localhost:8090/cloth";
        ResponseEntity<Cloth[]> responseEntity = restTemplate.getForEntity(url, Cloth[].class);
        Cloth[] cloths = responseEntity.getBody();
        return Arrays.asList(cloths);
    }

    public Cloth getCloth(int orderId) {
        String url = "http://localhost:8090/cloth/{orderId}";
        ResponseEntity<Cloth> responseEntity = restTemplate.getForEntity(url, Cloth.class);
        return responseEntity.getBody();
    }

//----------------------------------------------------------------------------------------------------------------------
    public List<UserHeadQuarter> getAllHeadQuarter(){
        String url = "http://localhost:8090/headQuarter";
        ResponseEntity<UserHeadQuarter[]> responseEntity = restTemplate.getForEntity(url, UserHeadQuarter[].class);
        UserHeadQuarter[] userHeadQuarters = responseEntity.getBody();
        return Arrays.asList(userHeadQuarters);
    }
    public UserHeadQuarter getUserHeadQuarter(String username) {
        String url = "http://localhost:8090/headQuarter/{username}";
        ResponseEntity<UserHeadQuarter> responseEntity = restTemplate.getForEntity(url, UserHeadQuarter.class);
        return responseEntity.getBody();
    }

    public void updateUserHeadQuarter(UserHeadQuarter userHeadQuarter) {
        String url = "http://localhost:8090/headQuarter/" + userHeadQuarter.getUsername();
        restTemplate.put(url, userHeadQuarter, UserHeadQuarter.class);
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<OrderBill> getAllOrderBill(){
        String url = "http://localhost:8090/orderbill";
        ResponseEntity<OrderBill[]> responseEntity = restTemplate.getForEntity(url, OrderBill[].class);
        OrderBill[] orderBills = responseEntity.getBody();
        return Arrays.asList(orderBills);
    }
    public OrderBill getOrderBill(int orderId) {
        String url = "http://localhost:8090/orderbill/"+orderId;
        ResponseEntity<OrderBill> responseEntity = restTemplate.getForEntity(url, OrderBill.class);
        return responseEntity.getBody();
    }

    public void create(OrderBill orderBill) {
        String url = "http://localhost:8090/orderbill/";
        restTemplate.postForObject(url, orderBill, OrderBill.class);
    }
//----------------------------------------------------------------------------------------------------------------------
    public List<OrderInfo> getAllOrderInfo(){
        String url = "http://localhost:8090/orderinfo";
        ResponseEntity<OrderInfo[]> responseEntity = restTemplate.getForEntity(url, OrderInfo[].class);
        OrderInfo[] userOrderInfos = responseEntity.getBody();
        return Arrays.asList(userOrderInfos);
}
    public OrderInfo getOrderInfo(int orderId) {
        String url = "http://localhost:8090/orderinfo/"+orderId;
        ResponseEntity<OrderInfo> responseEntity = restTemplate.getForEntity(url, OrderInfo.class);
        return responseEntity.getBody();
    }

    public void create(OrderInfo orderInfo) {
        String url = "http://localhost:8090/orderinfo/";
        restTemplate.postForObject(url, orderInfo, OrderInfo.class);
    }
    public void delete(int orderId){
        String url = "http://localhost:8090/orderinfo/"+ orderId;
        restTemplate.delete(url);
    }
    public void update(int orderId, OrderInfo orderInfo){
        String url = "http://localhost:8090/orderinfo/"+ orderId;
        restTemplate.put(url, orderInfo, OrderInfo.class);
    }
}
