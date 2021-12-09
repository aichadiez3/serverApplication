package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import SQLite.SQLiteManager;
import SQLite.SQLiteMethods;
import pojos.Insurance_company;
import pojos.MedicalRecord;
import pojos.Patient;
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
        Patient patient = null;
        Integer userId, patientId;
        Integer medRecordId;
        Integer ecgId, bitalinoId;
        Integer edaId, queriesId, physicalId;
        
        
        SQLiteManager manager = new SQLiteManager();
        manager.Connect();
        Boolean tables = manager.CreateTables();
        methods = manager.getMethods();
        if(tables==true) {
        	methods.Insert_default_elements_toDB();
        }
        
            //while true, lee el mensaje y hacemos los m�todos que nos pida el mensaje
            	
            try {
            	dataInputStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            	
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

            	
        		boolean stopClient = false;     
                while (!stopClient) {
                	instruction = dataInputStream.readUTF();
                    //We read until is finished the connection
                    if (instruction.equals("end_client")) {
                        //System.out.println("Client finished");
                        stopClient = true;
                    }
                    
                    String[] parameters = instruction.split(",");
                    if (parameters[0].equals("new_user")) {
                    	String user_name = parameters[1];
                    	String password = parameters[2];
                    	String email = parameters[3];
                        userId = methods.Insert_new_user(user_name, password, email);
                		dataOutputStream.writeUTF(userId.toString());

                    }
                    if (parameters[0].equals("new_patient")) {
                    	Integer user_id = Integer.parseInt(parameters[1]);
                    	String name = parameters[2];
                    	String surname = parameters[3];
                    	methods.Insert_new_patient(user_id, name, surname);
                    }
                    if (parameters[0].equals("new_doctor")) {
                    	String name = parameters[1];
                    	String telephone = parameters[2];
                    	Integer insurance_id = Integer.parseInt(parameters[3]);
                    	methods.Insert_new_doctor(name, telephone,insurance_id);
                    }
                    if (parameters[0].equals("new_medical_record")) {
                    	String record_date = parameters[1];
                		Integer reference_number = Integer.parseInt(parameters[2]);
                		patientId =userId = Integer.parseInt(parameters[3]);
                		medRecordId = methods.Insert_new_medical_record(record_date, reference_number, patientId);
                		//dataOutputStream.writeUTF("MEDICAL RECORD ID");
                		dataOutputStream.writeUTF(String.valueOf(medRecordId));
                    }
                    
                    if (parameters[0].equals("new_bitalino_test")) {
                        bitalinoId = methods.Insert_new_bitalino_test();
                        //dataOutputStream.writeUTF("BITALINO");
                		dataOutputStream.writeUTF(String.valueOf(bitalinoId));
                    }
                    
                    if (parameters[0].equals("new_ecg")) {
                		String ecg_values = parameters[1];
                		Integer test_id = Integer.parseInt(parameters[2]);
                        ecgId = methods.Insert_new_ecg(ecg_values, test_id);
                        //dataOutputStream.writeUTF("ECGID");
                		//dataOutputStream.writeUTF(String.valueOf(ecgId));
                    }
                    if (parameters[0].equals("new_eda")) {
                		Integer test_id = Integer.parseInt(parameters[2]);
						String eda_values = parameters[1];
                        edaId = methods.Insert_new_eda(eda_values, test_id);
                        //dataOutputStream.writeUTF("EDAID");
                		//dataOutputStream.writeUTF(String.valueOf(edaId));
                    }
                    if (parameters[0].equals("new_psycho")) {
                    	String[] psychotest = instruction.split("]");
                    	String temp = psychotest[0].replace("new_psycho,[","");
                    	//System.out.println(temp);
                    	String temp2 = psychotest[1].replace("[","");
                    	//System.out.println(temp2);
                    	String temp3 = psychotest[2].replace("[","");
                    	//System.out.println(temp3);
                    	String[] positives = temp.split(",");
                    	String[] negatives = temp2.split(",");
                    	String[] symptoms = temp3.split(",");
                		Integer medicalRecord_id = Integer.parseInt(parameters[parameters.length-1]);
                		LinkedList<String> positive_res = new LinkedList<String>(Arrays.asList(positives));
						LinkedList<String> negative_res = new LinkedList<String>(Arrays.asList(negatives)); 
						LinkedList<String> symptoms_res = new LinkedList<String>(Arrays.asList(symptoms)); 
                        queriesId = methods.Insert_new_psycho_test(positive_res, negative_res, symptoms_res, medicalRecord_id);
                        //dataOutputStream.writeUTF("QUERIESID");
                        //dataOutputStream.writeUTF(String.valueOf(queriesId));
                    }
                    if (parameters[0].equals("new_physical")) {
                		Integer saturation = Integer.parseInt(parameters[1]);
                		Integer pulse = Integer.parseInt(parameters[2]);
                		Integer breathingRate = Integer.parseInt(parameters[3]);
                		Integer medicalRecord_id = Integer.parseInt(parameters[4]);
                        physicalId = methods.Insert_new_physical_test(saturation, pulse, breathingRate, medicalRecord_id);
                        //dataOutputStream.writeUTF(String.valueOf(physicalId));
                        //dataOutputStream.writeUTF("PHYSICALID");
                    }
                    if (parameters[0].equals("change_user_info")) {
                    	String password = parameters[1];
                    	String email = parameters[2];
                    	Integer user_id = Integer.parseInt(parameters[3]);
                        boolean check = methods.Update_user_info(password, email, user_id);
                        dataOutputStream.writeUTF(String.valueOf(check));
                    }
                    if (parameters[0].equals("update_patient")) {
                		Integer user_id = Integer.parseInt(parameters[1]);
                		patientId = methods.Search_stored_patient_by_user_id(user_id);
                		String birth_date =  parameters[2];
						Integer height = Integer.parseInt(parameters[3]);
						Integer weight = Integer.parseInt(parameters[4]);
						String gender = parameters[5];
						Integer telephone = Integer.parseInt(parameters[6]);
						Integer insurance_id = Integer.parseInt(parameters[7]);
                        methods.Update_patient_info(patientId, birth_date, height, weight, gender, telephone, insurance_id);
                    }
                    
                    if (parameters[0].equals("update_medRecord_bitalino")){
                    	Integer medicalRecordId = Integer.parseInt(parameters[2]);
                    	Integer bitalino = Integer.parseInt(parameters[1]);
                    	methods.Update_medical_record_with_bitalino(bitalino, medicalRecordId);
                    }
                    
                    if (parameters[0].equals("search_patient_info_by_id")) {
                    	//PENDIENTE POR HACER
                    }
                    
                    if (parameters[0].equals("search_patient_by_user_id")) {
                    	Integer user_id = Integer.parseInt(parameters[1]);
                        patientId = methods.Search_stored_patient_by_user_id(user_id);
                        dataOutputStream.writeUTF(String.valueOf(patientId));
                    }
                    if (parameters[0].equals("list_all_medical_records")) {
                        List<MedicalRecord> record_list = methods.List_all_medical_records();
                        List<String> records = new ArrayList<String>();
                        for(int i=0;i<record_list.size();i++) {
                        	String date = record_list.get(i).getRecordDate();
                        	Integer ref = record_list.get(i).getReferenceNumber();
                        	Integer bitId = record_list.get(i).getBitalinoTestId();
                        	String ecg_root = methods.Search_associated_ecg(bitId);
                        	String eda_root = methods.Search_associated_eda(bitId);
                        	
                        	String out = date+","+String.valueOf(ref)+","+String.valueOf(ecg_root)+","+String.valueOf(eda_root)+";";
                        	records.add(out);
                        }
                        String recordsString= Arrays.toString(records.toArray());
                        dataOutputStream.writeUTF(recordsString);
                    }
                    if (parameters[0].equals("list_all_insurances")) {
                        List<Insurance_company> list = methods.List_all_insurances();
                        List<String> ins_name = new ArrayList<String>();
                        String name="";
                        for(int i=0;i<list.size();i++) {
                        	name = list.get(i).getCompany_name();
                        	ins_name.add(name);
                        }
                        dataOutputStream.writeUTF(Arrays.toString(ins_name.toArray()));
                    }
                    if (parameters[0].equals("search_user_by_userName")) {
                    	String user_name = parameters[1];
                        userId = methods.Search_stored_user_by_userName(user_name);
                        dataOutputStream.writeUTF(String.valueOf(userId));
                    }
                    if (parameters[0].equals("search_insurance_by_name")) {
                    	String insurance_name = parameters[1];
                        Integer insurance_id = methods.Search_insurance_by_name(insurance_name);
                        dataOutputStream.writeUTF(String.valueOf(insurance_id));
                    }
                    
                    if (parameters[0].equals("search_insurance_by_patient_id")) {
						patientId = Integer.parseInt(parameters[1]);
						Integer id = methods.Search_insurance_from_patient(patientId);
						dataOutputStream.writeUTF(String.valueOf(id));
					}
                    
                    if (parameters[0].equals("search_doctor_by_insurance")) {
                    	Integer insuranceId = Integer.parseInt(parameters[1]);
                    	String doctor = methods.Search_doctor_by_insurance_id(insuranceId);
                    	dataOutputStream.writeUTF(doctor);
                    }
                    if (parameters[0].equals("list_users")) { //POR AHORA NO LA USAMOS
                    	List<User> users = methods.List_all_users();
                        List<String> list = new ArrayList<String>();
                        String userName="";
                        for(int i=0;i<users.size();i++) {
                        	userName = users.get(i).getUserName();
                        	list.add(userName);
                        }
                        dataOutputStream.writeUTF(Arrays.toString(list.toArray()));
                    }
                    
                    if (parameters[0].equals("search_existent_refNumber")) { 
                    	Integer ref_number = methods.Search_existent_reference_number(Integer.parseInt(parameters[1]));
                    	dataOutputStream.writeUTF(String.valueOf(ref_number));
                    }
                    
                    if (parameters[0].equals("search_associated_ecg")) { //REVISAR DONDE SE LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    	Integer bitalino_id = Integer.parseInt(parameters[1]);
                    	String ecg_root = methods.Search_associated_ecg(bitalino_id);
                    	dataOutputStream.writeUTF(ecg_root);
                    }
                    if (parameters[0].equals("search_associated_eda")) { //REVISAR DONDE SE LLAMAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
                    	Integer bitalino_id = Integer.parseInt(parameters[1]);
                    	String eda_root = methods.Search_associated_eda(bitalino_id);
                    	dataOutputStream.writeUTF(eda_root);
                    }
                    
                    if (parameters[0].equals("compare_passwords")) {
                    	String user_name = parameters[1];
                    	String true_password = methods.Get_user_password(user_name);
                    	if(parameters[2].equals(true_password)) {
                        	dataOutputStream.writeUTF("okay");
                    	} else {
                        	dataOutputStream.writeUTF("wrong");
                    	}
                    }
                    if (parameters[0].equals("search_existent_email")) {
                    	String email = parameters[1];
                    	boolean check = methods.Search_existent_email(email);
                    	if(check == false) {
                        	dataOutputStream.writeUTF("okay");
                    	} else {
                        	dataOutputStream.writeUTF("wrong");
                    	}
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