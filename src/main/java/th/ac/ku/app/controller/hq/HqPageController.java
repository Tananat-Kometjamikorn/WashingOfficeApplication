package th.ac.ku.app.controller.hq;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import th.ac.ku.app.controller.orderInfo.OrderInfoController;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HqPageController {
    @FXML private Button logoutBtn, logoutBtn1, logoutBtn2,
            infoBtn, showBillInfoBtn, delBtn, showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML private TextField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML private TableView<OrderInfo> orderTable;
    @FXML private TableView<OrderInfo> closedOrderTable;
    @FXML private TableColumn<Object, Object> orderIdCol;
    @FXML private TableColumn<Object, Object> customerNameCol;
    @FXML private TableColumn<Object, Object> dateCol;
    @FXML private TableColumn<Object, Object> statusCol;
    @FXML private TableColumn<Object, Object> closedOrderIdCol;
    @FXML private TableColumn<Object, Object> closedCustomerNameCol;
    @FXML private TableColumn<Object, Object> closedDateCol;
    @FXML private Label hqNameLabel1,hqNameLabel2,hqNameLabel3;

    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;
    private OrderInfo selectedOrder;
    private Alert alert;

    //Main Page

    @FXML private void initialize(){
        Platform.runLater(() -> {
            hqNameLabel1.setText("Hello, " + accountManager.getCurrentHeadQuarter().getName());
            hqNameLabel2.setText("Hello, " + accountManager.getCurrentHeadQuarter().getName());
            hqNameLabel3.setText("Hello, " + accountManager.getCurrentHeadQuarter().getName());
            showOrderList();
            showClosedOrderList();
            orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedOrderInfo(newValue);
                }
            });
            closedOrderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedOrderInfo(newValue);
                }
            });
        });
    }
    private void selectedOrderInfo(OrderInfo orderInfo){
        selectedOrder = orderInfo;
    }

    public void showOrderList(){
        orderTable.setPlaceholder(new Label("Not have order at this time"));
        orderTable.setItems(getOrderInfoObservableList());
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("cloth"));
    }

    @FXML
    public void handleLogoutBtnOnAction(ActionEvent event) throws IOException {
        if(confirmationAlertBox("Confirm to logout?").getResult().equals(ButtonType.OK)) {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/login.fxml")
            );
            stage.setScene(new Scene(loader.load(), 1024, 720));
            loader.getController();
            stage.show();
        }
    }

    @FXML
    public void handleShowInfoBtnOnAction(ActionEvent event) throws IOException {
        if(selectedOrder!=null) {
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            FXMLLoader loader = new FXMLLoader
                    (getClass().getResource("/order_info.fxml"));
            popup.setScene(new Scene(loader.load(), 640, 480));
            OrderInfoController orderInfo = loader.getController();
            orderInfo.setServiceAPI(serviceAPI);
            orderInfo.setAccountManager(accountManager);
            orderInfo.setSelectedOrder(selectedOrder);
            popup.showAndWait();
            selectedOrder = null;
            orderTable.getSelectionModel().clearSelection();
            showOrderList();
        }
    }

    //Closed Order Page

    public void showClosedOrderList(){
        closedOrderTable.setItems(getClosedOrderInfoObservableList());
        closedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        closedCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        closedDateCol.setCellValueFactory(new PropertyValueFactory<>("closedDate"));
    }

    @FXML
    public void handleShowClosedInfoBtnOnAction(ActionEvent event) throws IOException {
        if(selectedOrder != null) {
            Stage popup = new Stage();
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            FXMLLoader loader = new FXMLLoader
                    (getClass().getResource("/order_info.fxml"));
            popup.setScene(new Scene(loader.load(), 640, 480));
            OrderInfoController orderInfo = loader.getController();
            orderInfo.setAccountManager(accountManager);
            orderInfo.setServiceAPI(serviceAPI);
            orderInfo.setSelectedOrder(selectedOrder);
            orderInfo.setDisable();
            selectedOrder = null;
            closedOrderTable.getSelectionModel().clearSelection();
            popup.showAndWait();
        }
    }

    @FXML
    public void handlePrintReportBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setResizable(false);
        FXMLLoader loader = new FXMLLoader
                (getClass().getResource("/date_picker.fxml"));
        popup.setScene(new Scene(loader.load(), 600, 400));
        printReportController datePickerController = loader.getController();
        datePickerController.setServiceAPI(serviceAPI);
        popup.showAndWait();
    }

    //Setting Page

    @FXML
    public void handleChangePasswordBtnOnAction(ActionEvent event) {

        if (!currentPasswordField.getText().isEmpty() &&
                !newPasswordField.getText().isEmpty() &&
                !confirmPasswordField.getText().isEmpty()) {
            if (confirmationAlertBox("Confirm to change password").getResult().equals(ButtonType.OK)) {
                String currentPasswd = currentPasswordField.getText();
                String newPasswd = newPasswordField.getText();
                String confirmNewPasswd = confirmPasswordField.getText();
                if (accountManager.getCurrentHeadQuarter() != null) {
                    if (accountManager.getCurrentHeadQuarter().getPassword().equals(currentPasswd)) {
                        if (newPasswd.equals(confirmNewPasswd)) {
                            accountManager.getCurrentHeadQuarter().setPassword(newPasswd);
                            serviceAPI.updateUserHeadQuarter(accountManager.getCurrentHeadQuarter());
                            clearSettingPage();
                            informationAlertBox("Password changed");
                        }
                        else errorAlertBox("New password is not match Confirm new password");
                    }
                    else {
                        errorAlertBox("Your current password is incorrect");
                    }
                }
            }
        } else {
            errorAlertBox("Please fill all blank fields");
        }
    }

    @FXML
    public void handleClearPasswordFieldBtnOnAction(ActionEvent event){
        clearSettingPage();
    }
    private void clearSettingPage(){
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    //setter
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

    //get order info
    private ObservableList<OrderInfo> getOrderInfoObservableList(){
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> notCleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo){
            if (i.getCloth().getCurrentStatus().equals("Success") ||
                    i.getCloth().getCurrentStatus().equals("Damaged") ||
                    i.getCloth().getCurrentStatus().equals("Sending to hq")){
                notCleaningSuccess.add(i);
            }
        }
        return FXCollections.observableArrayList(notCleaningSuccess);
    }
    //get closed order info
    private ObservableList<OrderInfo> getClosedOrderInfoObservableList(){
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> cleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo){
            if (i.getCloth().getCurrentStatus().equals("Closed")){
                cleaningSuccess.add(i);
            }
        }
        return FXCollections.observableArrayList(cleaningSuccess);
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
