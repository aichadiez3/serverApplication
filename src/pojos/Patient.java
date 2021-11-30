package pojos;

import java.io.Serializable;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Patient implements Serializable {



	private static final long serialVersionUID = 1L;

	private Integer patient_id;
	//private User user;
	private Integer user_id;
	private String name;
	private String surname;
	private LocalDate birth_date;
	private Integer age;
	private Integer height;
	private Integer weight;
	private String gender;
	private Integer telephone;
	private Insurance_company insurance;

	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Patient(String name, String surname, LocalDate birth_date, Integer height, Integer weight, String gender, Integer telephone, Insurance_company insurance) {
		super();
		this.name = name;
		this.surname = surname;
		this.birth_date = birth_date;
		this.height = height;
		this.weight = weight;
		this.gender = gender;
		this.telephone = telephone;
		this.insurance = insurance;
	}

	public Integer getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(Integer patient_id) {
		this.patient_id = patient_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getBirth_date() {
		Date birthday = convert_LocalDate_to_Date(birth_date);
		return birthday;
	}
	
	
	public void setBirth_date(Date birth_date) {
		Instant instant = birth_date.toInstant();
		ZonedDateTime zone =instant.atZone(ZoneId.systemDefault());
		LocalDate givenDate = zone.toLocalDate();
		this.birth_date = givenDate;
	}
	
	public Date convert_LocalDate_to_Date(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		Period period = Period.between(this.birth_date, LocalDate.now());
		this.age = period.getYears();
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public Insurance_company getInsurance() {
		return insurance;
	}

	public void setInsurance(Insurance_company insurance) {
		this.insurance = insurance;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(patient_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		return Objects.equals(patient_id, other.patient_id);
	}
	

	@Override
	public String toString() {
		return "Patient [patient_id=" + patient_id + ", user_id=" + user_id + ", name=" + name + ", surname=" + surname
				+ ", birth_date=" + birth_date + ", age=" + age + ", height=" + height + ", weight=" + weight
				+ ", gender=" + gender + ", telephone=" + telephone + ", insurance=" + insurance + "]";
	}
	
	




}
