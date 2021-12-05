package pojos;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Objects;

public class EdaTest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer eda_id;
	private Integer test_id;
	private String eda_values;
	
	public EdaTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EdaTest(String eda_values) {
		super();
		this.eda_values = eda_values;
	}

	public Integer getEda_id() {
		return eda_id;
	}

	public void setEda_id(Integer eda_id) {
		this.eda_id = eda_id;
	}

	public String getEda_values() {
		return eda_values;
	}

	public void setEda_values(String eda_values) {
		this.eda_values = eda_values;
	}

	public Integer getTest_id() {
		return test_id;
	}

	public void setTest_id(Integer test_id) {
		this.test_id = test_id;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(eda_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EdaTest other = (EdaTest) obj;
		return Objects.equals(eda_id, other.eda_id);
	}

	

	
}
