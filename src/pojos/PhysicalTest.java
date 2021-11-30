package pojos;

import java.io.Serializable;
import java.util.Objects;

public class PhysicalTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer test_id;
	private Integer saturation;
	private Integer pulse;
	private Integer medicalRecord_id;
	private Integer breathingRate;
	
	public Integer getSaturation() {
		return saturation;
	}
	public void setSaturation(Integer saturation) {
		this.saturation = saturation;
	}
	public Integer getPulse() {
		return pulse;
	}
	public void setPulse(Integer pulse) {
		this.pulse = pulse;
	}
	public Integer getMedicalRecord_id() {
		return medicalRecord_id;
	}
	public void setMedicalRecord_id(Integer medicalRecord_id) {
		this.medicalRecord_id = medicalRecord_id;
	}
	public Integer getBreathingRate() {
		return breathingRate;
	}
	public void setBreathingRate(Integer breathingRate) {
		this.breathingRate = breathingRate;
	}
	public Integer getTest_id() {
		return test_id;
	}
	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}
	@Override
	public int hashCode() {
		return Objects.hash(test_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhysicalTest other = (PhysicalTest) obj;
		return Objects.equals(test_id, other.test_id);
	}
	
	
}
