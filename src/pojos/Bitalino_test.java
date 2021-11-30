package pojos;
import java.io.Serializable;
import java.util.Objects;
public class Bitalino_test implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer test_id;
	
	public Bitalino_test() {
		super();
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
		Bitalino_test other = (Bitalino_test) obj;
		return Objects.equals(test_id, other.test_id);
	}

	

	
	
}
