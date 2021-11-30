package pojos;
import java.io.Serializable;
import java.util.Objects;

public class Symptom implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer symptom_id;
	private String name;
	private Integer weight;
	
	
	public Symptom() {
		super();
	}

	public Symptom(String name, Integer weight) {
		super();
		this.name = name;
		this.weight = weight;
	}
	
	public Integer getSymptom_id() {
		return symptom_id;
	}

	public void setSymptom_id(Integer symptom_id) {
		this.symptom_id = symptom_id;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(symptom_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Symptom other = (Symptom) obj;
		return Objects.equals(symptom_id, other.symptom_id);
	}
	
	



	

	
}