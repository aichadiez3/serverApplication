package pojos;

import java.io.Serializable;
import java.util.Objects;

public class Doctor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer doctor_id;
	private String name;
	private Integer telephone;
	
	public Integer getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(Integer doctor_id) {
		this.doctor_id = doctor_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTelephone() {
		return telephone;
	}
	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}
	public Doctor(Integer doctor_id, String name, Integer telephone) {
		super();
		this.doctor_id = doctor_id;
		this.name = name;
		this.telephone = telephone;
	}
	public Doctor() {
		super();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(doctor_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Doctor other = (Doctor) obj;
		return Objects.equals(doctor_id, other.doctor_id);
	}
	
	
		
	
	
}
