package interfaces;

import java.util.LinkedList;
import java.util.List;

import pojos.Insurance_company;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.Symptom;
import pojos.User;

public interface Interface {
	
	public void Insert_default_elements_toDB();
	public Integer Insert_new_user(String user_name, String password, String email);
	public Integer Insert_new_patient(Integer user_id, String name, String surname);
	public Integer Insert_new_doctor(String name, String telephone, Integer insurance_id);
	public Integer Insert_new_medical_record(String record_date, Integer reference_number, Integer patient_id);
	public Integer Insert_new_ecg(String ecg_values, Integer test_id);
	public Integer Insert_new_eda(String eda_values, Integer test_id);
	public Integer Insert_new_psycho_test(LinkedList<String> positive_res, LinkedList<String> negative_res, LinkedList<String> symptoms, Integer medicalRecord_id);
	public Integer Insert_new_physical_test(Integer saturation, Integer pulse, Integer breathingRate,Integer medicalRecord_id);
	public Integer Insert_new_bitalino_test();
	public Integer Insert_new_insurance(String company_name);
	
	public boolean Update_user_info(String password, String email, Integer user_id);
	public void Update_patient_info(Integer patient_id, String birth_date, Integer height, Integer weight, String gender, Integer telephone, Integer insurance_id);
	public boolean Update_medical_record_with_bitalino(Integer bitalino_id, Integer medRecord_id);
	
	public Patient Search_stored_patient_by_id(Integer patient_id);
    public Integer Search_stored_patient_by_user_id(Integer user_id);
	public Integer Search_stored_user_by_userName(String user_name);
	public String Search_doctor_by_insurance_id(Integer insurance_id);
	public boolean Search_existent_email(String email);
	public Integer Search_existent_reference_number(Integer number);
	public String Get_user_password (String user_name);
	public Integer Search_insurance_by_name(String insurance_name);
	public String Search_insurance_by_id(Integer insurance_id);
	public String Search_associated_ecg(Integer bitalino_id);
	public String Search_associated_eda(Integer bitalino_id);
	
	public List<MedicalRecord> List_all_medical_records();
	public List<Insurance_company> List_all_insurances();
	
}
