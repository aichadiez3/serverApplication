package interfaces;

import java.sql.Date;
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
	
	public User Insert_new_user(String user_name, String password, String email);
	public Patient Insert_new_patient(Integer user_id, String name, String surname);
	public Doctor Insert_new_doctor(Integer user_id, String namer);
	public Integer Insert_new_medical_record(Date record_date, Integer reference_number, Integer bitalino_test_id);
	public Integer Insert_new_ecg(LinkedList<Integer> ecg_values, Integer test_id);
	public Integer Insert_new_eda(LinkedList<Integer> eda_values, Integer test_id);
	public Integer Insert_new_psycho_test(LinkedList<Boolean> positive_res, LinkedList<Boolean> negative_res, Integer medicalRecord_id);
	public Integer Insert_new_physical_test(Integer saturation, Integer pulse, Integer breathingRate,Integer medicalRecord_id);
	
	
	public void Change_password(String password, Integer user_id);
	public boolean Update_patient_info(Integer user_id);
	
	public MedicalRecord Search_stored_record_by_id(Integer record_id);
	public List<MedicalRecord> Search_stored_record_by_test(Integer test);
	public Symptom Search_symptom_by_id(Integer symptom_id);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	
	
}
