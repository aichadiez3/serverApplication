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
		
			//while(running){
	            //This executes when we have a client
				
				try {
					serverSocket = new ServerSocket(9000);
					new Thread(new ThreadCreation(serverSocket)).start();
				/*
				 * while (true) {
				 * 
				 * if (stopButton.isPressed()==true) { System.out.println("Close server.");
				 * releaseResourcesServer(serverSocket); System.exit(0); } else { //This
				 * executes when we have a patient Socket socket = serverSocket.accept(); new
				 * Thread(new ServerToDB(socket)).start();
				 * 
				 * //System.out.println("Server received a socket: " +
				 * socket.getLocalSocketAddress()); }
				 * 
				 * }
				 */
		        } catch (IOException e) {
		        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
				}
//				finally {
//					System.out.println("salí");
//					releaseResourcesServer(serverSocket);
//				}
			
			//}
			
		});
		
		stopButton.setOnMouseClicked((MouseEvent event2) -> {
			System.out.println("He pillao cacho"); 
			releaseResourcesServer(serverSocket);
			Thread.currentThread().interrupt();
			System.exit(0);
		});	

}
	
	
	
	/*
   @FXML
   void close_server(MouseEvent event) {
	   running=false;
		System.out.println("He pillao cacho");
		releaseResourcesServer(serverSocket);
		System.exit(0);
   }
   */
	
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
		System.out.println("Release serverController");
	}
	
	
}
