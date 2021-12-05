package pojos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;


public class EcgTest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer ecg_id;
	private Integer test_id;
	private String ecg_values;
	
	public EcgTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EcgTest(String ecg_values) {
		super();
		this.ecg_values = ecg_values;
	}

	public Integer getEcg_id() {
		return ecg_id;
	}

	public void setEcg_id(Integer ecg_id) {
		this.ecg_id = ecg_id;
	}

	public String getEcg_values() {
		return ecg_values;
	}

	public void setEcg_values(String ecg_values) {
		this.ecg_values = ecg_values;
	}
	
	public Integer getTest_id() {
		return test_id;
	}

	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(ecg_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EcgTest other = (EcgTest) obj;
		return Objects.equals(ecg_id, other.ecg_id);
	}

	

	
	
}
