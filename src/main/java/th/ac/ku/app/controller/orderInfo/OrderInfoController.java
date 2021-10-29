package th.ac.ku.app.controller.orderInfo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.AccountManager;
import th.ac.ku.app.service.CreateBillService;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class OrderInfoController {

    @FXML private Button changeBtn, createBillBtn;
    @FXML private Label orderIdLabel, customerNameLabel, createDateLabel,
            staffNameLabel, phoneNumLabel, clothQuantityLabel,statusLabel;
    @FXML private ChoiceBox<String> statusChoiceBox;

    private ObservableList<String> statusTypeListHq = FXCollections.observableArrayList("Success", "Damaged");
    private ObservableList<String> statusTypeNotReady = FXCollections.observableArrayList("Ready to pickup");
    private ObservableList<String> statusTypeCleaned = FXCollections.observableArrayList("Ready to pickup","No contact response");
    private ObservableList<String> statusTypePicked = FXCollections.observableArrayList("Closed");
    private OrderInfo selectedOrder;
    private WashingOrderServiceAPI serviceAPI;
    private AccountManager accountManager;
    private Alert alert;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String date = LocalDateTime.now().format(formatter);

    @FXML private void initialize(){
        Platform.runLater(() -> {
            orderIdLabel.setText(Integer.toString(selectedOrder.getOrderId()));
            customerNameLabel.setText(selectedOrder.getCustomerName());
            createDateLabel.setText(selectedOrder.getOrderDate());
            staffNameLabel.setText(selectedOrder.getBranchName());
            phoneNumLabel.setText(selectedOrder.getCustomerPhone());
            clothQuantityLabel.setText(Integer.toString(selectedOrder.getCloth().getClothQuantity()));
            createBillBtn.setDisable(true);
            setOrderStatusLabel();
            if (accountManager.getCurrentBranch() != null){
                setStatusTypeListBranch();
            }
            else {
                setStatusTypeListHq();
            }
        });
    }

    public void setOrderStatusLabel() {
        if (selectedOrder.getCloth().getStatus().equals("Sending to hq")) {
            statusLabel.setText("Sending to hq");
        } else if (selectedOrder.getCloth().getStatus().equals("Success")) {
            statusLabel.setText("Success");
        } else if (selectedOrder.getCloth().getStatus().equals("Damaged")) {
            statusLabel.setText("Damaged");
        } else if (selectedOrder.getCloth().getStatus().equals("Ready to pickup")) {
            statusLabel.setText("Ready to pickup");
        } else if (selectedOrder.getCloth().getStatus().equals("No contact response")) {
            statusLabel.setText("No contact response");
        } else if (selectedOrder.getCloth().getStatus().equals("Closed")) {
            statusLabel.setText("Closed");
        } else {
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
    public void handleChangeBtnOnAction(ActionEvent event){
        if(confirmationAlertBox("Confirm to change status?").getResult().equals(ButtonType.OK)) {
            String currentStatus = statusChoiceBox.getValue();
            selectedOrder.getCloth().setStatus(currentStatus);
            if(currentStatus.equals("Closed")){
                selectedOrder.setClosedDate(date);
            }
            serviceAPI.update(selectedOrder.getOrderId(), selectedOrder);
            setOrderStatusLabel();
            if(accountManager.getCurrentBranch() != null) {
                setStatusTypeListBranch();
            }else{
                setStatusTypeListHq();
            }
        }
        Stage stage = (Stage) changeBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCreateBillBtnOnAction(ActionEvent event) throws IOException{
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        CreateBillService billService = new CreateBillService();
        billService.setSelectOrderInfo(selectedOrder);
        billService.setAccountManager(accountManager);
        billService.setStage(stage);
        billService.createPdf();
        informationAlertBox("Pdf created");
    }

    private void setStatusTypeListBranch(){
        if (selectedOrder.getCloth().getStatus().equals("Sending to hq")){
            createBillBtn.setVisible(false);
            createBillBtn.setDisable(true);
            statusChoiceBox.setDisable(true);
            changeBtn.setDisable(true);
        }
        if(selectedOrder.getCloth().getStatus().equals("Success")||
                selectedOrder.getCloth().getStatus().equals("Damaged")){
            statusChoiceBox.setDisable(false);
            createBillBtn.setVisible(false);
            createBillBtn.setDisable(true);
            statusChoiceBox.setItems(statusTypeCleaned);
        }
        if(selectedOrder.getCloth().getStatus().equals("No contact response")){
            statusChoiceBox.setDisable(false);
            createBillBtn.setVisible(false);
            createBillBtn.setDisable(true);
            statusChoiceBox.setItems(statusTypeNotReady);
        }
        if (selectedOrder.getCloth().getStatus().equals("Ready to pickup")){
            statusChoiceBox.setDisable(false);
            createBillBtn.setVisible(false);
            createBillBtn.setDisable(true);
            statusChoiceBox.setItems(statusTypePicked);
        }
    }
    private void setStatusTypeListHq(){
        statusChoiceBox.setItems(statusTypeListHq);
        if (selectedOrder.getCloth().getStatus().equals("Sending to hq")){
            createBillBtn.setVisible(true);
            createBillBtn.setDisable(true);
        }else if(selectedOrder.getCloth().getStatus().equals("Success")||
                selectedOrder.getCloth().getStatus().equals("Damaged")){
            createBillBtn.setVisible(true);
            createBillBtn.setDisable(false);
        }else{
            setDisable();
        }
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
