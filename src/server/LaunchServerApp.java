package server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import SQLite.SQLiteMethods;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


//This server can manage several clients simultaneously by using Threads

public class LaunchServerApp extends Application {

	private static Stage stage;
    private SQLiteMethods methods;
    private ServerSocket serverSocket;
    private Socket socket;
    
	
	
	public static void main(String[] args) throws IOException {
		
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			
			Parent root = FXMLLoader.load(getClass().getResource("ServerApplicationView.fxml"));
			primaryStage.setTitle("Log in page");
			Scene scene = new Scene(root);
			scene.getStylesheets().addAll(getClass().getResource("dark-theme.css").toExternalForm());
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setScene(scene);
			stage = primaryStage;
			primaryStage.show();
			
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
			
			// SERVER CREATION
			
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
		        	Logger.getLogger(LaunchServerApp.class.getName()).log(Level.SEVERE, null, e);
				}
				finally {
					releaseResourcesServer(socket, serverSocket);
				}
			}
			
		} catch(IOException fatal_server_error) {
			fatal_server_error.printStackTrace();
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
