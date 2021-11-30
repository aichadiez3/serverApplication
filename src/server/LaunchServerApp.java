package server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


//This server can manage several clients simultaneously by using Threads

public class LaunchServerApp extends Application {

	private static Stage stage;
	
	
	public static void main(String[] args) throws IOException {
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			//BorderPane root = new BorderPane();
			Parent root = FXMLLoader.load(getClass().getResource("ServerApplicationView.fxml"));
			primaryStage.setTitle("Log in page");
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(getClass().getResource("dark-theme.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.show();
			
		} catch(IOException fatal_server_error) {
			fatal_server_error.printStackTrace();
			System.exit(0);
		}
	}
}
