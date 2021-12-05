package server;

import java.net.URL;

import java.util.ResourceBundle;

import SQLite.SQLiteManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

	private SQLiteManager controller;
	
	@FXML
    private Pane serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;

    
    private static Stage main_menu_stage;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		controller = new SQLiteManager();
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// close the scene but the server still waiting for connections
			main_menu_stage = (Stage) serverScene.getScene().getWindow();
			main_menu_stage.setIconified(true);

			
			
		});
		
		stopButton.setOnMouseClicked((MouseEvent event) -> {
			controller.Close_connection();
			//LaunchServerApp.releaseResourcesServer(LaunchServerApp.socket,LaunchServerApp.serverSocket); //--------> Esto no funchiona
			System.exit(0);
		});
	}
    
	
	
	
	
}
