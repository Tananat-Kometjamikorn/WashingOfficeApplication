package th.ac.ku.app.controller.hq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import th.ac.ku.app.models.OrderInfo;
import th.ac.ku.app.service.CreateReportService;
import th.ac.ku.app.service.WashingOrderServiceAPI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class printReportController {
    @FXML private DatePicker datePicker;
    @FXML private Button printPdfBtn;
    private Alert alert;

    private WashingOrderServiceAPI serviceAPI;

    @FXML private void initialize(){
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    public void handlePrintPdfBtnOnAction(ActionEvent event) throws IOException {
        Button b = (Button) event.getSource();
        Stage stage = (Stage) b.getScene().getWindow();
        CreateReportService reportService = new CreateReportService();
        reportService.setDate(getSelectDate());
        reportService.setAllOrderQuantity(getAllOrderQuantity(getAllMatchedClosedOrder()));
        reportService.setAllClothQuantity(getAllClothQuantity(getAllMatchedClosedOrder()));
        reportService.setAllIncome(getAllIncome(getAllMatchedClosedOrder()));
        reportService.setAllSuccessCleaned(getAllSuccessCleaned(getAllMatchedClosedOrder()));
        reportService.setAllClothDamaged(getAllClothDamaged(getAllMatchedClosedOrder()));
        reportService.setSuccessOrderInfoList(getAllMatchedSuccessClosedOrder());
        reportService.setDamagedOrderInfoList(getAllMatchedDamagedClosedOrder());
        reportService.createReport(stage);
        informationAlertBox("Pdf created");
    }

    public String getSelectDate(){
        LocalDate selectedDate = datePicker.getValue();
        String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(formattedDate);
        return formattedDate;
    }

    public List<OrderInfo> getAllMatchedClosedOrder() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> matched = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getClosedDate() != null) {
                if (i.getClosedDate().startsWith(getSelectDate())) {
                    matched.add(i);
                }
            }
        }
            return matched;
        }

    public List<OrderInfo> getAllMatchedSuccessClosedOrder() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> matched = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getClosedDate() != null) {
                if (i.getClosedDate().startsWith(getSelectDate()) &&
                        i.getOrderBill().getCleanStatus().equals("Success")) {
                    matched.add(i);
                }
            }
        }
        return matched;
    }

    public List<OrderInfo> getAllMatchedDamagedClosedOrder() {
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> matched = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getClosedDate() != null) {
                if (i.getClosedDate().startsWith(getSelectDate()) &&
                        i.getOrderBill().getCleanStatus().equals("Damaged")) {
                    matched.add(i);
                }
            }
        }
        return matched;
    }


    public int getAllOrderQuantity(List<OrderInfo> matched){
        int q = 0;
        for (OrderInfo i : matched){
            q += 1;
        }
        return q;
    }
    public int getAllClothQuantity(List<OrderInfo> matched){
        int q = 0;
        for (OrderInfo i : matched){
            q += i.getCloth().getClothQuantity();
        }
        return q;
    }
    public int getAllIncome(List<OrderInfo> matched){
        int q = 0;
        for (OrderInfo i : matched){
            q += i.getOrderBill().getCost();
        }
        return q;
    }
    public int getAllSuccessCleaned(List<OrderInfo> matched){
        int q = 0;
        for (OrderInfo i : matched){
            if(i.getOrderBill().getCleanStatus().equals("Success")){
                q += 1;
            }
        }
        return q;
    }
    public int getAllClothDamaged(List<OrderInfo> matched){
        int q = 0;
        for (OrderInfo i : matched){
            if(i.getOrderBill().getCleanStatus().equals("Damaged")){
                q += 1;
            }
        }
        return q;
    }
    public void informationAlertBox(String message){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initStyle(StageStyle.UTILITY);
        alert.setTitle("INFORMATION");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }
}
