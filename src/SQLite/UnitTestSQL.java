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
		List<MedicalRecord> lmr = sqlMethods.List_all_medical_records();
		for(MedicalRecord mr : lmr)
		{
			System.out.println(mr);
		}
		/*
		Integer a = sqlMethods.Search_existent_reference_number(1382742397);
		System.out.println(a);
		*/
		
	}
}
