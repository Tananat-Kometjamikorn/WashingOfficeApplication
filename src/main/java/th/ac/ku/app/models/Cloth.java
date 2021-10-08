package th.ac.ku.app.models;

public class Cloth {
    private String status;
    private String clothesType;
    private String clothQuan;
    private String problem;

    public Cloth(String status, String clothesType, String clothQuan, String problem) {
        this.status = status;
        this.clothesType = clothesType;
        this.clothQuan = clothQuan;
        this.problem = problem;
    }
}
