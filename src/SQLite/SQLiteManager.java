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
			//CreateTables();
			return true;
			// create Managers

		} catch (ClassNotFoundException | SQLException connection_error) {
			connection_error.printStackTrace();
			return false;
		}
	}


	public boolean CreateTables() {
		try {
			Statement stmt0 = sqlite_connection.createStatement();
			String sql0 = "CREATE TABLE user " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " user_name TEXT NOT NULL UNIQUE, " + " password TEXT NOT NULL, "+" email TEXT NOT NULL UNIQUE)";
			stmt0.execute(sql0);
			stmt0.close();
			
			Statement stmt1 = sqlite_connection.createStatement();
			String sql1 = "CREATE TABLE patient " + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, "
					+ " surname TEXT NOT NULL, " + " birthdate DATETIME default NULL, " + " age INTEGER default NULL, " + " telephone INTEGER default NULL, "
					+ " height INTEGER default NULL, " + " weight INTEGER default NULL, " + " gender TEXT default NULL, "
					+ " insurance_id FOREING KEY REFERENCES insurance(insurance_id), " 
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt1.execute(sql1);
			stmt1.close();
			
			
			Statement stmt2 = sqlite_connection.createStatement();
			String sql2 = "CREATE TABLE symptom " + "(symptom_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " weight INTEGER default 0, "
					+ " record_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt2.execute(sql2);
			stmt2.close();
			
			Statement stmt3 = sqlite_connection.createStatement();
			String sql3 = "CREATE TABLE medical_record " + "(medicalRecord_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " reference_number INTEGER UNIQUE, " + " record_date DATETIME NOT NULL, " + " bitalino_test_id FOREING KEY REFERENCES bitalino_test(test_id), "
					+ " symptoms_list TEXT default NULL, "
					+ " patient_id FOREING KEY REFERENCES patient(patient_id) ON UPDATE RESTRICT ON DELETE CASCADE)";
			stmt3.execute(sql3);
			stmt3.close();
			
			Statement stmt4 = sqlite_connection.createStatement();
			String sql4 = "CREATE TABLE bitalino_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT)";
			stmt4.execute(sql4);
			stmt4.close();
			
			
			Statement stmt5 = sqlite_connection.createStatement();
			String sql5 = "CREATE TABLE ecg_test " + "(ecg_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " values TEXT default NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt5.execute(sql5);
			stmt5.close();
			
			Statement stmt6 = sqlite_connection.createStatement();
			String sql6 = "CREATE TABLE eda_test " + "(eda_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " values TEXT default NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt6.execute(sql6);
			stmt6.close();
			
			
			Statement stmt7 = sqlite_connection.createStatement();
			String sql7 = "CREATE TABLE insurance " + "(insurance_id INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ " name TEXT NOT NULL)";
			stmt7.execute(sql7);
			stmt7.close();
			
			Statement stmt8 = sqlite_connection.createStatement();
			String sql8 = "CREATE TABLE doctor " + "(doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " telephone INTEGER default NULL, " + " insurance_id FOREING KEY REFERENCES insurance(insurance_id), "
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt8.execute(sql8);
			stmt8.close();
			
			
			Statement stmt9 = sqlite_connection.createStatement();
			String sql9 = "CREATE TABLE psycho_test " + "(queries_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " positive_res TEXT NOT NULL, " + " negative_res TEXT NOT NULL, "
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt9.execute(sql9);
			stmt9.close();
			
			Statement stmt13 = sqlite_connection.createStatement();
			String sql13 = "CREATE TABLE physical_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " saturation INTEGER default NULL, " + " pulse INTEGER default NULL, "
					+ " breathingRate INTEGER default 0, "
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt13.execute(sql13);
			stmt13.close();
			
			
			
			// -----------> ManyToMany relation tables go here
			Statement stmt10 = sqlite_connection.createStatement();
			String sql10 = "CREATE TABLE patient_doctor " + "(patient_id INTEGER REFERENCES patient(patient_id), "
					+ " doctor_id INTEGER REFERENCES doctor(doctor_id),"
					+ " PRIMARY KEY (patient_id, doctor_id))";
			stmt10.execute(sql10);
			stmt10.close();
		
			Statement stmt11 = sqlite_connection.createStatement();
			String sql11 = "CREATE TABLE medicalRecord_symptom " + "(medicalRecord_id INTEGER REFERENCES medical_record(medicalRecord_id), "
					+ " symptom_id INTEGER REFERENCES symptom(symptom_id),"
					+ " PRIMARY KEY (medicalRecord_id, symptom_id))";
			stmt11.execute(sql11);
			stmt11.close();
			
			Statement stmt12 = sqlite_connection.createStatement();
			String sql12 = "CREATE TABLE medicalRecord_bitalino " + "(medicalRecord_id INTEGER REFERENCES medical_record(medicalRecord_id), "
					+ " test_id INTEGER REFERENCES bitalino_test(test_id),"
					+ " PRIMARY KEY (medicalRecord_id, test_id))";
			stmt12.execute(sql12);
			stmt12.close();
			
			return true;
		}catch (SQLException tables_error) {
			if (tables_error.getMessage().contains("already exists")) {
				System.out.println("Ya existe la DB");
			} else {
				tables_error.printStackTrace();
				return false;
			}
			System.out.println("No puedo crear tablas");
			return false;
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
