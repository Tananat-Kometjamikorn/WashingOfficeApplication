package th.ac.ku.app.controller.orderInfo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;

public class OrderInfoController {

    @FXML private Button changeBtn, createBillBtn;
    @FXML private Label orderIdLabel, customerNameLabel, createDateLabel,
            staffNameLabel, phoneNumLabel, clothQuantityLabel,statusLabel;
    @FXML private ChoiceBox<String> statusChoiceBox;

    private ObservableList<String> statusTypeListBranch = FXCollections.observableArrayList("Ready to pickup", "No contact response", "Closed");
    private ObservableList<String> statusTypeListHq = FXCollections.observableArrayList("Success", "Damaged");
    private OrderInfo selectedOrder;
    private WashingOrderServiceAPI serviceAPI;
    private AccountManager accountManager;

    @FXML private void initialize(){
        Platform.runLater(() -> {
            orderIdLabel.setText(Integer.toString(selectedOrder.getOrderId()));
            customerNameLabel.setText(selectedOrder.getCustomerName());
            createDateLabel.setText(selectedOrder.getOrderDate());
            staffNameLabel.setText(selectedOrder.getBranchName());
            phoneNumLabel.setText(selectedOrder.getCustomerPhone());
            clothQuantityLabel.setText(Integer.toString(selectedOrder.getCloth().getClothQuantity()));
            createBillBtn.setDisable(true);
            setOrderStatus();
            if (accountManager.getCurrentBranch() != null){
                setStatusTypeListBranch();
            }
            else {
                setStatusTypeListHq();
            }
        });
    }

    public void setOrderStatus(){
        if(selectedOrder.getCloth().getStatus().equals("Sending to hq")){
            statusLabel.setText("Sending to hq");
        }else if(selectedOrder.getCloth().getStatus().equals("Sending to branch")){
            statusLabel.setText("Sending to branch");
        }else if(selectedOrder.getCloth().getStatus().equals("Ready to pickup")){
            statusLabel.setText("Ready to pickup");
        }else if(selectedOrder.getCloth().getStatus().equals("No contact response")){
            statusLabel.setText("No contact response");
        }else if(selectedOrder.getCloth().getStatus().equals("Closed")){
            statusLabel.setText("Closed");
        }else{
            statusLabel.setText("");
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
    public void handleChangeBtnOnAction(ActionEvent event) throws IOException {
        selectedOrder.getCloth().setStatus(statusChoiceBox.getValue());
        serviceAPI.update(selectedOrder.getOrderId(),selectedOrder);
        createBillBtn.setDisable(false);
    }

    @FXML
    public void handleCreateBillBtnOnAction(ActionEvent event) throws IOException {
        //pdfwriter
    }

    private void setStatusTypeListBranch(){
        if (selectedOrder.getCloth().getStatus().equals("Sending to hq")){
            createBillBtn.setVisible(false);
            createBillBtn.setDisable(true);
            statusChoiceBox.setDisable(true);
        }
        if(selectedOrder.getCloth().getStatus().equals("Sending to branch")){
            statusChoiceBox.setDisable(false);
            statusChoiceBox.setItems(statusTypeListBranch);
        }
    }
    private void setStatusTypeListHq(){
        statusChoiceBox.setItems(statusTypeListHq);
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
