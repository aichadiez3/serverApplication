package pruebasDB;

import SQLite.SQLiteMethods;

import java.sql.SQLException;

import SQLite.SQLiteManager;
import pojos.*;

public class mainDB {
public static void main(String[] args) throws SQLException {
		
		SQLiteManager manager = new SQLiteManager();
		boolean everything_ok = manager.Connect();
		boolean tables_ok = manager.CreateTables();
		System.out.println(manager.getSqlite_connection().getWarnings());
		
		System.out.println(everything_ok + " ");
			
		SQLiteMethods methods = manager.getMethods();
//		User user = methods.Insert_new_user("name", "123", "aa@aaa");
//		Patient patient = methods.Insert_new_patient(user.getUserId(),"Pablo","Carnero");
//		String userName = "name";
//		Integer id = methods.Search_stored_user_by_userName(userName);
//		Patient patient2 = methods.Insert_new_patient(id, "Chochi", "Palizote");
//		System.out.println(methods.List_all_users());
//		Patient patient3 = methods.Search_stored_patient_by_id(2);
//		System.out.println(patient3.getPatient_id());
//		methods.Update_patient_info(patient3.getPatient_id(), "Alvarito", "Redondito", null, null, null, null, null, null, null);
//		Integer user2_id = methods.Search_stored_user_by_userName("name");
//		System.out.println(user2_id);
//		methods.Change_password("telemed", user2_id);
//		User user3 = methods.Insert_new_user("MrSocket","SoyDios","destruyesockets@gmail.com");
//		System.out.println(methods.List_all_users());
		manager.Close_connection();
		
		
	}
}
