package prueba;

import SQLite.SQLiteMethods;

import java.sql.SQLException;

import SQLite.SQLiteManager;
import pojos.*;

public class prueba {
public static void main(String[] args) throws SQLException {
		
		SQLiteManager manager = new SQLiteManager();
		boolean everything_ok = manager.Connect();
		boolean tables_ok = manager.CreateTables();
		System.out.println(manager.getSqlite_connection().getWarnings());
}
}           