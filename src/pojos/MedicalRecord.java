package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class MedicalRecord implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer patient_id;
	private Integer medicalRecord_id;
	private String recordDate;
	private Integer referenceNumber;
	private Integer bitalino_test_id;
	private Integer ecg_id;
	private Integer eda_id;
	private List<Integer> symptom_list = new LinkedList<Integer>();

	
	public MedicalRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicalRecord(String recordDate, Integer referenceNumber, Integer patient_id) {
		super();
		this.recordDate = recordDate;
		this.referenceNumber = referenceNumber;
		this.patient_id = patient_id;
	}

	public MedicalRecord(Integer medicalRecord_id, String recordDate, Integer referenceNumber, Integer patient_id) {
		super();
		this.medicalRecord_id = medicalRecord_id;
		this.recordDate = recordDate;
		this.referenceNumber = referenceNumber;
		this.patient_id = patient_id;
	}
	
	public Integer getMedicalRecord_id() {
		return medicalRecord_id;
	}

	public void setMedicalRecord_id(Integer medicalRecord_id) {
		this.medicalRecord_id = medicalRecord_id;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
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

	
	
	
	
	
	
	
}
