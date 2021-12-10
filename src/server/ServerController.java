package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

    ServerSocket serverSocket;
	private static Stage main_menu_stage;
    private Boolean running=true;
    
	@FXML
    private Pane serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;

    
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// SERVER CREATION
			
			serverSocket = null;
				
				try {
					serverSocket = new ServerSocket(9000);
					new Thread(new ThreadCreation(serverSocket)).start();
				
		        } catch (IOException e) {
		        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
				}
			
		});
		
		stopButton.setOnMouseClicked((MouseEvent event2) -> {
			System.out.println("Closing the server..."); 
			releaseResourcesServer(serverSocket);
			Thread.currentThread().interrupt();
			System.exit(0);
		});	

	}
	
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) serverScene.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}
	
	public static void releaseResourcesServer(ServerSocket serverSocket) {
		try {
			serverSocket.close();
	    } catch (IOException ex) {
	        Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, ex);
	    }
		System.out.println("Releasing server resources");
	}
	
	
}
