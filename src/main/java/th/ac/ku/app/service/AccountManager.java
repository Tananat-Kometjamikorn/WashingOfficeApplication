package th.ac.ku.app.service;

import th.ac.ku.app.models.UserBranch;
import th.ac.ku.app.models.UserHeadQuarter;

import java.util.HashMap;
import java.util.List;

public class AccountManager {
    private HashMap<String, UserBranch> branchHashMap = new HashMap<>();
    private HashMap<String, UserHeadQuarter> headQuarterHashMap = new HashMap<>();

    private UserBranch currentBranch;
    private UserHeadQuarter currentHeadQuarter;

    public AccountManager(){
        currentBranch = null;
        currentHeadQuarter = null;
    }

    public void checkAccount(String username, String password, String role){

        if (role.equals("branch")){
            for(UserBranch branch : branchHashMap.values()){
                try{
                    if(branch.checkAccountLogin(username,password)){
                        currentBranch = branch;
                        return; }
                }
                catch (IllegalArgumentException e){
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            if(currentBranch==null) throw new IllegalArgumentException("We don't have this account");
        }
        else {
            for(UserHeadQuarter headQuarter : headQuarterHashMap.values()){
                try{
                    if(headQuarter.checkAccountLogin(username,password)){
                        currentHeadQuarter = headQuarter;
                        return; }
                }
                catch (IllegalArgumentException e){
                    throw new IllegalArgumentException(e.getMessage());
                }
            }
            if(currentHeadQuarter==null) throw new IllegalArgumentException("We don't have this account");
        }
    }
    public HashMap<String, UserBranch> branchListToMap(List<UserBranch> list){
        branchHashMap.clear();
        for (UserBranch b : list){
            branchHashMap.put(b.getUserName(), b);
        }
        return branchHashMap;
    }
    public void setBranchHashMap(HashMap<String, UserBranch> branchHashMap){
        this.branchHashMap = branchHashMap;
    }
    public HashMap<String, UserBranch> getBranchHashMap(){
        return branchHashMap;
    }
    public UserBranch getCurrentBranch(){
        return this.currentBranch;
    }
    public void setCurrentBranch(UserBranch branch){
        this.currentBranch = branch;
    }
    public void setBranchHashMapFromList(List<UserBranch> branchList){
        this.branchHashMap = branchListToMap(branchList);
    }


    public HashMap<String, UserHeadQuarter> headQuarterListToMap(List<UserHeadQuarter> list){
        headQuarterHashMap.clear();
        for (UserHeadQuarter h : list){
            headQuarterHashMap.put(h.getUserName(), h);
        }
        return headQuarterHashMap;
    }
    public void setHeadQuarterHashMap(HashMap<String, UserHeadQuarter> headQuarterHashMap){
        this.headQuarterHashMap = headQuarterHashMap;
    }
    public HashMap<String, UserHeadQuarter>UserHeadQuarterHashMap(){
        return headQuarterHashMap;
    }
    public UserHeadQuarter getCurrentHeadQuarter(){
        return this.currentHeadQuarter;
    }
    public void setCurrentUserHeadQuarter(UserHeadQuarter headQuarter){
        this.currentHeadQuarter = headQuarter;
    }
    public void setHeadQuarterHashMapFromList(List<UserHeadQuarter> headQuarterList){
        this.headQuarterHashMap = headQuarterListToMap(headQuarterList);
    }
}
