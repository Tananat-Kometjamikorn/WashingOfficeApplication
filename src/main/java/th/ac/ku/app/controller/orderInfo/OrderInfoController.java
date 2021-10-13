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

import java.io.IOException;

public class OrderInfoController {

    @FXML private Button closeBtn, changeBtn, createBillBtn;
    @FXML private Label orderIdLabel, customerNameLabel, createDateLabel, staffNameLabel, phoneNumLabel, clothQuantityLabel;
    @FXML private ChoiceBox statusChoiceBox;

    private ObservableList<String> statusTypeList = FXCollections.observableArrayList("ส่งไปนู้น", "ส่งกลับมา", "ส่งกลับมา", "อะไรไม่รู้");

    @FXML private void initialize(){
        Platform.runLater(new Runnable(){
            @Override
            public void run(){
            }
        });
        statusChoiceBox.setItems(statusTypeList);
        orderIdLabel.setText("1");
        customerNameLabel.setText("2");
        createDateLabel.setText("3");
        staffNameLabel.setText("4");
        phoneNumLabel.setText("5");
        clothQuantityLabel.setText("6");

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
    }
}
