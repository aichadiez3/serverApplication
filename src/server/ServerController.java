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
		/*
		controller.Connect();
		controller.CreateTables();
		controller = null;
		
		// PREDETERMINED USEFUL INFORMATION FOR THE APPLICATION. NEED TO BE INSIDE THE DATABASE ONCE
		
		methods.Insert_new_insurance("Anthem");
		methods.Insert_new_insurance("Centene");
		methods.Insert_new_insurance("UnitedHealth");
		methods.Insert_new_insurance("HCSC");
		methods.Insert_new_insurance("DKV");
		methods.Insert_new_insurance("Sanitas");
		methods.Insert_new_insurance("Maphre");
		methods.Insert_new_insurance("AXA");
		methods.Insert_new_insurance("Asisa");
		methods.Insert_new_insurance("Adeslas");
		methods.Insert_new_insurance("Caser");
		methods.Insert_new_insurance("Allianz");
		methods.Insert_new_insurance("Aegon");
		methods.Insert_new_insurance("Other");
		
		methods.Insert_new_doctor("Jose Luis García", "UnitedHealth");
		methods.Insert_new_doctor("Carlos Ruíz", "Maphre");
		methods.Insert_new_doctor("Marta Martínez", "Anthem");
		methods.Insert_new_doctor("Laura Esteban", "Centene");
		methods.Insert_new_doctor("Julia Medea", "HCSC");
		methods.Insert_new_doctor("Maria José García", "DKV");
		
		*/
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// close the scene but the server still waiting for connections
			main_menu_stage = (Stage) serverScene.getScene().getWindow();
			main_menu_stage.setIconified(true);
			
			// SERVER CREATION
			
			ServerSocket serverSocket = null;
		
			while(true){
	            //This executes when we have a client
				Socket socket = null;
				try {
					serverSocket = new ServerSocket(9000);
		            while (true) {
		                //This executes when we have a patient
		                socket = serverSocket.accept();
		                new Thread(new ServerToDB(socket)).start();	
		                
		                System.out.println("Server received a socket: " + socket.getLocalAddress().toString());
		                
		                // condition receive a message from a close instruction from client application (button x, log_out..)
		                //releaseResources(socket);
		            }
		        } catch (IOException e) {
		        	Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, e);
				}
				finally {
					releaseResourcesServer(socket, serverSocket);
				}
			}
			
		});
		
		stopButton.setOnMouseClicked((MouseEvent event) -> {
			controller.Close_connection();
			//LaunchServerApp.releaseResourcesServer(LaunchServerApp.socket,LaunchServerApp.serverSocket); //--------> Esto no funchiona
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
	
	public static void releaseResourcesServer(Socket socket, ServerSocket serverSocket) {
		try {
	        socket.close();
	    } catch (IOException ex) {
	        Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, ex);
	    }
		
		try {
			serverSocket.close();
	    } catch (IOException ex) {
	        Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
	
	
}
