package SQLite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import interfaces.Interface;
import pojos.Doctor;
import pojos.Insurance_company;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.User;

public class SQLiteMethods implements Interface {

	private Connection sqlite_connection;
	

	// -----> INSERT METHODS <-----
	
	public SQLiteMethods(Connection sqlite_connection) {
		this.sqlite_connection = sqlite_connection;
	}

	public void Insert_default_elements_toDB() {
		Insert_new_insurance("Anthem");
		Insert_new_insurance("Centene");
		Insert_new_insurance("UnitedHealth");
		Insert_new_insurance("HCSC");
		Insert_new_insurance("DKV");
		Insert_new_insurance("Sanitas");
		Insert_new_insurance("Maphre");
		Insert_new_insurance("AXA");
		Insert_new_insurance("Asisa");
		Insert_new_insurance("Adeslas");
		Insert_new_insurance("Caser");
		Insert_new_insurance("Allianz");
		Insert_new_insurance("Aegon");
		Insert_new_insurance("Other");
		
		Insert_new_doctor("Jose Luis García", "55566778", 1);
		Insert_new_doctor("Carlos Ruíz", "59987765", 2);
		Insert_new_doctor("Marta Martínez", "56734037", 3);
		Insert_new_doctor("Laura Esteban", "53445465", 4);
		Insert_new_doctor("Julia Medea", "53064387", 5);
		Insert_new_doctor("Maria José García", "55334580", 6);
		Insert_new_doctor("Mariano Lopez", "56489225", 7);
		Insert_new_doctor("Richard Gansey", "56637910", 8);
		Insert_new_doctor("Carolina Gonzalez", "59887098", 9);
		Insert_new_doctor("Zoe Belladona", "54286551", 10);
		Insert_new_doctor("Samanta Díaz", "5745309", 11);
		Insert_new_doctor("Rodrigo  Lupin", "50008247", 12);
		Insert_new_doctor("Ricardo Esteban", "57738954", 13);
		Insert_new_doctor("Sara Girod", "56846304", 14);
	}
	
	
	//funciona
	public Integer Insert_new_user(String user_name, String password, String email) {
		try {
			String table = "INSERT INTO user (user_name, password, email) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, user_name);
			template.setString(2, password);
			template.setString(3, email);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM user WHERE user_name = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			ResultSet result_set = template.executeQuery();
			User user = new User();
		    user.setUserName(result_set.getString("user_name"));
		    user.setPassword(result_set.getString("password"));
		    user.setEmail(result_set.getString("email"));
		    user.setUserId(result_set.getInt("user_id"));
		    return user.getUserId();
		} catch (SQLException insert_user_error) {
			insert_user_error.printStackTrace();
			return null;
		}
	}
	
	public Integer Insert_new_medical_record(String record_date, Integer reference_number, Integer patient_id) {
		try {
			String table = "INSERT INTO medical_record (record_date, reference_number, patient_id) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, record_date);
			template.setInt(2, reference_number);
			template.setInt(3, patient_id);
			template.executeUpdate();
			
			String SQL_code1 = "SELECT last_insert_rowid() AS record_id";
			template = this.sqlite_connection.prepareStatement(SQL_code1);
			ResultSet result_set = template.executeQuery();
			Integer record_id = result_set.getInt("record_id");
			MedicalRecord medical_record = new MedicalRecord(record_id, record_date, reference_number, patient_id);
			template.close();
			
			return medical_record.getMedicalRecord_id();	
			
		} catch (SQLException insert_record_error) {
			insert_record_error.printStackTrace();
			return null;
		}
	}
	
