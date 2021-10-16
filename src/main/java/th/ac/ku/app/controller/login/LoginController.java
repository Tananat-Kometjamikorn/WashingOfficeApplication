package th.ac.ku.app.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import th.ac.ku.app.config.ComponentConfig;
import th.ac.ku.app.controller.branch.BranchPageController;
import th.ac.ku.app.controller.hq.HqPageController;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.applet.AppletContext;
import java.io.IOException;

@Component
public class LoginController {

    @FXML private Button loginBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private RadioButton branchBtn, headQuarterBtn;

    private Alert alert;
    private ToggleGroup roleToggleGroup;
    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;


    @FXML
    public void initialize() {
        accountManager = new AccountManager();
        ApplicationContext context = new AnnotationConfigApplicationContext(ComponentConfig.class);
        serviceAPI = context.getBean(WashingOrderServiceAPI.class);
        accountManager.setBranchHashMapFromList(serviceAPI.getAllBranch());
        accountManager.setHeadQuarterHashMapFromList(serviceAPI.getAllHeadQuarter());

        roleToggleGroup = new ToggleGroup();
        this.branchBtn.setToggleGroup(roleToggleGroup);
        this.headQuarterBtn.setToggleGroup(roleToggleGroup);
        branchBtn.setSelected(true);
    }

    @FXML
    public void handleLoginBtnOnAction(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(username.equals("") || password.equals("")){
            System.out.println("fill all?");
        }else{
            if (this.roleToggleGroup.getSelectedToggle().equals(this.branchBtn)) {
                Button b = (Button) event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                try {
                    accountManager.checkAccount(username, password, "branch");
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/branch_page.fxml")
                    );
                    stage.setScene(new Scene(loader.load(), 1024, 720));
                    BranchPageController branch = loader.getController();
                    stage.show();
                } catch (IllegalArgumentException e) {
                    System.out.println("branch error");
                }
            }
            else if (this.roleToggleGroup.getSelectedToggle().equals(this.headQuarterBtn)) {
                Button b = (Button) event.getSource();
                Stage stage = (Stage) b.getScene().getWindow();
                try {
                    accountManager.checkAccount(username, password, "headquarter");
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/hq_page.fxml")
                    );
                    stage.setScene(new Scene(loader.load(), 1024, 720));
                    HqPageController hq = loader.getController();
                    stage.show();
                } catch (IllegalArgumentException e) {
                    System.out.println("hq error");
                }
            }
        }

    }
}
//    private void AlertBox(){
//        alert = new Alert(Alert.AlertType.ERROR);
//        alert.initStyle(StageStyle.UTILITY);
//        alert.setTitle("ERROR");
//        alert.setHeaderText(null);
//        alert.setContentText("kuy");
//        alert.showAndWait();
//    }
// ใช้ confirmation แทนได้มั้งจะได้ไม่รก