package th.ac.ku.app.models;

public class User {
    private String username;
    private String password;
    private String name;


    public boolean checkAccountLogin(String username , String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

//    public int checkChangePassword(String currentPassword, String newPassword, String confirmNewPassword) {
//        if (currentPassword.equals(this.password)) {
//            if (newPassword.equals(confirmNewPassword)) {
//                this.password = newPassword;
//                return 0;
//            }
//            else{
//                return 1;
//            }
//        }
//        else{
//            return 2;
//        }
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
