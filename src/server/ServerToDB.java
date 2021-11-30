package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteMethods;
import pojos.EcgTest;
import pojos.EdaTest;
import pojos.Insurance_company;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.PhysicalTest;
import pojos.PsychoTest;
import pojos.User;

public class ServerToDB {

	private static ServerSocket serverSocket;
	
	public static void main(String args[]) throws IOException {
		
		Socket socket = null;
            
            //This executes when we have a client
        	InputStream inputStream = null;
    		ObjectOutputStream objectOutputStream = null;
            byte[] byteRead;
            String instruction;
            SQLiteMethods methods;
            User user = null;
            MedicalRecord record = null;
            EcgTest ecg = null;
            EdaTest eda = null;
            PsychoTest psycho = null;
            PhysicalTest physical = null;
            Patient patient = null;

            try {
            	serverSocket = new ServerSocket(9000);
                socket = serverSocket.accept();
                methods = new SQLiteMethods();
                
                //while true, lee el mensaje y hacemos los métodos que nos pida el mensaje
                	
                try {
                	DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                	ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                	
        			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                	
                	// inputStream = socket.getInputStream(); 
            		boolean stopClient = false;
                    while (!stopClient) {
                    	//instruction = dataInputStream.readLine();
                    	instruction = dataInputStream.readUTF();
                        //byteRead = dataInputStream.readLine();
                        //We read until is finished the connection or character 'x'
                        if (instruction.equals("end_client")) {
                            //System.out.println("Client character reception finished");
                            stopClient = true;
                        }
                        
                        //todo lo que he hecho, aïcha habia puesto esto mas adelante, donde comentare **!**, no se donde habrá que colocarlo para que funcione
                        String[] parameters = instruction.split(",");
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        if (parameters[0].equals("new_patient")) {
                        	try {
								user = (User) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_patient(user);
                        }
                        if (parameters[0].equals("new_doctor")) {
                        	try {
								user = (User) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_doctor(user);
                        }
                        if (parameters[0].equals("new_medical_record")) {
                        	try {
								record = (MedicalRecord) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_medical_record(record);
                        }
                        if (parameters[0].equals("new_ecg")) {
                        	try {
                        		ecg = (EcgTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_ecg(ecg);
                        }
                        if (parameters[0].equals("new_eda")) {
                        	try {
                        		eda = (EdaTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_eda(eda);
                        }
                        if (parameters[0].equals("new_psycho")) {
                        	try {
                        		psycho = (PsychoTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_psycho_test(psycho);
                        }
                        if (parameters[0].equals("new_physical")) {
                        	try {
                        		physical = (PhysicalTest) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Insert_new_physical_test(physical);
                        }
                        if (parameters[0].equals("change_password")) {
                        	String password = parameters[1];
                        	int user_id = Integer.parseInt(parameters[2]);
                            methods.Change_password(password, user_id);
                        }
                        if (parameters[0].equals("update_patient")) {
                        	try {
                        		patient = (Patient) objectInputStream.readObject();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

                            methods.Update_patient_info(patient);
                        }
                        if (parameters[0].equals("search_record_by_id")) {
                        	int record_id = Integer.parseInt(parameters[1]);
                            methods.Search_stored_record_by_id(record_id);
                        }
                        if (parameters[0].equals("search_record_by_test")) {
                        	int test_id = Integer.parseInt(parameters[1]);
                            methods.Search_stored_record_by_test(test_id);
                        }
                        if (parameters[0].equals("search_symptom_by_id")) {
                        	int symptom_id = Integer.parseInt(parameters[1]);
                            methods.Search_symptom_by_id(symptom_id);
                        }
                        if (parameters[0].equals("search_record_by_date_ascendent")) {
                            methods.Search_stored_record_by_date_ascendent();
                        }
                        if (parameters[0].equals("search_record_by_date_descendent")) {
                            methods.Search_stored_record_by_date_descendent();
                        }
                        if (parameters[0].equals("search_symptoms_from_record")) {
                        	int record_id = Integer.parseInt(parameters[1]);
                            methods.Search_all_symptoms_from_record(record_id);
                        }
                        if (parameters[0].equals("list_users")) {
                            methods.List_all_users();
                        }
                        if (parameters[0].equals("search_insurance_by_name")) {
                        	String name = parameters[1];
                        	Insurance_company insurance = null;
                            insurance = methods.Search_insurance_by_name(name);
                			objectOutputStream.writeObject(insurance);
                        }
                    }
                
                }  catch (IOException ex) {
                    Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    releaseResourcesClient(inputStream, socket);
                }
                
                //aqui aïcha es donde decia de poner lo que yo he puesto donde esta el **!**, yo no se donde ponerlo, me acabo de dar cuenta
                
                /*byte[] bytes = String.valueOf(inputStream.read()).getBytes();
                
                String message = new String(bytes, StandardCharsets.UTF_8);
                
                if(message.equals("insert user")) {
                	//SQLiteMethods.Insert_new_user(); // Parámetros se tienen que introducir a través de los sockets desde cliente 
                										//-> son los datos q envía el cliente al server, y el server al manejador de db
                	
                }*/
                  
                
            } catch (IOException e) {
            	e.printStackTrace();
            	releaseResourcesClient(inputStream, socket);
                System.out.println("\n\n\nClient finished");
            } finally {
            	releaseResourcesServer(socket, serverSocket);
            	
            }
        
     
		
	}
	
	private static void releaseResourcesClient(InputStream inputStream, Socket socket) {
		try {
            inputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
	private static void releaseResourcesServer(Socket socket, ServerSocket serverSocket) {
		try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
		
		try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
}
