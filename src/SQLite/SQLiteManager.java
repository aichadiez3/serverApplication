package SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLiteManager {
	private Connection sqlite_connection;
	private SQLiteMethods methods;
	
	public SQLiteManager() {
		
	}

	
	public SQLiteMethods getMethods() {
		return methods;
	}


	public void setMethods(SQLiteMethods methods) {
		this.methods = methods;
	}


	public Connection getSqlite_connection() {
		return sqlite_connection;
	}


	public void setSqlite_connection(Connection sqlite_connection) {
		this.sqlite_connection = sqlite_connection;
	}


	public boolean Connect() {
		// TODO Auto-generated method stub
		try {
			Class.forName("org.sqlite.JDBC");
			this.sqlite_connection = DriverManager.getConnection("jdbc:sqlite:./db/database.db");//hay que poner nuestra database
			sqlite_connection.createStatement().execute("PRAGMA foreign_keys=ON");
			this.methods = new SQLiteMethods(sqlite_connection);
			
			return true;

		} catch (ClassNotFoundException | SQLException connection_error) {
			connection_error.printStackTrace();
			return false;
		}
	}


	public boolean CreateTables() {
		try {
			Statement stmt1 = sqlite_connection.createStatement();
			String sql0 = "CREATE TABLE user " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " user_name TEXT NOT NULL UNIQUE, " + " password TEXT NOT NULL, "+" email TEXT NOT NULL UNIQUE)";
			stmt1.execute(sql0);
			
			stmt1 = sqlite_connection.createStatement();
			String sql1 = "CREATE TABLE patient " + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, "
					+ " surname TEXT NOT NULL, " + " birth_date DATETIME default NULL, " + " age INTEGER default NULL, " + " telephone INTEGER default NULL, "
					+ " height INTEGER default NULL, " + " weight INTEGER default NULL, " + " gender TEXT default NULL, "
					+ " insurance_id FOREING KEY REFERENCES insurance(insurance_id), " 
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt1.execute(sql1);
			
			stmt1 = sqlite_connection.createStatement();
			String sql2 = "CREATE TABLE symptom " + "(symptom_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " weight INTEGER default 0, "
					+ " record_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt1.execute(sql2);
			
			stmt1 = sqlite_connection.createStatement();
			String sql3 = "CREATE TABLE medical_record " + "(medicalRecord_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " reference_number INTEGER UNIQUE, " + " record_date DATETIME NOT NULL, " + " bitalino_test_id FOREING KEY REFERENCES bitalino_test(test_id), "
					+ " symptoms_list TEXT default NULL, "
					+ " patient_id FOREING KEY REFERENCES patient(patient_id) ON UPDATE RESTRICT ON DELETE CASCADE)";
			stmt1.execute(sql3);
			
			stmt1 = sqlite_connection.createStatement();
			String sql4 = "CREATE TABLE bitalino_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT)";
			stmt1.execute(sql4);
			
			stmt1 = sqlite_connection.createStatement();
			String sql5 = "CREATE TABLE ecg_test " + "(ecg_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " ecg_values TEXT default NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt1.execute(sql5);
			
			stmt1 = sqlite_connection.createStatement();
			String sql6 = "CREATE TABLE eda_test " + "(eda_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " eda_values TEXT default NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt1.execute(sql6);
			
			stmt1 = sqlite_connection.createStatement();
			String sql7 = "CREATE TABLE insurance " + "(insurance_id INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ " name TEXT NOT NULL)";
			stmt1.execute(sql7);
			
			stmt1 = sqlite_connection.createStatement();
			String sql8 = "CREATE TABLE doctor " + "(doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " telephone INTEGER default NULL, " + " insurance_id FOREING KEY REFERENCES insurance(insurance_id)";
			stmt1.execute(sql8);
			
			stmt1 = sqlite_connection.createStatement();
			String sql9 = "CREATE TABLE psycho_test " + "(queries_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " positive_res TEXT NOT NULL, " + " negative_res TEXT NOT NULL, "
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt1.execute(sql9);
			
			stmt1 = sqlite_connection.createStatement();
			String sql13 = "CREATE TABLE physical_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " saturation INTEGER default NULL, " + " pulse INTEGER default NULL, "
					+ " breathingRate INTEGER default 0, "
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt1.execute(sql13);
			
			// -----------> ManyToMany relation tables go here
			stmt1 = sqlite_connection.createStatement();
			String sql10 = "CREATE TABLE patient_doctor " + "(patient_id INTEGER REFERENCES patient(patient_id), "
					+ " doctor_id INTEGER REFERENCES doctor(doctor_id),"
					+ " PRIMARY KEY (patient_id, doctor_id))";
			stmt1.execute(sql10);
		
			stmt1 = sqlite_connection.createStatement();
			String sql11 = "CREATE TABLE medicalRecord_symptom " + "(medicalRecord_id INTEGER REFERENCES medical_record(medicalRecord_id), "
					+ " symptom_id INTEGER REFERENCES symptom(symptom_id),"
					+ " PRIMARY KEY (medicalRecord_id, symptom_id))";
			stmt1.execute(sql11);
			
			stmt1 = sqlite_connection.createStatement();
			String sql12 = "CREATE TABLE medicalRecord_bitalino " + "(medicalRecord_id INTEGER REFERENCES medical_record(medicalRecord_id), "
					+ " test_id INTEGER REFERENCES bitalino_test(test_id),"
					+ " PRIMARY KEY (medicalRecord_id, test_id))";
			stmt1.execute(sql12);
			stmt1.close();
			
			return true;
		}catch (SQLException tables_error) {
			if (tables_error.getMessage().contains("already exists")) {
				System.out.println("Ya existe la DB");
				return false;
			} else {
				System.out.println("No puedo crear tablas");
				tables_error.printStackTrace();
				return false;
			}
		}
	}
	
	
	// -------> CLOSE DATABASE CONNECTION <---------
	
	public boolean Close_connection() {
		// TODO Auto-generated method stub
		try {
			this.sqlite_connection.close();
			return true;
		} catch (SQLException close_connection_error) {
			close_connection_error.printStackTrace();
			return false;
		}
	}

}
