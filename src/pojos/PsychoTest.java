package pojos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class PsychoTest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer queries_id;
	private Integer medicalRecord_id;
	private LinkedList<Boolean> positive_res = new LinkedList<Boolean>();
	private LinkedList<Boolean> negative_res = new LinkedList<Boolean>();
	public Integer getQueries_id() {
		return queries_id;
	}
	public void setQueries_id(Integer queries_id) {
		this.queries_id = queries_id;
	}
	public Integer getMedicalRecord_id() {
		return medicalRecord_id;
	}
	public void setMedicalRecord_id(Integer medicalRecord_id) {
		this.medicalRecord_id = medicalRecord_id;
	}
	public LinkedList<Boolean> getPositive_res() {
		return positive_res;
	}
	public void setPositive_res(LinkedList<Boolean> positive_res) {
		this.positive_res = positive_res;
	}
	public LinkedList<Boolean> getNegative_res() {
		return negative_res;
	}
	public void setNegative_res(LinkedList<Boolean> negative_res) {
		this.negative_res = negative_res;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(queries_id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PsychoTest other = (PsychoTest) obj;
		return Objects.equals(queries_id, other.queries_id);
	}
	
	
}
