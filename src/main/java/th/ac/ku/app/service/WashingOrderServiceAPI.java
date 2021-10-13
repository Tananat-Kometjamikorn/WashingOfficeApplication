package th.ac.ku.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import th.ac.ku.app.models.UserBranch;

import java.util.Arrays;
import java.util.List;

public class WashingOrderServiceAPI {

    private RestTemplate restTemplate;

    public WashingOrderServiceAPI(RestTemplate restTemplate) {
            this.restTemplate = restTemplate;
        }

    public List<UserBranch> getAllBranch(){
        String url = "http://localhost:8090/branch";
        ResponseEntity<UserBranch[]> responseEntity = restTemplate.getForEntity(url, UserBranch[].class);
        UserBranch[] userBranches = responseEntity.getBody();
        return Arrays.asList(userBranches);
    }
    public UserBranch getUserBranch(String username){
        String url = "http://localhost:8090/branch/{username}";
        ResponseEntity<UserBranch> responseEntity = restTemplate.getForEntity(url, UserBranch.class);
        return responseEntity.getBody();
    }
    public void updateUserBranch(UserBranch userBranch){
        String url = "http://localhost:8090/owner/" + userBranch.getUserName();
    }
}
