package th.ac.ku.app.controller.orderInfo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;

public class OrderInfoController {

    @FXML private Button closeBtn, changeBtn, createBillBtn;
    @FXML private Label orderIdLabel, customerNameLabel, createDateLabel, staffNameLabel, phoneNumLabel, clothQuantityLabel;
    @FXML private ChoiceBox<String> statusChoiceBox;

    private ObservableList<String> statusTypeList = FXCollections.observableArrayList("Sending to hq", "Sending to branch", "Ready to pickup", "No contact response", "Closed");
    private OrderInfo selectedOrder;
    private WashingOrderServiceAPI serviceAPI;
    private AccountManager accountManager;

    @FXML private void initialize(){
        Platform.runLater(() -> {
            statusChoiceBox.setItems(statusTypeList);
            orderIdLabel.setText(Integer.toString(selectedOrder.getOrderId()));
            customerNameLabel.setText(selectedOrder.getCustomerName());
            createDateLabel.setText(selectedOrder.getOrderDate());
            staffNameLabel.setText(selectedOrder.getBranchName());
            phoneNumLabel.setText(selectedOrder.getCustomerPhone());
            clothQuantityLabel.setText(Integer.toString(selectedOrder.getCloth().getClothQuantity()));
            setOrderStatus();
        });
    }

    public void setOrderStatus(){
        if(selectedOrder.getCloth().getStatus().equals("Sending to hq")){
            statusChoiceBox.setValue("Sending to hq");
        }else if(selectedOrder.getCloth().getStatus().equals("Sending to branch")){
            statusChoiceBox.setValue("Sending to branch");
        }else if(selectedOrder.getCloth().getStatus().equals("Ready to pickup")){
            statusChoiceBox.setValue("Ready to pickup");
        }else if(selectedOrder.getCloth().getStatus().equals("No contact response")){
            statusChoiceBox.setValue("No contact response");
        }else if(selectedOrder.getCloth().getStatus().equals("Closed")){
            statusChoiceBox.setValue("Closed");
        }else{
            statusChoiceBox.setValue("");
        }
    }

    public void setDisable(){
        statusChoiceBox.setDisable(true);
        changeBtn.setVisible(false);
        changeBtn.setDisable(true);
        createBillBtn.setVisible(false);
        createBillBtn.setDisable(true);
    }

    @FXML
    public void handleCloseBtnOnAction(){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleChangeBtnOnAction(ActionEvent event) throws IOException {
    }

    @FXML
    public void handleCreateBillBtnOnAction(ActionEvent event) throws IOException {
        selectedOrder.getCloth().setStatus("sending back to branch");
        serviceAPI.update(selectedOrder.getOrderId(),selectedOrder);
        //pdfwriter
    }

    //setter service
    public void setSelectedOrder(OrderInfo selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
}
