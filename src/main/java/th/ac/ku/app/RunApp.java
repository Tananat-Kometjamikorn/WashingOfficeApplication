package th.ac.ku.app;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class RunApp extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/login.fxml")));
		Scene scene = new Scene(root, 1024, 720);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.getIcons().add(new Image("/image/icon.png"));
		primaryStage.setTitle("Washing Billing System");
		primaryStage.setResizable(false);

	}


	public static void main(String[] args) {
		launch(args);
	}

}
