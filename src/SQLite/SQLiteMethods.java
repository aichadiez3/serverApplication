package SQLite;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import interfaces.Interface;
import pojos.Doctor;
import pojos.EcgTest;
import pojos.EdaTest;
import pojos.Insurance_company;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.PhysicalTest;
import pojos.PsychoTest;
import pojos.Symptom;
import pojos.User;

public class SQLiteMethods implements Interface {

	private Connection sqlite_connection;
	
	public SQLiteMethods() {
		// TODO Auto-generated constructor stub
	}

	// -----> INSERT METHODS <-----
	
	public SQLiteMethods(Connection sqlite_connection) {
		this.sqlite_connection = sqlite_connection;
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
	
	public Integer Insert_new_medical_record(Date record_date, Integer reference_number, Integer patient_id) {
		try {
			String table = "INSERT INTO medical_record (reference_number, record_date, patient_id) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setDate(1, (Date) record_date);
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
     
    public Integer Insert_new_doctor(String name, String telephone) {
		try {
			String table = "INSERT INTO doctor (name, telephone) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setString(1, name);
			template.setString(2, telephone);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM doctor WHERE name = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, name);
			ResultSet result_set = template.executeQuery();
			Doctor doctor = new Doctor();
			doctor.setDoctor_id(result_set.getInt("doctor_id"));
			doctor.setName(result_set.getString("name"));
			doctor.setTelephone(result_set.getInt("telephone"));
			return doctor.getDoctor_id();
		} catch(SQLException new_doctor_error) {
			new_doctor_error.printStackTrace();
			return null;
		}
	}
     
    public Integer Insert_new_ecg(String ecg_values, Integer test_id) {
		try {
			String table = "INSERT INTO ecg_test (values, test_id) " + "VALUES (?,?)";
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
			return null;
		}
	}

    public Integer Insert_new_eda(String eda_values, Integer test_id) {
		try {
			String table = "INSERT INTO eda_test (values, test_id) " + "VALUES (?,?)";
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
    public Integer Insert_new_psycho_test(LinkedList<String> positive_res, LinkedList<String> negative_res, Integer medicalRecord_id) {
		try {
			String table = "INSERT INTO psycho_test (positive_res, negative_res, medicalRecord_id) " + "VALUES (?,?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setArray(1, (Array) positive_res);
			template.setArray(2, (Array) negative_res);
			template.setInt(3, medicalRecord_id);
			template.executeUpdate();
			
			
			String SQL_code = "SELECT last_insert_rowid() AS queries_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer queries_id = result_set.getInt("queries_id");
			template.close();
			
			/*
			LinkedList<Boolean> positive_res = psycho.getPositive_res();
			for() {
				
			}
			
			
			List<Biomaterial> biomaterial_list = transaction.getBiomaterial_list();
			for(Biomaterial biomaterial: biomaterial_list) {
				table = "INSERT INTO transaction_biomaterial (transaction_id, biomaterial_id) " 
						+ "VALUES (?,?);";
				template = this.sqlite_connection.prepareStatement(table);
				template.setInt(1, transaction_id);
				template.setInt(2, biomaterial.getBiomaterial_id());
				template.executeUpdate();
				template.close();
			}
			
			*/
			
			return queries_id;
		} catch(SQLException new_psycho_test_error) {
			new_psycho_test_error.printStackTrace();
			return null;
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
			return null;
		}
	}
 	
    public Integer Insert_new_bitalino_test(Integer medicalRecord_id) {
    	Integer test_id = null;
    	try {
    		String table = "INSERT INTO bitalino_test";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.executeUpdate();
			String SQL_code = "SELECT last_insert_rowid() AS insurance_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			test_id = result_set.getInt("insurance_id");
			template.close();
			return test_id;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return test_id;
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
    public boolean Update_patient_info(Integer patient_id, String name, String surname, Date birth_date/*, Integer age*/, Integer height, Integer weight, String gender, Integer telephone, Integer insurance_id) {
    	try {
    		
    		Patient patient = Search_stored_patient_by_id(patient_id);
    		
    		String SQL_code = "UPDATE patient SET name = ?, surname = ?, birth_date = ?, height = ?, weight = ?, gender = ?, telephone = ?, insurance_id = ? WHERE patient_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			if(!name.equals("")) {
			template.setString(1, name);
			}

			if(!surname.equals("")) {
				template.setString(2, surname);
			}

			if(!birth_date.equals("")) {
			template.setDate(3, birth_date);
			}
			
			/*if(!age.equals("")) {
				template.setInt(4, age);
			}*/
			
			if(!height.equals("")) {
				template.setInt(4, height);
			}
			
			if(!weight.equals("")) {
				template.setInt(5, weight);
			}
			
			if(!gender.equals("")) {
				template.setString(6, gender);
			}
			
			if(!telephone.equals("")) {
				template.setInt(7, telephone);
			}
			
			if(!insurance_id.equals("")) {
				template.setInt(8, insurance_id);
			}
			template.setInt(9, patient_id);
			template.executeUpdate();
			template.close();
    		
			return true;
		} catch (SQLException update_patient_error) {
			update_patient_error.printStackTrace();
			return false;
		}
    }
    
	// -----> SEARCH STORED ELEMENTS BY ID METHODS <-----
    //funciona
    public Patient Search_stored_patient_by_id(Integer patient_id) {
		try {
			String SQL_code = "SELECT * FROM patient WHERE patient_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, patient_id);
			Patient patient= new Patient();
			ResultSet result_set = template.executeQuery();
			patient.setPatient_id(patient_id);
			patient.setName(result_set.getString("name"));
			patient.setSurname(result_set.getString("surname"));
			patient.setBirth_date(result_set.getDate("birth_date"));
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
		try {
			String SQL_code = "SELECT * FROM patient WHERE user_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user_id);
			Patient patient= new Patient();
			ResultSet result_set = template.executeQuery();
			patient.setPatient_id(result_set.getInt("patient_id"));
			patient.setName(result_set.getString("name"));
			patient.setSurname(result_set.getString("surname"));
			patient.setBirth_date(result_set.getDate("birth_date"));
			patient.setHeight(result_set.getInt("height"));
			patient.setWeight(result_set.getInt("weight"));
			patient.setGender(result_set.getString("gender"));
			patient.setTelephone(result_set.getInt("telephone"));
			patient.setInsurance_id(result_set.getInt("insurance_id"));
			patient.setUser_id(user_id);
			template.close();
			return patient.getPatient_id();
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return null;
		}
		
	}
    
    //funciona
    public Integer Search_stored_user_by_userName(String user_name) {
		try {
			String SQL_code = "SELECT * FROM user WHERE user_name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			User user = new User();
			ResultSet result_set = template.executeQuery();
		    user.setUserName(result_set.getString("user_name"));
		    user.setPassword(result_set.getString("password"));
		    user.setEmail(result_set.getString("email"));
		    user.setUserId(result_set.getInt("user_id"));
			template.close();
			return user.getUserId();
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return -1;
		}
		
	}
    
    
	  //funciona
	    public boolean Search_existent_email(String email) {
			try {
				String SQL_code = "SELECT * FROM user WHERE email LIKE ?";
				PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
				template.setString(1, email);
				User user = new User();
				ResultSet result_set = template.executeQuery();
			    user.setUserName(result_set.getString("user_name"));
			    user.setPassword(result_set.getString("password"));
			    user.setEmail(result_set.getString("email"));
			    user.setUserId(result_set.getInt("user_id"));
				template.close();
				return true;
			} catch (SQLException search_patient_error) {
				search_patient_error.printStackTrace();
				return false;
			}
			
		}
    
    
    public String Get_user_password (String user_name) {
		try {
			String SQL_code = "SELECT * FROM user WHERE user_name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, user_name);
			User user = new User();
			ResultSet result_set = template.executeQuery();
		    user.setUserName(result_set.getString("user_name"));
		    user.setPassword(result_set.getString("password"));
		    user.setEmail(result_set.getString("email"));
		    user.setUserId(result_set.getInt("user_id"));
			template.close();
			return user.getPassword();
		} catch (SQLException search_patient_error) {
			search_patient_error.printStackTrace();
			return null;
		}
		
	}
    
	public MedicalRecord Search_stored_record_by_id(Integer record_id) {
		try {
			String SQL_code = "SELECT * FROM medical_record WHERE medicalRecord_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			MedicalRecord record=new MedicalRecord();
			ResultSet result_set = template.executeQuery();
			record.setMedicalRecord_id(record_id);
			record.setReferenceNumber(result_set.getInt("reference_number"));
			record.setRecordDate(result_set.getDate("record_date"));
			record.setBitalinoTestId(result_set.getInt("bitalino_test_id"));
			//List<Symptom> symptoms_list = Search_all_symptoms_from_record(record_id);
			//record.setSymptoms_list(symptoms_list);
			template.close();
			return record;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}
	
	
	public Symptom Search_symptom_by_id(Integer symptom_id) {
		try {
			String SQL_code = "SELECT * FROM medicalRecord_symptom WHERE symptom_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, symptom_id);
			Symptom symptom = new Symptom();
			ResultSet result_set = template.executeQuery();
			symptom.setSymptom_id(symptom_id);
			symptom.setName(result_set.getString("name"));
			symptom.setWeight(result_set.getInt("weight"));
			template.close(); 
			return symptom;
		} catch (SQLException search_symptom_error) {
			search_symptom_error.printStackTrace();
			return null;
		}
	}
	
	public Insurance_company Search_insurance_by_name(String insurance_name) {
		try {
			String SQL_code = "SELECT * FROM insurance WHERE name LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, insurance_name);
			Insurance_company insurance = new Insurance_company();
			ResultSet result_set = template.executeQuery();
			insurance.setInsurance_id(result_set.getInt("insurance_id"));
			insurance.setCompany_name(insurance_name);
			template.close(); 
			return insurance;
		} catch (SQLException search_insurance_error) {
			search_insurance_error.printStackTrace();
			return null;
		}
	}
	
	public Integer Search_associated_ecg(Integer bitalino_id) {
		try {
			String SQL_code = "SELECT * FROM ecg_test WHERE test_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, bitalino_id);
			EcgTest test = new EcgTest();
			ResultSet result_set = template.executeQuery();
			test.setEcg_id(result_set.getInt("ecg_id"));
			test.setEcg_values(result_set.getString("ecg_values"));
			template.close();
			return test.getEcg_id();
		} catch (SQLException search_ecg_error) {
			search_ecg_error.printStackTrace();
			return null;
		}
	}
	public Integer Search_associated_eda(Integer bitalino_id) {
		try {
			String SQL_code = "SELECT * FROM eda_test WHERE test_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, bitalino_id);
			EdaTest test = new EdaTest();
			ResultSet result_set = template.executeQuery();
			test.setEda_id(result_set.getInt("eda_id"));
			test.setEda_values(result_set.getString("eda_values"));
			template.close();
			return test.getEda_id();
		} catch (SQLException search_eda_error) {
			search_eda_error.printStackTrace();
			return null;
		}
	}
	
	
	// -----> SEARCH BY DATE METHODS <-----
	public List<MedicalRecord> Search_stored_record_by_date_ascendent() {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record ORDER BY record_date ASC";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet rs = template.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				Integer bitalino_test_id = rs.getInt("bitalino_test_id");
				
				Integer ecg_id = Search_associated_ecg(bitalino_test_id);
				Integer eda_id = Search_associated_eda(bitalino_test_id);
				
				//List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id));
			}
			return records;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}
	
	public List<MedicalRecord> Search_stored_record_by_date_descendent() {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record ORDER BY record_date DESC";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet rs = template.executeQuery();
			
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				Integer bitalino_test_id = rs.getInt("bitalino_test_id");
				//List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id));
			}
			return records;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}
	
	// -----> LIST METHODS <-----
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id) {
		try {
			
			String SQL_code = "SELECT * FROM medicalRecord_symptom WHERE medicalRecord_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			ResultSet result_set = template.executeQuery();
			List<Symptom> symptom_list = new LinkedList<Symptom>();
			while (result_set.next()) {
				Symptom symptom = Search_symptom_by_id(result_set.getInt("symptom_id"));
				symptom_list.add(symptom);
			}
			template.close();
			return symptom_list;
		} catch (SQLException list_symptom_record_error) {
			list_symptom_record_error.printStackTrace();
			return null;
		}
	}
	
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
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			Statement statement = this.sqlite_connection.createStatement();
			String SQL_code = "SELECT * FROM medical_record";
			List<MedicalRecord> record_list = new LinkedList<MedicalRecord>();
			ResultSet rs = statement.executeQuery(SQL_code);
			while(rs.next()) {
				Integer id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				Integer referenceNumber = rs.getInt("reference_number");
				Integer bitalino_test_id = rs.getInt("bitalino_test_id");
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id));
			}
			return records;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}

	
	
}
