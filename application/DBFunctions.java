package application;

import java.sql.Connection;
import java.sql.DriverManager;

//This class is in charge of connecting to the DB on your local device
public class DBFunctions {

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost:3306/pcez";
			String username = "root";
			String password = "Triangle*123";
			Class.forName(driver);

			Connection conn = DriverManager.getConnection(url, username, password);
		
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		return null;
	}
}
