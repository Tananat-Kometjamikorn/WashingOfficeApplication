package th.ac.ku.app.controller.hq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
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
        reportService.createReport(stage);
    }

    public String getSelectDate(){
        LocalDate selectedDate = datePicker.getValue();
        String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(formattedDate);
        return formattedDate;
    }

    public List<OrderInfo> getAllMatchedClosedOrder(){
        List<OrderInfo> allOrderInfo = serviceAPI.getAllOrderInfo();
        List<OrderInfo> matched = new ArrayList<>();
        for (OrderInfo i : allOrderInfo) {
            if (i.getClosedDate().startsWith(getSelectDate())) {
                matched.add(i);
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


    public void setServiceAPI(WashingOrderServiceAPI serviceAPI) {
        this.serviceAPI = serviceAPI;
    }
}
