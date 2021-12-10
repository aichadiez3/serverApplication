package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class ServerController implements Initializable {

    ServerSocket serverSocket;
	private static Stage main_menu_stage;
    
	@FXML
    private Group serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;
    
    @FXML
    private Circle startCircle;
    
    @FXML
    private Pane closePane;

    @FXML
    private TextField passwordField;

    @FXML
    private Button exitButton;
    
    @FXML
    private ImageView startImage;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		stopButton.setDisable(true);
		stopButton.setOpacity(0.5);
		startButton.setDisable(false);
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// Visual effects of the button pressed
			
			startCircle.setFill(javafx.scene.paint.Color.GRAY.deriveColor(0, 0.1, 0.8, 0.1));
			startButton.setDisable(true);
			stopButton.setDisable(false);
			stopButton.setOpacity(1);
			startImage.setOpacity(0.3);
			
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
			
			closePane.setVisible(true);
			serverScene.setEffect(new BoxBlur(4,4,4));
			serverScene.setDisable(true);
			
		});	

	}
	
	@FXML
    void close_server(MouseEvent event) {
		if (passwordField.getText().equals("closeStresstS")) {
			
			System.out.println("Closing the server..."); 
			releaseResourcesServer(serverSocket);
			Thread.currentThread().interrupt();
			System.exit(0);
			
		} else {
			passwordField.clear();
			passwordField.setPromptText("Password is incorrect");
		}
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
