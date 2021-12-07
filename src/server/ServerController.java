package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import SQLite.SQLiteMethods;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

	private SQLiteManager controller;
    private SQLiteMethods methods;
    ServerSocket serverSocket;
	
	@FXML
    private Pane serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;

    
    private static Stage main_menu_stage;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// close the scene but the server still waiting for connections
			main_menu_stage = (Stage) serverScene.getScene().getWindow();
			main_menu_stage.setIconified(true);
			
			// SERVER CREATION
			
			serverSocket = null;
		
			while(true){
	            //This executes when we have a client
				Socket socket = null;
				try {
					serverSocket = new ServerSocket(9000);
		            while (true) {
		                //This executes when we have a patient
		                socket = serverSocket.accept();
		                new Thread(new ServerToDB(socket)).start();	
		                
		                System.out.println("Server received a socket: " + socket.getLocalSocketAddress());
		                
		                // condition receive a message from a close instruction from client application (button x, log_out..)
		                //releaseResources(socket);
		            }
		        } catch (IOException e) {
		        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
				}
				finally {
					//releaseResourcesServer(serverSocket);
					System.out.println("Client finished.");
				}
			}
			
		});
		
		stopButton.setOnMouseClicked((MouseEvent event) -> {
			controller.Close_connection(); 
			releaseResourcesServer(serverSocket);
			System.exit(0);
		});
	
}
    
	
	private static void releaseResources(Socket socket) {
		try {
	        socket.close();
	    } catch (IOException ex) {
	        Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	public static void releaseResourcesServer(ServerSocket serverSocket) {
		try {
			serverSocket.close();
	    } catch (IOException ex) {
	        Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	
}
