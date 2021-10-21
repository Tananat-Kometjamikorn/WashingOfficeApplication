package th.ac.ku.app.controller.hq;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import th.ac.ku.app.controller.login.LoginController;
import th.ac.ku.app.controller.orderInfo.OrderInfoController;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;

public class HqPageController {
    @FXML private Button logoutBtn, infoBtn, showBillInfoBtn, delBtn, showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML private TextField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML private TableView orderTable, closedOrderTable;
    @FXML private TableColumn orderIdCol, customerNameCol, dateCol, closedOrderIdCol, closedCustomerNameCol, quantityCol;
    @FXML private Label hqNameLabel1,hqNameLabel2,hqNameLabel3;

    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;

    //Main Page

    @FXML private void initialize(){
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                hqNameLabel1.setText(accountManager.getCurrentHeadQuarter().getName());
                hqNameLabel2.setText(accountManager.getCurrentHeadQuarter().getName());
                hqNameLabel3.setText(accountManager.getCurrentHeadQuarter().getName());
                showOrderList();
                showClosedOrderList();
            }
        });
    }

    public void showOrderList(){
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));;
    }

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

    //Closed Order Page

    public void showClosedOrderList(){
        closedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        closedCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("clothQuantity"));;
    }

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
        if(!currentPasswordField.getText().isEmpty()&&!newPasswordField.getText().isEmpty()&&!confirmPasswordField.getText().isEmpty()) {
            String currentPasswd = currentPasswordField.getText();
            String newPasswd = newPasswordField.getText();
            String confirmNewPasswd = confirmPasswordField.getText();
            if (accountManager.getCurrentHeadQuarter() != null) {
                if (accountManager.getCurrentHeadQuarter().getPassword().equals(currentPasswd)) {
                    if (newPasswd.equals(confirmNewPasswd)) {
                        accountManager.getCurrentHeadQuarter().setPassword(newPasswd);
                        serviceAPI.updateUserHeadQuarter(accountManager.getCurrentHeadQuarter());
                        clearSettingPage();
                    }
                }
            }
        }
    }

    @FXML
    public void handleClearPasswordFieldBtnOnAction(ActionEvent event) throws IOException {
        clearSettingPage();
    }
    private void clearSettingPage(){
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    //getter and setter
    public AccountManager getAccountManager() {
        return accountManager;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public WashingOrderServiceAPI getServiceAPI() {
        return serviceAPI;
    }

    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }
}
