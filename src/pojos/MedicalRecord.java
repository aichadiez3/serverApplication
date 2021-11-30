package pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class MedicalRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer medicalRecord_id;
	private Date recordDate;
	private Integer referenceNumber;
	private Integer bitalino_test_id;
	private List<Symptom> symptoms_list = new LinkedList<Symptom>();

	
	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(Date recordDate, Integer bitalino_test_id) {
		super();
		this.recordDate = recordDate;
		this.bitalino_test_id = bitalino_test_id;
	}
	
	public MedicalRecord(Date recordDate, Integer bitalino_test_id, List<Symptom> symptoms_list) {
		super();
		this.recordDate = recordDate;
		this.bitalino_test_id = bitalino_test_id;
		this.symptoms_list = symptoms_list;
	}
	
	public MedicalRecord(Integer medicalRecord_id, Date recordDate, Integer referenceNumber,
			Integer bitalino_test_id, List<Symptom> symptoms_list) {
		super();
		this.medicalRecord_id = medicalRecord_id;
		this.recordDate = recordDate;
		this.referenceNumber = referenceNumber;
		this.bitalino_test_id = bitalino_test_id;
		this.symptoms_list = symptoms_list;
	}

	public Integer getMedicalRecord_id() {
		return medicalRecord_id;
	}

	public void setMedicalRecord_id(Integer medicalRecord_id) {
		this.medicalRecord_id = medicalRecord_id;
	}

	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	public Integer getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(Integer referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Integer getBitalinoTestId() {
		return bitalino_test_id;
	}

	public void setBitalinoTestId(Integer bitalino_test_id) {
		this.bitalino_test_id = bitalino_test_id;
	}

	public List<Symptom> getSymptoms_list() {
		return symptoms_list;
	}

	public void setSymptoms_list(List<Symptom> symptoms_list) {
		this.symptoms_list = symptoms_list;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(medicalRecord_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRecord other = (MedicalRecord) obj;
		return Objects.equals(medicalRecord_id, other.medicalRecord_id);
	}

	
	
	
	
	
	public void addSymptom(Symptom symptom) {
		symptoms_list.add(symptom);
	}
	
	public void removeSymptom(Symptom symptom) {
		if(symptoms_list.contains(symptom)) {
			symptoms_list.remove(symptom);
		}
	}
	
	
	
	
	
	
	
}
