package ru.salnikov.magnit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Main {
	
	private static String DB_URL = "jdbc:postgresql://127.0.0.1:5432/TestMagnit";
	private static String USER = "postgres";
	private static String PASS = "admin";
	
	private static Integer N;
	

	
	public static void main(String[] argv) {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			setN(Integer.parseInt(reader.readLine()));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
			e.printStackTrace();
			return;
		}
	 
		Connection connection = null;
		ArrayList<Integer> values = new ArrayList<Integer>();
		try {
			connection = DriverManager.getConnection(DB_URL, USER, PASS);
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT * FROM TEST");
			if (rs.next())
				statement.executeUpdate("TRUNCATE TABLE TEST");

			statement.executeUpdate("INSERT INTO TEST(FIELD) VALUES (generate_series(1, "+ N + "))");
//			for (int i = 1; i < N + 1; i++) {
//				statement.executeUpdate("INSERT INTO TEST(FIELD) VALUES (" + i + ")");
//			}
			rs = statement.executeQuery("SELECT * FROM TEST");
			while (rs.next())
				values.add(rs.getInt("field"));
			
			rs.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			System.out.println("Connection Failed");
			e.printStackTrace();
			return;
		}		
		XMLBilder.bild(values, "1.xml");
		XMLBilder.convent("1.xml", "2.xml", "2.xsl");
		System.out.println("Àðèôìåòè÷åñêàÿ ñóììà çíà÷åíèé âñåõ àòðèáóòîâ field: " + Sum.sumFields("2.xml"));
	  }
	
	public Main() {}
	
	public static String getDB_URL() {
		return DB_URL;
	}

	public static void setDB_URL(String dB_URL) {
		DB_URL = dB_URL;
	}

	public static String getUSER() {
		return USER;
	}

	public static void setUSER(String uSER) {
		USER = uSER;
	}

	public static String getPASS() {
		return PASS;
	}

	public static void setPASS(String pASS) {
		PASS = pASS;
	}

	public static Integer getN() {
		return N;
	}

	public static void setN(Integer n) {
		N = n;
	}
}
