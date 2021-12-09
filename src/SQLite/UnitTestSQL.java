package SQLite;

import java.sql.Connection;
import java.util.List;

import pojos.MedicalRecord;

public class UnitTestSQL {
	
	public static void main(String[] args) {
		
		SQLiteManager manager = new SQLiteManager();
        manager.Connect();
		Connection c = manager.getSqlite_connection();
		SQLiteMethods sqlMethods = new SQLiteMethods(c);
		
		boolean email = sqlMethods.Search_existent_email("a");
		Integer id = sqlMethods.Search_stored_user_by_userName("a");
		String pas = sqlMethods.Get_user_password("b");
		id = sqlMethods.Search_stored_patient_by_user_id(id);
		
		//Integer a = sqlMethods.Search_existent_reference_number(1382742397);
		System.out.println(id + pas + email);
		
		
	}
}
