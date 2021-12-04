package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteMethods;
import SQLite.SQLiteManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ServerController implements Initializable {

	
	@FXML
    private Pane serverScene;

    @FXML
    private Group startButton;

    @FXML
    private Group stopButton;

    
    private static Stage main_menu_stage;
    private SQLiteManager controller;
    private ServerSocket serverSocket;
    private Socket socket;
    private SQLiteMethods methods;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1){
		
		controller = new SQLiteManager();
		
		startButton.setOnMouseClicked((MouseEvent event) -> {
			
			// close the scene but the server still waiting for connections
			main_menu_stage = (Stage) serverScene.getScene().getWindow();
			main_menu_stage.setIconified(true);

			
			/*
			controller.Connect();
			controller.CreateTables();
			controller = null;
			
			
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


			//hagamos este server
			
			serverSocket = null;
		
			while(true){
	            //This executes when we have a client
	            socket = null;
				try {
					serverSocket = new ServerSocket(9000);
		            while (true) {
		                //This executes when we have a patient
		                socket = serverSocket.accept();
		                new Thread(new ServerToDB(socket)).start();		                
		                // condition receive a message from a close instruction from client application (button x, log_out..)
		                releaseResources(socket);
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
			releaseResourcesServer(socket,serverSocket);
			System.exit(0);
		});
	}
    
	
	
	private static void releaseResources(Socket socket) {
		try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	private static void releaseResourcesServer(Socket socket, ServerSocket serverSocket) {
		try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		try {
			serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
}
