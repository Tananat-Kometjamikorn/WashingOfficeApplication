package th.ac.ku.app.controller.branch;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
import th.ac.ku.app.controller.orderInfo.OrderInfoController;
import th.ac.ku.app.models.Cloth;
import th.ac.ku.app.models.OrderBill;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BranchPageController {

    @FXML
    private Button logoutBtn, logoutBtn1, logoutBtn2, logoutBtn3, logoutBtn4,
            infoBtn, infoBtn1, delBtn, clearOrderFieldBtn, addOrderBtn, showReportBtn,
            showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML
    private TextField customerNameField, customerPhoneField, clothQuantityField;
    @FXML
    private PasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML
    private TableView<OrderInfo> orderTable;
    @FXML
    private TableColumn<OrderInfo, Integer> orderIdCol;
    @FXML
    private TableColumn<OrderInfo, String> customerNameCol;
    @FXML
    private TableColumn<OrderInfo, String> dateCol;
    @FXML
    private TableColumn<OrderInfo, String> statusCol;
    @FXML
    private TableView<OrderInfo> orderTable2;
    @FXML
    private TableColumn<OrderInfo, Integer> orderIdCol2;
    @FXML
    private TableColumn<OrderInfo, String> customerNameCol2;
    @FXML
    private TableColumn<OrderInfo, String> dateCol2;
    @FXML
    private TableColumn<OrderInfo, String> statusCol2;
    @FXML
    private TableView<OrderInfo> closedOrderTable;
    @FXML
    private TableColumn<Object, String> closedOrderIdCol;
    @FXML
    private TableColumn<Object, String> closedCustomerNameCol;
    @FXML
    private TableColumn<Object, String> closedDateCol;
    @FXML
    private Label branchNameLabel1, branchNameLabel2, branchNameLabel3, branchNameLabel4, branchNameLabel5;

    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private OrderInfo selectedOrder;
    private Alert alert;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            branchNameLabel1.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel2.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel3.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel4.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel5.setText("hello, " + accountManager.getCurrentBranch().getName());
            orderTable.setPlaceholder(new Label("Not have new order at this time"));
            orderTable2.setPlaceholder(new Label("Not have cleaned order at this time"));
            closedOrderTable.setPlaceholder(new Label("Not have closed order at this time"));
            orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedOrderInfo(newValue);
                }
            });
            orderTable2.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedOrderInfo(newValue);
                }
            });
            closedOrderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedOrderInfo(newValue);
                }
            });
            showOrderList();
            showCleanedOrderList();
            showClosedOrderList();
        });
    }

//Preparing Page-------------------------------------------------------------------------------------------------------------

    public void showOrderList() {
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
        popup.showAndWait();
        showOrderList();
        showCleanedOrderList();
        showClosedOrderList();
    }

    private void selectedOrderInfo(OrderInfo orderInfo) {
        selectedOrder = orderInfo;
    }


    @FXML
    public void handleDeleteBtnOnAction(ActionEvent event) {
        if (selectedOrder != null) {
            if(warningAlertBox("Sure to delete select order?").equals("Delete")){
                serviceAPI.delete(selectedOrder.getOrderId());
                showOrderList();
            }
        }
    }

    //Cleaned order page
    public void showCleanedOrderList() {
        orderTable2.setItems(getCleanedOrderInfoObservableList());
        orderIdCol2.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        dateCol2.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusCol2.setCellValueFactory(new PropertyValueFactory<>("cloth"));
    }

    //Add Order Page----------------------------------------------------------------------------------------------------

    @FXML
    public void handleClearOrderFieldBtnOnAction(ActionEvent event) {
        clearAddOrderPage();
    }

    @FXML
    public void handleAddOrderBtnOnAction(ActionEvent event) {

        String customerName = customerNameField.getText();
        String customerPhone = customerPhoneField.getText();
        int quantity = Integer.parseInt(clothQuantityField.getText());
        String date = LocalDateTime.now().format(formatter);

        if(confirmationAlertBox("Confirm to add order?").getResult().equals(ButtonType.OK)) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderDate(date);
            orderInfo.setCustomerName(customerName);
            orderInfo.setCustomerPhone(customerPhone);
            orderInfo.setBranchName(accountManager.getCurrentBranch().getName());

            Cloth cloth = new Cloth();
            cloth.setClothQuantity(quantity);
            cloth.setStatus("Sending to hq");

            OrderBill orderBill = new OrderBill();
            orderBill.setCost(0);

            orderInfo.setCloth(cloth);
            orderInfo.setOrderBill(orderBill);
            serviceAPI.create(orderInfo);

            clearAddOrderPage();
            showOrderList();
            informationAlertBox("Add order success");
        }
    }

    private void clearAddOrderPage() {
        customerNameField.clear();
        customerPhoneField.clear();
        clothQuantityField.clear();
    }

    //Closed Order Page-------------------------------------------------------------------------------------------------

    public void showClosedOrderList() {
        closedOrderTable.setItems(getClosedOrderInfoObservableList());
        closedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        closedCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        closedDateCol.setCellValueFactory(new PropertyValueFactory<>("closedDate"));
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
        orderInfo.setAccountManager(accountManager);
        orderInfo.setServiceAPI(serviceAPI);
        orderInfo.setSelectedOrder(selectedOrder);
        orderInfo.setDisable();
        popup.showAndWait();
    }

    //Setting Page------------------------------------------------------------------------------------------------------

    @FXML
    public void handleChangePasswordBtnOnAction(ActionEvent event) {

        if (!currentPasswordField.getText().isEmpty() &&
                !newPasswordField.getText().isEmpty() &&
                !confirmPasswordField.getText().isEmpty()) {
            if (confirmationAlertBox("Confirm to change password").getResult().equals(ButtonType.OK)) {
                String currentPasswd = currentPasswordField.getText();
                String newPasswd = newPasswordField.getText();
                String confirmNewPasswd = confirmPasswordField.getText();
                if (accountManager.getCurrentBranch() != null) {
                    if (accountManager.getCurrentBranch().getPassword().equals(currentPasswd)) {
                        if (newPasswd.equals(confirmNewPasswd)) {
                            accountManager.getCurrentBranch().setPassword(newPasswd);
                            serviceAPI.updateUserBranch(accountManager.getCurrentBranch());
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
    public void handleClearPasswordFieldBtnOnAction(ActionEvent event) {
        clearSettingPage();
    }

    private void clearSettingPage() {
        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();
    }

    //get order detail
    private ObservableList<OrderInfo> getOrderInfoObservableList() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> notCleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getCloth().getStatus().equals("Sending to hq")) {
                notCleaningSuccess.add(i);
            }
        }
        return FXCollections.observableArrayList(notCleaningSuccess);
    }

    private ObservableList<OrderInfo> getCleanedOrderInfoObservableList() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> cleaned = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getCloth().getStatus().equals("Success") || i.getCloth().getStatus().equals("Damaged") ||
                    i.getCloth().getStatus().equals("No contact response") || i.getCloth().getStatus().equals("Ready to pickup")) {
                cleaned.add(i);
            }
        }
        return FXCollections.observableArrayList(cleaned);
    }

    private ObservableList<OrderInfo> getClosedOrderInfoObservableList() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> cleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getCloth().getStatus().equals("Closed")) {
                cleaningSuccess.add(i);
            }
        }
        return FXCollections.observableArrayList(cleaningSuccess);
    }

    //setter
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
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
