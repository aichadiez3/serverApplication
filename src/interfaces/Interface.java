package interfaces;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

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

public interface Interface {
	
	public Integer Insert_new_user(String user_name, String password, String email);
	public Integer Insert_new_patient(Integer user_id, String name, String surname);
	public Integer Insert_new_doctor(String name, String telephone);
	public Integer Insert_new_medical_record(Date record_date, Integer reference_number, Integer patient_id);
	public Integer Insert_new_ecg(String ecg_values, Integer test_id);
	public Integer Insert_new_eda(String eda_values, Integer test_id);
	public Integer Insert_new_psycho_test(LinkedList<Boolean> positive_res, LinkedList<Boolean> negative_res, Integer medicalRecord_id);
	public Integer Insert_new_physical_test(Integer saturation, Integer pulse, Integer breathingRate,Integer medicalRecord_id);
	public Integer Insert_new_bitalino_test(Integer medicalRecord_id);
	public Integer Insert_new_insurance(String company_name);
	
	public boolean Update_user_info(String password, String email, Integer user_id);
	public boolean Update_patient_info(Integer patient_id, String name, String surname, Date birth_date, Integer age, Integer height, Integer weight, String gender, Integer telephone, Integer insurance_id);
	
	public Patient Search_stored_patient_by_id(Integer patient_id);
    public Integer Search_stored_patient_by_user_id(Integer user_id);
	public Integer Search_stored_user_by_userName(String user_name);
	public boolean Search_existent_email(String email);
	public String Get_user_password (String user_name);
	
	public Symptom Search_symptom_by_id(Integer symptom_id);
	public Insurance_company Search_insurance_by_name(String insurance_name);
	public Integer Search_associated_ecg(Integer bitalino_id);
	public Integer Search_associated_eda(Integer bitalino_id);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	public List<MedicalRecord> List_all_medical_records();
	
}
