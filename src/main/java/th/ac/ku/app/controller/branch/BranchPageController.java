package th.ac.ku.app.controller.branch;

import com.sun.org.apache.xpath.internal.operations.Or;
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
import org.springframework.core.annotation.Order;
import th.ac.ku.app.controller.login.LoginController;
import th.ac.ku.app.controller.orderInfo.OrderInfoController;
import th.ac.ku.app.models.Cloth;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BranchPageController {

    @FXML private Button logoutBtn, infoBtn, delBtn, clearOrderFieldBtn, addOrderBtn,
                            showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML private TextField customerNameField, customerPhoneField, clothQuantityField;
    @FXML private PasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML private TableView orderTable;
    @FXML private TableColumn<OrderInfo, Integer> orderIdCol;
    @FXML private TableColumn<OrderInfo, String > customerNameCol;
    @FXML private TableColumn<OrderInfo, String > dateCol;
    @FXML private TableColumn<OrderInfo, String > statusCol;
    @FXML private TableView closedOrderTable;
    @FXML private TableColumn<Object, String > closedOrderIdCol;
    @FXML private TableColumn<Object, String > closedCustomerNameCol;
    @FXML private TableColumn<Object, Integer> quantityCol;
    @FXML private Label branchNameLabel;

    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private OrderInfo selectedOrder;
//Main Page

    @FXML private void initialize(){
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
                branchNameLabel.setText(accountManager.getCurrentBranch().getName());
                orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        selectedOrderInfo((OrderInfo) newValue);
                    }
                });
                showOrderList();
                showClosedOrderList();
            }
        });
    }

    public void showOrderList(){
        orderTable.setItems(getOrderInfoObservableList());
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("cloth"));
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
        orderInfo.setSelectedOrder(selectedOrder);
        popup.showAndWait();
    }

    private void selectedOrderInfo(OrderInfo orderInfo){
        selectedOrder = orderInfo;
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

        String customerName = customerNameField.getText();
        String customerPhone = customerPhoneField.getText();
        int quantity = Integer.parseInt(clothQuantityField.getText());
        String date =  LocalDateTime.now().format(formatter);

        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderDate(date);
        orderInfo.setCustomerName(customerName);
        orderInfo.setCustomerPhone(customerPhone);
        orderInfo.setBranchName(accountManager.getCurrentBranch().getName());

        Cloth cloth = new Cloth();
        cloth.setClothQuantity(quantity);
        cloth.setStatus("Preparing");

        orderInfo.setCloth(cloth);
//        cloth.setOrderInfo(orderInfo);

//        serviceAPI.create(cloth);
        serviceAPI.create(orderInfo);


        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();

        showOrderList();
    }

    //Closed Order Page

    public void showClosedOrderList(){
        closedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        closedCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("clothQuantity"));
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
            if (accountManager.getCurrentBranch() != null) {
                if (accountManager.getCurrentBranch().getPassword().equals(currentPasswd)) {
                    if (newPasswd.equals(confirmNewPasswd)) {
                        accountManager.getCurrentBranch().setPassword(newPasswd);
                        serviceAPI.updateUserBranch(accountManager.getCurrentBranch());
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

    //get order detail
    private ObservableList<OrderInfo> getOrderInfoObservableList(){
        ObservableList<OrderInfo> orderInfoObservableList = FXCollections.observableArrayList(serviceAPI.getAllOrderInfo());
        return orderInfoObservableList;
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