	//funciona
    public Integer Insert_new_patient(Integer user_id, String name, String surname) {
    	try {
			String table = "INSERT INTO patient (user_id, name, surname) " + "VALUES (?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, user_id);
			template.setString(2, name);
			template.setString(3, surname);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM patient WHERE user_id = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user_id);
			ResultSet result_set = template.executeQuery();
			Patient patient = new Patient();
			patient.setPatient_id(result_set.getInt("patient_id"));
			patient.setName(result_set.getString("name"));
			patient.setSurname(result_set.getString("surname"));
			patient.setUser_id(user_id);
			patient.setBirth_date(null);
			//patient.setAge();
			patient.setHeight(null);
			patient.setWeight(null);
			patient.setGender(null);
			patient.setTelephone(null);
			patient.setInsurance_id(null);
			
			return patient.getPatient_id();
		} catch (SQLException new_client_account_error) {
			new_client_account_error.printStackTrace();
			return null;
		}
    }
     
    public Integer Insert_new_doctor(String name, String telephone, Integer insurance_id) {
		try {
			String table = "INSERT INTO doctor (name, telephone, insurance_id) " + "VALUES (?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.setString(2, telephone);
			template.setInt(3, insurance_id);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM doctor WHERE name = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, name);
			ResultSet result_set = template.executeQuery();
			Doctor doctor = new Doctor();
			doctor.setDoctor_id(result_set.getInt("doctor_id"));
			doctor.setName(result_set.getString("name"));
			doctor.setTelephone(result_set.getInt("telephone"));
			doctor.setInsurance_id(result_set.getInt("insurance_id"));
			return doctor.getDoctor_id();
		} catch(SQLException new_doctor_error) {
			new_doctor_error.printStackTrace();
			return -1;
		}
	}
     
    public Integer Insert_new_ecg(String ecg_values, Integer test_id) {
		try {
			String table = "INSERT INTO ecg_test (ecg_root, test_id) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, ecg_values);
			template.setInt(2, test_id);
			template.executeUpdate();
			
			String SQL_code = "SELECT last_insert_rowid() AS ecg_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer ecg_id = result_set.getInt("ecg_id");
			template.close();
			return ecg_id;
		} catch(SQLException new_ecg_error) {
			new_ecg_error.printStackTrace();
			return -1;
		}
	}

    public Integer Insert_new_eda(String eda_values, Integer test_id) {
		try {
			String table = "INSERT INTO eda_test (eda_root, test_id) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, eda_values);
			template.setInt(2, test_id);
			template.executeUpdate();
			
			String SQL_code = "SELECT last_insert_rowid() AS eda_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer eda_id = result_set.getInt("eda_id");
			template.close();
			return eda_id;
		} catch(SQLException new_eda_error) {
			new_eda_error.printStackTrace();
			return null;
		}
	}
    
    
    // CREO QUE HABRÁ QUE CAMBIAR LOS TIPOS A STRING PARA EXPORTARLOS A TEXTO Y QUE SEA ENTENDIBLE
    public Integer Insert_new_psycho_test(LinkedList<String> positive_res, LinkedList<String> negative_res, LinkedList<String> symptoms, Integer medicalRecord_id) {
		try {
			String table = "INSERT INTO psycho_test (positive_res, negative_res, symptoms, medicalRecord_id) " + "VALUES (?,?,?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, positive_res.toString());
			template.setString(2, negative_res.toString());
			template.setString(3, symptoms.toString());
			template.setInt(4, medicalRecord_id);
			template.executeUpdate();
			
			
			String SQL_code = "SELECT last_insert_rowid() AS queries_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer queries_id = result_set.getInt("queries_id");
			template.close();
			return queries_id;
		} catch(SQLException new_psycho_test_error) {
			new_psycho_test_error.printStackTrace();
			return -1;
		}
	}
    
    public Integer Insert_new_physical_test(Integer saturation, Integer pulse, Integer breathingRate,Integer medicalRecord_id) {
		try {
			String table = "INSERT INTO physical_test (saturation, pulse, breathingRate, medicalRecord_id) " + "VALUES (?,?,?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, saturation);
			template.setInt(2, pulse);
			template.setInt(3, breathingRate);
			template.setInt(4, medicalRecord_id);
			template.executeUpdate();
			
			String SQL_code = "SELECT last_insert_rowid() AS test_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer test_id = result_set.getInt("test_id");
			template.close();
			return test_id;
		} catch(SQLException new_physical_test_error) {
			new_physical_test_error.printStackTrace();
			return -1;
		}
	}
 	
    public Integer Insert_new_bitalino_test() {
    	try {
    		String table = "INSERT INTO bitalino_test DEFAULT VALUES";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS test_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer test_id = result_set.getInt("test_id");
			template.close();
			return test_id;
		} catch (SQLException new_bitalino_error) {
			new_bitalino_error.printStackTrace();
			return -1;
		}
    }
    
    //funciona
	public Integer Insert_new_insurance(String company_name) {
		Integer insurance_id;
		String table = "INSERT INTO insurance (name) " + "VALUES (?)";
		try {
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, company_name);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS insurance_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			insurance_id = result_set.getInt("insurance_id");
			template.close();
			return insurance_id;
		} catch (SQLException new_insurance_error) {
			new_insurance_error.printStackTrace();
			return -1;
		}
		
	}
    
	// -----> UPDATE METHODS <-----
	
    //funciona
    public boolean Update_user_info(String password, String email, Integer user_id) {
		try {
			String SQL_code = "UPDATE user SET password = ?, email = ? WHERE user_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			if(!password.equals("")) {
				template.setString(1, password);
			}
			if(!email.equals("")) {
				template.setString(2, email);
			}
			template.setInt(3, user_id);
			template.executeUpdate();
			template.close();
			return true;
		} catch (SQLException update_user_error) {
			update_user_error.printStackTrace();
			return false;
		}
	}
    
    //funciona
    public void Update_patient_info(Integer patient_id, String birth_date, Integer height, Integer weight, String gender, Integer telephone, Integer insurance_id) {
    	try {
    		
    		Patient patient = Search_stored_patient_by_id(patient_id);
    		
    		String SQL_code = "UPDATE patient SET birth_date = ?, height = ?, weight = ?, gender = ?, telephone = ?, insurance_id = ? WHERE patient_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);

			if(!birth_date.equals("")) {
			template.setString(1, birth_date);
			}
			
			if(!height.equals("")) {
				template.setInt(2, height);
			}
			
			if(!weight.equals("")) {
				template.setInt(3, weight);
			}
			
			if(!gender.equals("")) {
				template.setString(4, gender);
			}
			
			if(!telephone.equals("")) {
				template.setInt(5, telephone);
			}
			
			if(!insurance_id.equals("")) {
				template.setInt(6, insurance_id);
			}
			template.setInt(7, patient_id);
			template.executeUpdate();
			template.close();
		} catch (SQLException update_patient_error) {
			update_patient_error.printStackTrace();
		}
    }
    
    
    public boolean Update_medical_record_with_bitalino(Integer bitalino_id, Integer medRecord_id) {
    	try {
			String SQL_code = "UPDATE medical_record SET bitalino_test_id = ? WHERE  medicalRecord_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, bitalino_id);
			template.setInt(2, medRecord_id);
			template.executeUpdate();
			template.close();
			return true;
		} catch (SQLException update_medRecord_error) {
			update_medRecord_error.printStackTrace();
			return false;
		}
    }
    
	// -----> SEARCH STORED ELEMENTS BY ID METHODS <-----
    //funciona
    public Patient Search_stored_patient_by_id(Integer patient_id) {
		try {
			String SQL_code = "SELECT * FROM patient WHERE patient_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, patient_id);
			Patient patient= new Patient();
			ResultSet result_set = template.executeQuery();
			patient.setPatient_id(patient_id);
			patient.setName(result_set.getString("name"));
			patient.setSurname(result_set.getString("surname"));
			patient.setBirth_date(result_set.getString("birth_date"));
			patient.setHeight(result_set.getInt("height"));
			patient.setWeight(result_set.getInt("weight"));
			patient.setGender(result_set.getString("gender"));
			patient.setTelephone(result_set.getInt("telephone"));
			patient.setInsurance_id(result_set.getInt("insurance_id"));
			template.close();
			return patient;
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return null;
		}
		
	}
    
    //funciona
    public Integer Search_stored_patient_by_user_id(Integer user_id) {
    	Integer id=-1;
		try {
			String SQL_code = "SELECT * FROM patient WHERE user_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user_id);
			ResultSet result_set = template.executeQuery();
			while(result_set.next()) {
				id = result_set.getInt("patient_id");
			}
			template.close();
			return id;
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return id;
		}
		
	}
    
	    //funciona
	    public Integer Search_stored_user_by_userName(String user_name) {
	    	Integer user_id=-1;
			try {
				String SQL_code = "SELECT user_id FROM user WHERE user_name LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, user_name);
				ResultSet result_set = template.executeQuery();
				
			    while(result_set.next()) {
			    	user_id = result_set.getInt("user_id");
			    }
				template.close();
				return user_id;
			} catch (SQLException search_patient_error) {
				search_patient_error.printStackTrace();
				return user_id;
			}
			
		}
    
    	public String Search_doctor_by_insurance_id(Integer insurance_id) {
    		String name="";
    		try {
				String SQL_code = "SELECT name FROM doctor WHERE insurance_id = ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, insurance_id);
				ResultSet result_set = template.executeQuery();
				while(result_set.next()) {
					name = result_set.getString("name");
				}
				template.close();
				return name;
			} catch (SQLException search_doctor) {
				search_doctor.printStackTrace();
				return "Doctor not found.";
			}
    	}
    
    	public Integer Search_insurance_from_patient(Integer patient_id) {
    		Integer id=-1;
    		try {
				String SQL_code = "SELECT insurance_id FROM patient WHERE patient_id = ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, patient_id);
				ResultSet result_set = template.executeQuery();
				
				while(result_set.next()) {
					id = result_set.getInt("insurance_id");
				}
				template.close();
				return id;
			} catch (SQLException search_insurance_error) {
				search_insurance_error.printStackTrace();
				return id;
			}
    	}
    
	  //funciona
	    public boolean Search_existent_email(String email) {
			try {
				String SQL_code = "SELECT email FROM user WHERE email LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, email);
				ResultSet result_set = template.executeQuery();
				if(result_set.next()) {
					template.close();
					return true;
				} else{
					template.close();
					return false;
				}
			} catch (SQLException search_patient_error) {
				search_patient_error.printStackTrace();
				return false;
			}
			
		}

	    
	    public Integer Search_existent_reference_number(Integer number) {
	    	Integer match=1;
			try {
				String SQL_code = "SELECT reference_number FROM medical_record WHERE reference_number = ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setInt(1, number);
				ResultSet result_set = template.executeQuery();
				if(result_set.next()){
					match=0;
					template.close();
					return match;
				} else {
					template.close();
				return match;
				}
				
			} catch (SQLException search_refnumber_error) {
				search_refnumber_error.printStackTrace();
				return match;
			}
			
		}
    
    
    public String Get_user_password (String user_name) {
    	String pass="Password doesn't match";
		try {
			String SQL_code = "SELECT password FROM user WHERE user_name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			ResultSet result_set = template.executeQuery();
			while (result_set.next()) {
				pass = result_set.getString("password");
			}
			template.close();
			return pass;
			
			
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return pass;
		}
		
	}
    
    /*
	public Integer Search_stored_record_by_id(Integer record_id) {
		Integer id=-1;
		try {
			String SQL_code = "SELECT record_id FROM medical_record WHERE medicalRecord_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			ResultSet result_set = template.executeQuery();
			while(result_set.next()) {
				id = result_set.getInt("")
				return id;
			} else {
				template.close();
				return id;
			}
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return id;
		}
		
	}
	*/
	
	
	public Integer Search_insurance_by_name(String insurance_name) {
		Integer id=-1;
		try {
			String SQL_code = "SELECT insurance_id FROM insurance WHERE name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, insurance_name);
			
			ResultSet result_set = template.executeQuery();
			while(result_set.next()) {
				id = result_set.getInt("insurance_id");
			}
			template.close(); 
			return id;
		} catch (SQLException search_insurance_error) {
			search_insurance_error.printStackTrace();
			return id;
		}
	}
	
	public String Search_associated_ecg(Integer bitalino_id) {
		String root="Not registered.";
		try {
			String SQL_code = "SELECT ecg_root FROM ecg_test WHERE test_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, bitalino_id);
			ResultSet result_set = template.executeQuery();
			while(result_set.next()){
				root = result_set.getString("ecg_root");
			}
			template.close();
			return root;
		} catch (SQLException search_ecg_error) {
			search_ecg_error.printStackTrace();
			return root;
		}
	}
	public String Search_associated_eda(Integer bitalino_id) {
		String root="Not registered.";
		try {
			String SQL_code = "SELECT eda_root FROM eda_test WHERE test_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, bitalino_id);
			ResultSet result_set = template.executeQuery();
			while(result_set.next()){
				root = result_set.getString("eda_root");
			}
			template.close();
			return root;
		} catch (SQLException search_eda_error) {
			search_eda_error.printStackTrace();
			return root;
		}
	}
	
	
	// -----> LIST METHODS <-----
	
	public List<User> List_all_users() {
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM user";
			List<User> users_list = new LinkedList<User>();
			ResultSet result_set = statement.executeQuery(SQL_code);
			while (result_set.next()) {
				User user = new User();
				user.setUserId(result_set.getInt("user_id"));
				user.setUserName(result_set.getString("user_name"));
				user.setPassword(result_set.getString("password"));
				user.setEmail(result_set.getString("email"));
				users_list.add(user);
			}
			statement.close();
			return users_list;
		} catch (SQLException list_users_error) {
			list_users_error.printStackTrace();
			return null;
		}
	}
	
	public List<MedicalRecord> List_all_medical_records() {
		List<MedicalRecord> record_list = new LinkedList<MedicalRecord>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM medical_record";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				Integer id = rs.getInt("medicalRecord_id");
				String date = rs.getString("record_date");
				Integer referenceNumber = rs.getInt("reference_number");
				Integer bitalino_test_id = rs.getInt("bitalino_test_id");
				record_list.add(new MedicalRecord(id, referenceNumber, date, bitalino_test_id));
			}
			return record_list;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}
	
	public List<Insurance_company> List_all_insurances(){
		List<Insurance_company> list = new LinkedList<Insurance_company>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM insurance";
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				Integer id = rs.getInt("insurance_id");
				String name = rs.getString("name");
				list.add(new Insurance_company(id, name));
			}
			return list;
		} catch (SQLException list_insurances) {
			list_insurances.printStackTrace();
			return null;
		}
	}

	
	
}
