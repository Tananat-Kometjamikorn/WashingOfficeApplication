package th.ac.ku.app.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import th.ac.ku.app.config.ComponentConfig;
import th.ac.ku.app.controller.branch.BranchPageController;
import th.ac.ku.app.controller.hq.HqPageController;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.util.Optional;

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
            errorAlertBox("Please fill all blanks");
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
                    branch.setAccountManager(accountManager);
                    branch.setServiceAPI(serviceAPI);
                    stage.show();
                } catch (IllegalArgumentException e) {
                    errorAlertBox(e.getMessage());
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
                    hq.setAccountManager(accountManager);
                    hq.setServiceAPI(serviceAPI);
                    stage.show();
                } catch (IllegalArgumentException e) {
                    errorAlertBox(e.getMessage());
                }
            }
        }
    }
    //alert
    public Alert confirmationAlertBox(String message) {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        return alert;
    }
    public String warningAlertBox(String message){
        alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType buttonDelete = new ButtonType("Delete");
        ButtonType buttonCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonDelete,buttonCancel);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == buttonDelete){
            return "Delete";
        }
        return "Cancel";
    }

    public void errorAlertBox(String message) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("ERROR");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void informationAlertBox(String message){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
