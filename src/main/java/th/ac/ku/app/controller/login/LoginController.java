package th.ac.ku.app.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Component;
import th.ac.ku.app.controller.branch.BranchPageController;
import th.ac.ku.app.controller.hq.HqPageController;
import th.ac.ku.app.controller.alert.Confirmation;

import java.io.IOException;

@Component
public class LoginController{

    @FXML private Button loginBtn;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private RadioButton branchBtn,headQuarterBtn;

    private Alert alert;
    private ToggleGroup roleToggleGroup = new ToggleGroup();


    @FXML public void initialize() {
        this.branchBtn.setToggleGroup(roleToggleGroup);
        this.headQuarterBtn.setToggleGroup(roleToggleGroup);
        branchBtn.setSelected(true);
    }

    @FXML
    public void handleLoginBtnOnAction(ActionEvent event) throws IOException {

        if (this.roleToggleGroup.getSelectedToggle().equals(this.branchBtn)) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/branch_page.fxml")
            );
            stage.setScene(new Scene(loader.load(), 1024, 720));
            BranchPageController branch = loader.getController();
            stage.show();
        }
        else if (this.roleToggleGroup.getSelectedToggle().equals(this.headQuarterBtn)) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/hq_page.fxml")
            );
            stage.setScene(new Scene(loader.load(), 1024, 720));
            HqPageController hq = loader.getController();
            stage.show();
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

}
