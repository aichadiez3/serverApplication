package interfaces;

import java.sql.Date;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import pojos.Doctor;
import pojos.EcgTest;
import pojos.EdaTest;
import pojos.MedicalRecord;
import pojos.Patient;
import pojos.PhysicalTest;
import pojos.PsychoTest;
import pojos.Symptom;
import pojos.User;

public interface Interface {
	
	public Integer Insert_new_user(String user_name, String password, String email);
	public Integer Insert_new_patient(Integer user_id, String name, String surname);
	public Integer Insert_new_doctor(Integer user_id, String name);
	public Integer Insert_new_medical_record(Date record_date, Integer reference_number, Integer bitalino_test_id);
	public Integer Insert_new_ecg(LinkedList<Integer> ecg_values, Integer test_id);
	public Integer Insert_new_eda(LinkedList<Integer> eda_values, Integer test_id);
	public Integer Insert_new_psycho_test(LinkedList<Boolean> positive_res, LinkedList<Boolean> negative_res, Integer medicalRecord_id);
	public Integer Insert_new_physical_test(Integer saturation, Integer pulse, Integer breathingRate,Integer medicalRecord_id);
	public Integer Insert_new_bitalino_test();
	public Integer Insert_new_insurance(String company_name);
	
	public void Change_password(String password, Integer user_id);
	public boolean Update_patient_info(Integer patient_id, String name, String surname, LocalDate birth_date, Integer age, Integer height, Integer weight, String gender, Integer telephone, Integer insurance_id);
	
	public Patient Search_stored_patient_by_id(Integer patient_id);
    public Integer Search_stored_patient_by_user_id(Integer user_id);
	public MedicalRecord Search_stored_record_by_id(Integer record_id);
	public List<MedicalRecord> Search_stored_record_by_test(Integer test_id);
	public Symptom Search_symptom_by_id(Integer symptom_id);
	public Integer Search_stored_user_by_userName(String user_name);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	
	
}
