package th.ac.ku.app.controller.branch;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import th.ac.ku.app.controller.login.LoginController;
import th.ac.ku.app.controller.orderInfo.OrderInfoController;

import java.io.IOException;

public class BranchPageController {
    @FXML private Button logoutBtn, infoBtn, delBtn, clearOrderFieldBtn, addOrderBtn, showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML private TextField customerNameField, customerPhoneField, clothQuantityField,
                            currentPasswordField, newPasswordField, confirmPasswordField;


    //Main Page

    @FXML
    public void handleLogoutBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/login.fxml")
        );
        stage.setScene(new Scene(loader.load(), 1024, 720));
        LoginController login = loader.getController();
        stage.show();
    }

    @FXML
    public void handleShowInfoBtnOnAction(ActionEvent event) throws IOException {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        FXMLLoader loader = new FXMLLoader
                (getClass().getResource("/order_info.fxml"));
        popup.setScene(new Scene(loader.load(), 640, 480));
        OrderInfoController orderInfo = loader.getController();
        popup.showAndWait();
    }

    @FXML
    public void handleDeleteBtnOnAction(ActionEvent event) throws IOException {

    }

    //Add Order Page

    @FXML
    public void handleClearOrderFieldBtnOnAction(ActionEvent event) throws IOException {
        customerNameField.clear();
        customerPhoneField.clear();
        clothQuantityField.clear();
    }

    @FXML
    public void handleAddOrderBtnOnAction(ActionEvent event) throws IOException {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    //Closed Order Page

    @FXML
    public void handleShowClosedInfoBtnOnAction(ActionEvent event) throws IOException {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        FXMLLoader loader = new FXMLLoader
                (getClass().getResource("/order_info.fxml"));
        popup.setScene(new Scene(loader.load(), 640, 480));
        OrderInfoController orderInfo = loader.getController();
        orderInfo.setDisable();
        popup.showAndWait();
    }

    //Setting Page

    @FXML
    public void handleChangePasswordBtnOnAction(ActionEvent event) throws IOException {

    }

    @FXML
    public void handleClearPasswordFieldBtnOnAction(ActionEvent event) throws IOException {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }



}
