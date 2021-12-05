package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import SQLite.SQLiteMethods;
import pojos.EcgTest;
import pojos.EdaTest;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.PhysicalTest;
import pojos.PsychoTest;
import pojos.User;

public class ServerToDB implements Runnable{

	Socket socket;
	SQLiteMethods methods;
	DataInputStream dataInputStream;
	DataOutputStream dataOutputStream;
	
	public ServerToDB(Socket socket) {
		super();
		this.socket = socket;
	}
	
	@Override
	public void run() {
		//This executes when we have a client
        String instruction;
        User user = null;
        MedicalRecord record = null;
        EcgTest ecg = null;
        EdaTest eda = null;
        PsychoTest psycho = null;
        PhysicalTest physical = null;
        Patient patient = null;
        Integer userId, patientId;
        Integer medRecordId;
        Integer ecgId, bitalinoId;;
        Integer edaId, queriesId, physicalId;
        
        
        SQLiteManager manager = new SQLiteManager();
        manager.Connect();
        manager.CreateTables();
        methods = manager.getMethods();
            
            //while true, lee el mensaje y hacemos los m�todos que nos pida el mensaje
            	
            try {
            	dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            	
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

            	
        		boolean stopClient = false;     // A ESTO HAY QUE DARLE OTRA VUELTA
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
                        userId = methods.Insert_new_user(user_name, password, email);
                		dataOutputStream.writeUTF(userId.toString());

                    }
                    if (parameters[0].equals("new_patient")) {
                    	System.out.println("He leido la instruccion");
                    	Integer user_id = Integer.parseInt(parameters[1]);
                    	String name = parameters[2];
                    	String surname = parameters[3];
                    	methods.Insert_new_patient(user_id, name, surname);
                    }


                    if (parameters[0].equals("new_doctor")) {
                    	String name = parameters[1];
                    	String telephone = parameters[2];
                    	methods.Insert_new_doctor(name, telephone);
                    }

                    
                    if (parameters[0].equals("new_medical_record")) {
                    	Integer test_id = Integer.parseInt(parameters[1]); // -----------> UNUSED
                		Date record_date = null;
						try {
							record_date = new SimpleDateFormat("dd/MM/yyyy").parse(parameters[2]);
						} catch (ParseException e) {
							e.printStackTrace();
						}
                		Integer reference_number = Integer.parseInt(parameters[3]);
                		Integer bitalino_test_id = Integer.parseInt(parameters[4]);
                		medRecordId = methods.Insert_new_medical_record((java.sql.Date) record_date, reference_number, bitalino_test_id);
                		dataOutputStream.writeUTF(medRecordId.toString());
                    }
                    
                    if (parameters[0].equals("new_bitalino_test")) {
                		Integer test_id = Integer.parseInt(parameters[1]);
                        bitalinoId = methods.Insert_new_bitalino_test();
                		dataOutputStream.writeUTF(bitalinoId.toString());
                    }
                    
                    if (parameters[0].equals("new_ecg")) {
                		Integer test_id = Integer.parseInt(parameters[2]);
                		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[1])); // ----------> ESTO TIENE QUE CAMBIAR
						LinkedList<Integer> ecg_values = linkedList;
                        ecgId = methods.Insert_new_ecg(ecg_values, test_id);
                		dataOutputStream.writeUTF(ecgId.toString());
                    }
                    if (parameters[0].equals("new_eda")) {
                		//Integer eda_id = Integer.parseInt(parameters[1]);
                		Integer test_id = Integer.parseInt(parameters[2]);
                		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[1])); // ----------> ESTO TIENE QUE CAMBIAR
						LinkedList<Integer> eda_values = linkedList;
                        edaId = methods.Insert_new_eda(eda_values, test_id);
                		dataOutputStream.writeUTF(edaId.toString());
                    }
                    if (parameters[0].equals("new_psycho")) {
                		//Integer queries_id = Integer.parseInt(parameters[1]);
                		Integer medicalRecord_id = Integer.parseInt(parameters[3]);
                		LinkedList linkedList = new LinkedList(Arrays.asList(parameters[1])); // ----------> ESTO NO ME DA FE
						LinkedList<Boolean> positive_res = linkedList;
						LinkedList linkedList2 = new LinkedList(Arrays.asList(parameters[2])); // ----------> ESTO NO ME DA FE
						LinkedList<Boolean> negative_res = linkedList2;
                        queriesId = methods.Insert_new_psycho_test(positive_res, negative_res, medicalRecord_id);
                        dataOutputStream.writeUTF(queriesId.toString());
                    }
                    if (parameters[0].equals("new_physical")) {
                		//Integer test_id = Integer.parseInt(parameters[1]);
                		Integer saturation = Integer.parseInt(parameters[1]);
                		Integer pulse = Integer.parseInt(parameters[2]);
                		Integer breathingRate = Integer.parseInt(parameters[3]);
                		Integer medicalRecord_id = Integer.parseInt(parameters[6]);
                        physicalId = methods.Insert_new_physical_test(saturation, pulse, breathingRate, medicalRecord_id);
                        dataOutputStream.writeUTF(physicalId.toString());
                    }
                    if (parameters[0].equals("change_user_info")) {
                    	String password = parameters[1];
                    	String email = parameters[2];
                    	Integer user_id = Integer.parseInt(parameters[3]);
                        methods.Update_user_info(password, email, user_id);
                    }
                    if (parameters[0].equals("update_patient")) {
                		Integer user_id = Integer.parseInt(parameters[1]);
                		patientId = methods.Search_stored_patient_by_user_id(user_id);
                		String name = parameters[1];
                		String surname = parameters[2];
                		Date birth_date = null;
						try {
							birth_date = new SimpleDateFormat("dd/MM/yyyy").parse(parameters[3]);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Integer age = Integer.parseInt(parameters[4]);
						Integer height = Integer.parseInt(parameters[5]);
						Integer weight = Integer.parseInt(parameters[6]);
						String gender = parameters[7];
						Integer telephone = Integer.parseInt(parameters[8]);
						Integer insurance_id = Integer.parseInt(parameters[9]);
						//HAY QUE CAMBIAR DE LocalDate A Date
                        methods.Update_patient_info(patientId, name, surname, (java.sql.Date) birth_date, age, height, weight, gender, telephone, insurance_id);
                    }
                    if (parameters[0].equals("search_patient_by_id")) {
                    	Integer patient_id = Integer.parseInt(parameters[1]);
                        methods.Search_stored_patient_by_id(patient_id);
                    }
                    if (parameters[0].equals("search_patient_by_user_id")) {
                    	Integer user_id = Integer.parseInt(parameters[1]);
                        patientId = methods.Search_stored_patient_by_user_id(user_id);
                        dataOutputStream.writeUTF(patientId.toString());
                    }
                    if (parameters[0].equals("list_all_medical_records")) {
                        methods.List_all_medical_records();
                    }
                    if (parameters[0].equals("search_symptom_by_id")) {
                    	Integer symptom_id = Integer.parseInt(parameters[1]);
                        methods.Search_symptom_by_id(symptom_id);
                    }
                    if (parameters[0].equals("search_user_by_userName")) {
                    	String user_name = parameters[1];
                        userId = methods.Search_stored_user_by_userName(user_name);
                        dataOutputStream.writeUTF(userId.toString());
                    }
                    if (parameters[0].equals("search_insurance_by_name")) {
                    	String insurance_name = parameters[1];
                        methods.Search_insurance_by_name(insurance_name);
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
                    if (parameters[0].equals("search_associated_ecg")) {
                    	Integer bitalino_id = Integer.parseInt(parameters[1]);
                    	methods.Search_associated_ecg(bitalino_id);
                    }
                    if (parameters[0].equals("search_associated_eda")) {
                    	Integer bitalino_id = Integer.parseInt(parameters[1]);
                    	methods.Search_associated_eda(bitalino_id);
                    }
                }
            
            }  catch (IOException ex) {
            	manager.Close_connection();
                Logger.getLogger(ServerToDB.class.getName()).log(Level.SEVERE, null, ex);
                 
            } 
            /*
            finally {
                releaseResourcesClient(socket, dataOutputStream); 
            }
           	*/
	}
	
	private static void releaseResourcesClient(Socket socket, DataOutputStream dataOutputStream) {
		try {
			dataOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
	
	
}