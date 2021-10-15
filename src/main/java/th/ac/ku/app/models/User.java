package th.ac.ku.app.models;

public class User {
    private String userName;
    private String password;
    private String name;


    public boolean checkAccountLogin(String userName , String password){
        return this.userName.equals(userName) && this.password.equals(password);
    }

    public int changePassword(String currentPassword, String newPassword, String confirmNewPassword) {
        if (currentPassword.equals(password)) {
            if (newPassword.equals(confirmNewPassword)) {
                password = newPassword;
                return 0;
            }
            else{
                return 1;
            }
        }
        else{
            return 2;
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
