package th.ac.ku.app.models;

public class
User {
    private String userName;
    private String password;
    private String name;
    private String role;

    public User(String userName, String password, String name, String role) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.role = role;
    }
}
