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
			Statement stmt0 = sqlite_connection.createStatement();
			String sql0 = "CREATE TABLE user " + "(user_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " user_name TEXT NOT NULL UNIQUE, " + " password TEXT NOT NULL, "+" email TEXT NOT NULL UNIQUE)";
			stmt0.execute(sql0);
			
			Statement stmt1 = sqlite_connection.createStatement();
			String sql1 = "CREATE TABLE patient " + "(patient_id INTEGER PRIMARY KEY AUTOINCREMENT, " + " name TEXT NOT NULL, "
					+ " surname TEXT NOT NULL, " + " birth_date TEXT default NULL, " + " telephone INTEGER default NULL, "
					+ " height INTEGER default NULL, " + " weight INTEGER default NULL, " + " gender TEXT default NULL, "
					+ " insurance_id FOREING KEY REFERENCES insurance(insurance_id), " 
					+ " user_id FOREING KEY REFERENCES user(user_id) ON DELETE CASCADE)";
			stmt1.execute(sql1);
			
			Statement stmt2 = sqlite_connection.createStatement();
			String sql2 = "CREATE TABLE medical_record " + "(medicalRecord_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " record_date TEXT NOT NULL, " + " reference_number INTEGER UNIQUE, "
					+ " bitalino_test_id FOREING KEY REFERENCES bitalino_test(test_id), "
					+ " patient_id FOREING KEY REFERENCES patient(patient_id) ON UPDATE RESTRICT ON DELETE CASCADE)";
			stmt2.execute(sql2);
			
			Statement stmt3 = sqlite_connection.createStatement();
			String sql3 = "CREATE TABLE bitalino_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT)";
			stmt3.execute(sql3);
			
			Statement stmt4 = sqlite_connection.createStatement();
			String sql4 = "CREATE TABLE ecg_test " + "(ecg_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " ecg_root TEXT NOT NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt4.execute(sql4);
			
			Statement stmt5 = sqlite_connection.createStatement();
			String sql5 = "CREATE TABLE eda_test " + "(eda_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " eda_root TEXT NOT NULL, "
					+ " test_id FOREING KEY REFERENCES bitalino_test(test_id))";
			stmt5.execute(sql5);
			
			Statement stmt6 = sqlite_connection.createStatement();
			String sql6 = "CREATE TABLE insurance " + "(insurance_id INTEGER PRIMARY KEY AUTOINCREMENT, " 
					+ " name TEXT NOT NULL)";
			stmt6.execute(sql6);
			
			Statement stmt7 = sqlite_connection.createStatement();
			String sql7 = "CREATE TABLE doctor " + "(doctor_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " name TEXT NOT NULL, " + " telephone INTEGER default NULL, " + " insurance_id FOREING KEY REFERENCES insurance(insurance_id))";
			stmt7.execute(sql7);
			
			Statement stmt8 = sqlite_connection.createStatement();
			String sql8 = "CREATE TABLE psycho_test " + "(queries_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " positive_res TEXT NOT NULL, " + " negative_res TEXT NOT NULL, "
					+ " symptoms TEXT NOT NULL,"
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt8.execute(sql8);
			
			Statement stmt9 = sqlite_connection.createStatement();
			String sql9 = "CREATE TABLE physical_test " + "(test_id INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ " saturation INTEGER default 0, " + " pulse INTEGER default 0, "
					+ " breathingRate INTEGER default 0, "
					+ " medicalRecord_id FOREING KEY REFERENCES medical_record(medicalRecord_id))";
			stmt9.execute(sql9);
			
			return true;
		}catch (SQLException tables_error) {
			if (tables_error.getMessage().contains("already exists")) {
				System.out.println("Database already exists.");
				return false;
			} else {
				System.out.println("Error creating tables! Abort.");
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
