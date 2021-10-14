package th.ac.ku.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunApp extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
		primaryStage.setScene(new Scene(root, 1024, 720));
		primaryStage.show();
		primaryStage.getIcons().add(new Image("/image/icon.png"));
		primaryStage.setTitle("Washing Billing System");
		primaryStage.setResizable(false);
	}


	public static void main(String[] args) {
		launch(args);
	}

}
