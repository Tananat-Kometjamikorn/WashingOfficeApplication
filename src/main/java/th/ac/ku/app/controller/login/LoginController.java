package th.ac.ku.app.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import th.ac.ku.app.controller.branch.BranchPageController;

import java.io.IOException;

public class LoginController {
    @FXML private Button loginBtn;

    @FXML
    public void handleLoginBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/branch_page.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1024, 720));
        BranchPageController branch = loader.getController();
        stage.show();
    }
}
