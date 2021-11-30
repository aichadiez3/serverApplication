package interfaces;

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
	public Patient Insert_new_patient(User user);
	public Doctor Insert_new_doctor(User user);
	public Integer Insert_new_medical_record(MedicalRecord record);
	public Integer Insert_new_ecg(EcgTest ecg);
	public Integer Insert_new_eda(EdaTest eda);
	public Integer Insert_new_psycho_test(PsychoTest psycho);
	public Integer Insert_new_physical_test(PhysicalTest physical);
	
	
	public void Change_password(String password, Integer user_id);
	public boolean Update_patient_info(Patient patient);
	
	public MedicalRecord Search_stored_record_by_id(Integer record_id);
	public List<MedicalRecord> Search_stored_record_by_test(Integer test);
	public Symptom Search_symptom_by_id(Integer symptom_id);
	
	public List<MedicalRecord> Search_stored_record_by_date_ascendent();
	public List<MedicalRecord> Search_stored_record_by_date_descendent();
	
	public List<Symptom> Search_all_symptoms_from_record(Integer record_id);
	public List<User> List_all_users();
	
	
}
