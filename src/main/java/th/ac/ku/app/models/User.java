package th.ac.ku.app.models;

public class User {
    private String username;
    private String password;
    private String name;


    public boolean checkAccountLogin(String username , String password) {
        if (this.username.equals(username) && this.password.equals(password)) {
            return true;
        }
//        else{
//            throw new IllegalArgumentException("Username or Password incorrect");
//      }
        return false;
        }

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
