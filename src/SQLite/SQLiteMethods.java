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
		super();
		// TODO Auto-generated constructor stub
	}

	// -----> INSERT METHODS <-----
	
	public SQLiteMethods(Connection sqlite_connection) {
		this.sqlite_connection = sqlite_connection;
	}

	public User Insert_new_user(String user_name, String password, String email) {
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
		    return user;
		} catch (SQLException insert_user_error) {
			insert_user_error.printStackTrace();
			return null;
		}
	}
	
	public MedicalRecord Insert_new_medical_record(Date record_date, Integer reference_number, Integer bitalino_test_id) {
		try {
			String table = "INSERT INTO medical_record (reference_number, record_date, bitalino_test_id) " + " VALUES(?,?,?);";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setDate(1, (Date) record_date);
			template.setInt(2, reference_number);
			template.setInt(3, bitalino_test_id);
			template.executeUpdate();
			
			String SQL_code = "SELECT last_insert_rowid() AS record_id";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			ResultSet result_set = template.executeQuery();
			Integer record_id = result_set.getInt("record_id");
			MedicalRecord medical_record = new MedicalRecord(record_id, record_date, (Integer) reference_number, (Integer) bitalino_test_id);
			template.close();
			return medical_record;
			
		} catch (SQLException insert_record_error) {
			insert_record_error.printStackTrace();
			return null;
		}
	}
	
    public Patient Insert_new_patient(Integer user_id, String name, String surname) {
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
			patient.setAge(null);
			patient.setHeight(null);
			patient.setWeight(null);
			patient.setGender(null);
			patient.setTelephone(null);
			patient.setInsurance_id(null);
			
			return patient;
		} catch (SQLException new_client_account_error) {
			new_client_account_error.printStackTrace();
			return null;
		}
    }
     
    public Doctor Insert_new_doctor(Integer user_id, String name) {
		try {
			String table = "INSERT INTO doctor (user_id, name) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setInt(1, user_id);
			template.setString(2, name);
			template.executeUpdate();
			
			String SQL_code = "SELECT * FROM director WHERE user_id = ?";
			template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, user_id);
			ResultSet result_set = template.executeQuery();
			Doctor doctor = new Doctor();
			doctor.setDoctor_id(result_set.getInt("director_id"));
			doctor.setName(result_set.getString("name"));
			doctor.setTelephone(result_set.getInt("telephone"));
			return doctor;
		} catch(SQLException new_director_error) {
			new_director_error.printStackTrace();
			return null;
		}
	}
    
    
    public Integer Insert_new_ecg(LinkedList<Integer> ecg_values, Integer test_id) {
		try {
			String table = "INSERT INTO ecg_test (values, test_id) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setArray(1, (Array) ecg_values);
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

    public Integer Insert_new_eda(LinkedList<Integer> eda_values, Integer test_id) {
		try {
			String table = "INSERT INTO eda_test (values, test_id) " + "VALUES (?,?)";
			PreparedStatement template = this.sqlite_connection.prepareStatement(table);
			template.setArray(1, (Array) eda_values);
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
    
    public Integer Insert_new_psycho_test(LinkedList<Boolean> positive_res, LinkedList<Boolean> negative_res, Integer medicalRecord_id) {
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
    
    
	
	
	// -----> UPDATE METHODS <-----
	
    public void Change_password(String password, Integer user_id) {
		try {
			String SQL_code = "UPDATE user SET password = ? WHERE user_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, password);
			template.setInt(2, user_id);
			template.executeUpdate();
			template.close();
		} catch (SQLException update_password_error) {
			update_password_error.printStackTrace();
		}
	}
    
    public boolean Update_patient_info(Integer patient_id) {
    	try {
    		//BUSCAR PACIENTE LLAMANDO AL METODO BUSCAR PACIENTE POR ID
    		
    		Patient patient = Search_stored_patient_by_id(patient_id);
    		
    		String SQL_code = "UPDATE patient SET name = ?, surname = ?, birthdate = ?, age = ?, telephone = ?, height = ?, weight = ?, gender = ?, insurance_id = ? WHERE patient_id = ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setString(1, patient.getName());
			template.setString(2, patient.getSurname());
			template.setDate(3, (Date) patient.getBirth_date());
			template.setInt(4, patient.getAge());
			template.setInt(5, patient.getTelephone());
			template.setInt(6, patient.getHeight());
			template.setInt(7, patient.getWeight());
			template.setString(8, patient.getGender());
			template.setInt(9, patient.getInsurance_id());
			template.setInt(10, patient.getPatient_id());
			template.executeUpdate();
			template.close();
    		
			return true;
		} catch (SQLException update_patient_error) {
			update_patient_error.printStackTrace();
			return false;
		}
    }
    
    

	// -----> SEARCH STORED ELEMENTS BY ID METHODS <-----
    public Patient Search_stored_patient_by_id(Integer patient_id) {
		try {
			String SQL_code = "SELECT * FROM patient WHERE patient_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, patient_id);
			Patient patient= new Patient();
			ResultSet result_set = template.executeQuery();
			patient.setName(result_set.getString("name"));
			patient.setSurname(result_set.getString("surname"));
			patient.setBirth_date(result_set.getDate("name"));
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
    
    
    
	public MedicalRecord Search_stored_record_by_id(Integer record_id) {
		try {
			String SQL_code = "SELECT * FROM medical_record WHERE medicalRecord_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, record_id);
			MedicalRecord record=new MedicalRecord();
			ResultSet result_set = template.executeQuery();
			record.setReferenceNumber(result_set.getInt("reference_number"));
			record.setRecordDate(result_set.getDate("record_date"));
			record.setBitalinoTestId(result_set.getInt("bitalino_test_id"));
			List<Symptom> symptoms_list = Search_all_symptoms_from_record(record_id);
			record.setSymptoms_list(symptoms_list);
			template.close();
			return record;
		} catch (SQLException search_record_error) {
			search_record_error.printStackTrace();
			return null;
		}
		
	}
	
	public List<MedicalRecord> Search_stored_record_by_test(Integer test_id) {
		List<MedicalRecord> records = new LinkedList<MedicalRecord>();
		try {
			String SQL_code = "SELECT * FROM medical_record WHERE bitalino_test_id LIKE ?";
			PreparedStatement template = this.sqlite_connection.prepareStatement(SQL_code);
			template.setInt(1, test_id);
			ResultSet rs = template.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("medicalRecord_id");
				Date date = rs.getDate("record_date");
				int referenceNumber = rs.getInt("reference_number");
				Integer bitalino_test_id = rs.getInt("bitalino_test_id");
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list");
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id, symptoms_list));
			}
			return records;
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
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id, symptoms_list));
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
				List<Symptom> symptoms_list = (List<Symptom>) rs.getArray("symptoms_list"); // PUEDE QUE ESTO VAYA A DAR UN ERROR CON EL TIPO DE DATO DE LA TABLA medical_record
				records.add(new MedicalRecord(id, date, referenceNumber, bitalino_test_id, symptoms_list));
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
	
}
