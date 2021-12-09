package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

	private SQLiteManager controller;
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
		
			while(running == true){
	            //This executes when we have a client
				Socket socket = null;
				try {
					serverSocket = new ServerSocket(9000);
		            while (running == true) {
		                //This executes when we have a patient
		                socket = serverSocket.accept();
		                new Thread(new ServerToDB(socket)).start();	
		                
		                System.out.println("Server received a socket: " + socket.getLocalSocketAddress());
		                
		                // condition receive a message from a close instruction from client application (button x, log_out..)
		                //releaseResources(socket);
		                /*
		                System.out.println(running);
		                if (stopButton.isPressed()) {
		                	running=false;
		                	System.out.println(stopButton.isPressed());
							controller.Close_connection(); 
							releaseResourcesServer(serverSocket);
							System.exit(0);
		                }
		                */
		            }
		        } catch (IOException e) {
		        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
				}
				finally {
					//releaseResourcesServer(serverSocket);
					System.out.println("Client finished.");
				}
				
				/*
				stopButton.setOnMouseClicked((MouseEvent event2) -> {
					controller.Close_connection(); 
					releaseResourcesServer(serverSocket);
					System.exit(0);
				});
				*/
			}
			
		});
		
	
}
    
	@FXML
	void minimize_window(MouseEvent event) {
		main_menu_stage = (Stage) serverScene.getScene().getWindow();
		main_menu_stage.setIconified(true);
	}
	
	@FXML
	void close_server(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			controller.Close_connection(); 
			releaseResourcesServer(serverSocket);
			running=false;
			System.exit(0);
		}	
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
