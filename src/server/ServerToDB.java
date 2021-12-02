package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
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
            Integer userId;
            

            try {
            	serverSocket = new ServerSocket(9000);
                socket = serverSocket.accept();
                methods = new SQLiteMethods();
                
                //while true, lee el mensaje y hacemos los m�todos que nos pida el mensaje
                	
                try {
                	DataInputStream dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                	
                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                	
            		boolean stopClient = false;
                    while (!stopClient) {
                    	instruction = dataInputStream.readUTF();
                        //We read until is finished the connection or character 'x'
                        if (instruction.equals("end_client")) {
                            //System.out.println("Client character reception finished");
                            stopClient = true;
                        }
                        
                        //todo lo que he hecho, a�cha habia puesto esto mas adelante, donde comentare *!*, no se donde habr� que colocarlo para que funcione
                        String[] parameters = instruction.split(",");
                        if (parameters[0].equals("new_user")) {
                        	String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                            methods.Insert_new_user(user_name, password, email);
                        }
                        if (parameters[0].equals("new_patient")) {
                    		String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                        	user = new User(user_name, password, email);
                        	Integer user_id = user.getUserId();
                        	String name = parameters[4];
                        	String surname = parameters[5];
                        	LocalDate date = LocalDate.parse(parameters[6]);
                        	Integer age = Integer.parseInt(parameters[7]);
                        	Integer height = Integer.parseInt(parameters[8]);
                        	Integer weight = Integer.parseInt(parameters[9]);
                        	String gender = parameters[10];
                        	Integer telephone = Integer.parseInt(parameters[11]);
                        	Integer insurance_id = Integer.parseInt(parameters[12]);
                        	String company_name = parameters[13];
                        	Insurance_company company = new Insurance_company(insurance_id, company_name);
                            methods.Insert_new_patient(user_id, name, surname);
                        }


                        if (parameters[0].equals("new_doctor")) {
                    		String user_name = parameters[1];
                        	String password = parameters[2];
                        	String email = parameters[3];
                        	user = new User(user_name, password, email);
                        	Integer user_id = user.getUserId();
                        	String name = parameters[4];
                        	methods.Insert_new_doctor(user_id, name);
                        }

                        
                        if (parameters[0].equals("new_medical_record")) {
                    		Integer record_id = Integer.parseInt(parameters[1]);
                    		Date record_date = null;
							try {
								record_date = new SimpleDateFormat("dd/MM/yyyy").parse(parameters[2]);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                    		Integer reference_number = Integer.parseInt(parameters[3]);
                    		Integer bitalino_test_id = Integer.parseInt(parameters[4]);
                            methods.Insert_new_medical_record((java.sql.Date) record_date, reference_number, bitalino_test_id);
                        }
                        if (parameters[0].equals("new_ecg")) {
                    		Integer ecg_id = Integer.parseInt(parameters[1]);
                    		Integer test_id = Integer.parseInt(parameters[2]);
                    		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[3]));
							LinkedList<Integer> ecg_values = linkedList;
                            methods.Insert_new_ecg(ecg_values, test_id);
                        }
                        if (parameters[0].equals("new_eda")) {
                    		Integer eda_id = Integer.parseInt(parameters[1]);
                    		Integer test_id = Integer.parseInt(parameters[2]);
                    		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[3]));
							LinkedList<Integer> eda_values = linkedList;
                            methods.Insert_new_eda(eda_values, test_id);
                        }
                        if (parameters[0].equals("new_psycho")) {
                    		Integer queries_id = Integer.parseInt(parameters[1]);
                    		Integer medicalRecord_id = Integer.parseInt(parameters[2]);
                    		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[3]));
							LinkedList<Boolean> positive_res = linkedList;
							LinkedList linkedList2 = new LinkedList(Arrays.asList(parameters[4]));
							LinkedList<Boolean> negative_res = linkedList2;
                            methods.Insert_new_psycho_test(positive_res, negative_res, medicalRecord_id);
                        }
                        if (parameters[0].equals("new_physical")) {
                    		Integer test_id = Integer.parseInt(parameters[1]);
                    		Integer saturation = Integer.parseInt(parameters[2]);
                    		Integer pulse = Integer.parseInt(parameters[3]);
                    		Integer breathingRate = Integer.parseInt(parameters[4]);
                    		Integer medicalRecord_id = Integer.parseInt(parameters[5]);
                            methods.Insert_new_physical_test(saturation, pulse, breathingRate, medicalRecord_id);
                        }
                        if (parameters[0].equals("change_password")) {
                        	String password = parameters[1];
                        	Integer user_id = Integer.parseInt(parameters[2]);
                            methods.Change_password(password, user_id);
                        }
                        if (parameters[0].equals("update_patient")) {
                    		Integer user_id = Integer.parseInt(parameters[1]);
                            methods.Update_patient_info(user_id);
                        }
                        if (parameters[0].equals("search_patient_by_id")) {
                        	int patient_id = Integer.parseInt(parameters[1]);
                            methods.Search_stored_patient_by_id(patient_id);
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
                        if (parameters[0].equals("search_user_by_userName")) {
                        	String user_name = parameters[1];
                            userId = methods.Search_stored_user_by_userName(user_name);
                            dataOutputStream.writeUTF(userId.toString());
                            
                        }
                        if (parameters[0].equals("search_record_by_date_ascendent")) {
                            methods.Search_stored_record_by_date_ascendent();
                        }
                        if (parameters[0].equals("search_record_by_date_descendent")) {
                            methods.Search_stored_record_by_date_descendent();
                        }
                        if (parameters[0].equals("search_all_symptoms_from_record")) {
                        	int record_id = Integer.parseInt(parameters[1]);
                            methods.Search_all_symptoms_from_record(record_id);
                        }
                        if (parameters[0].equals("list_users")) {
                            methods.List_all_users();
                        }
                        
                    }
                
                }  catch (IOException ex) {
                    Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    releaseResourcesClient(inputStream, socket);
                }
                
                //aqui a�cha es donde decia de poner lo que yo he puesto donde esta el *!*, yo no se donde ponerlo, me acabo de dar cuenta
                                
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