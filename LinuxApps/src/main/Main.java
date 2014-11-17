package main;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	public static Stage stage;
	
	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("static-access")
	@Override
	public void start(Stage stage) throws IOException {
		this.stage = stage;
		stage.initStyle(StageStyle.TRANSPARENT);		
		Parent root = FXMLLoader
				.load(getClass().getResource("/view/Main.fxml"));
		Scene scene = new Scene(root);
		stage.setScene(scene);
		scene.setFill(Color.TRANSPARENT);
		stage.show();
	}

}
