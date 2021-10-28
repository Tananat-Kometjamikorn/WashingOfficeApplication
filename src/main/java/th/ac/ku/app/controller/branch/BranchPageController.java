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

    @FXML private Button logoutBtn, logoutBtn1, logoutBtn2, logoutBtn3, logoutBtn4,
                        infoBtn, infoBtn1, delBtn, clearOrderFieldBtn, addOrderBtn, showReportBtn,
                        showClosedInfoBtn, changePasswordBtn, clearPasswordFieldBtn;
    @FXML private TextField customerNameField, customerPhoneField, clothQuantityField;
    @FXML private PasswordField currentPasswordField, newPasswordField, confirmPasswordField;
    @FXML private TableView<OrderInfo> orderTable;
    @FXML private TableColumn<OrderInfo, Integer> orderIdCol;
    @FXML private TableColumn<OrderInfo, String > customerNameCol;
    @FXML private TableColumn<OrderInfo, String > dateCol;
    @FXML private TableColumn<OrderInfo, String > statusCol;
    @FXML private TableView<OrderInfo> orderTable2;
    @FXML private TableColumn<OrderInfo, Integer> orderIdCol2;
    @FXML private TableColumn<OrderInfo, String > customerNameCol2;
    @FXML private TableColumn<OrderInfo, String > dateCol2;
    @FXML private TableColumn<OrderInfo, String > statusCol2;
    @FXML private TableView<OrderInfo> closedOrderTable;
    @FXML private TableColumn<Object, String > closedOrderIdCol;
    @FXML private TableColumn<Object, String > closedCustomerNameCol;
    @FXML private TableColumn<Object, Integer> quantityCol;
    @FXML private Label branchNameLabel1,branchNameLabel2,branchNameLabel3,branchNameLabel4,branchNameLabel5;

    private AccountManager accountManager;
    private WashingOrderServiceAPI serviceAPI;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private OrderInfo selectedOrder;

    @FXML private void initialize(){
        Platform.runLater(() -> {
            branchNameLabel1.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel2.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel3.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel4.setText(accountManager.getCurrentBranch().getName());
            branchNameLabel5.setText("hello, "+accountManager.getCurrentBranch().getName());
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
            showOrderList();
            showCleanedOrderList();
            showClosedOrderList();
        });
    }

//Preparing Page-------------------------------------------------------------------------------------------------------------

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
        orderInfo.setAccountManager(accountManager);
        orderInfo.setServiceAPI(serviceAPI);
        orderInfo.setSelectedOrder(selectedOrder);
        popup.showAndWait();
    }

    private void selectedOrderInfo(OrderInfo orderInfo){
        selectedOrder = orderInfo;
    }


    @FXML
    public void handleDeleteBtnOnAction(ActionEvent event) throws IOException {
        if (selectedOrder!=null){
            //alert
            //if result.get == buttonType.ok
            serviceAPI.delete(selectedOrder.getOrderId());
            showOrderList();
        }
    }
    //Cleaned order page
    public void showCleanedOrderList(){
        orderTable2.setItems(getCleanedOrderInfoObservableList());
        orderIdCol2.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol2.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        dateCol2.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        statusCol2.setCellValueFactory(new PropertyValueFactory<>("cloth"));
    }

    //Add Order Page----------------------------------------------------------------------------------------------------

    @FXML
    public void handleClearOrderFieldBtnOnAction(ActionEvent event) throws IOException {
        clearAddOrderPage();
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
        cloth.setStatus("Sending to hq");

        orderInfo.setCloth(cloth);
        serviceAPI.create(orderInfo);

        clearAddOrderPage();
        showOrderList();
        //show alert and change to main page
    }

    private void clearAddOrderPage(){
        customerNameField.clear();
        customerPhoneField.clear();
        clothQuantityField.clear();
    }

    //Closed Order Page-------------------------------------------------------------------------------------------------

    public void showClosedOrderList(){
        closedOrderTable.setItems(getClosedOrderInfoObservableList());
        closedOrderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        closedCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("orderInfo"));
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

    //Setting Page------------------------------------------------------------------------------------------------------

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
                        //alert change success
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
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> notCleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo){
            if (i.getCloth().getStatus().equals("sending to hq")){
                notCleaningSuccess.add(i);
            }
        }
        ObservableList<OrderInfo> orderInfoObservableList = FXCollections.observableArrayList(notCleaningSuccess);
        return orderInfoObservableList;
    }
    private ObservableList<OrderInfo> getCleanedOrderInfoObservableList(){
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> cleaned = new ArrayList<>();
        for (OrderInfo i : allOrderInfo){
            if (i.getCloth().getStatus().equals("Success") || i.getCloth().getStatus().equals("Damaged") ||
                    i.getCloth().getStatus().equals("No contact response") || i.getCloth().getStatus().equals("Ready to pickup")){
                cleaned.add(i);
            }
        }
        ObservableList<OrderInfo> orderInfoObservableList = FXCollections.observableArrayList(cleaned);
        return orderInfoObservableList;
    }
    private ObservableList<OrderInfo> getClosedOrderInfoObservableList(){
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> cleaningSuccess = new ArrayList<>();
        for (OrderInfo i : allOrderInfo){
            if (i.getCloth().getStatus().equals("Closed")){
                cleaningSuccess.add(i);
            }
        }
        return FXCollections.observableArrayList(cleaningSuccess);
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
