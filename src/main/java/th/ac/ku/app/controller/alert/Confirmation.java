package th.ac.ku.app.controller.alert;


import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert;
import javafx.stage.StageStyle;

public class Confirmation {
    public void comfirmBox(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, "THIS USERNAME ALREADY EXISTED", ButtonType.OK);
        alert.setTitle("ERROR");
        alert.initStyle(StageStyle.UTILITY);
        alert.setHeaderText(null);
        alert.show();
    }
}
