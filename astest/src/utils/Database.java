/**
 * 
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

/**
 * @author kishorekalapala
 * @project mas
 * @class Database.java
 */
public class Database {

	public final String driver = "com.mysql.jdbc.Driver";

	// String dburl = "jdbc:mysql://localhost:3306/";
	// String dbName = "as";
	// String driver = "com.mysql.jdbc.Driver";
	// String userName = "root";
	// String password = "123456";

	/*
	 * public Database() throws Exception {
	 * 
	 * Class.forName(driver).newInstance(); Connection conn =
	 * DriverManager.getConnection(dburl + dbName, userName, password);
	 * 
	 * Statement st = (Statement) conn.createStatement(); int userid = 12;
	 * ResultSet res = st.executeQuery("SELECT * FROM users where id=" +
	 * userid);
	 * 
	 * while (res.next()) { int id = res.getInt("id"); String msg =
	 * res.getString("email"); System.out.println(id + "\t" + msg); }
	 * 
	 * conn.close();
	 * 
	 * }
	 */

	/*
	 * public run(String dbUrl, String userName, String passWord, String dbName)
	 * { try { Class.forName(driver).newInstance(); Connection conn =
	 * DriverManager.getConnection(dbUrl + dbName, userName, password);
	 * 
	 * } catch (Exception e) { e.printStackTrace();
	 * 
	 * } }
	 */

	public String run(String dbUrl, String userName, String passWord,
			String dbName, String tableName, String whereColoumn,
			String whereColumnValue) {
		int id;
		String msg = null;
		String result = null;
		try {
			String driver = "com.mysql.jdbc.Driver";
			Class.forName(driver).newInstance();

			Connection conn = DriverManager.getConnection(dbUrl + dbName,
					userName, passWord);
			Statement st = (Statement) conn.createStatement();

			// ResultSet res =
			// st.executeQuery("SELECT * FROM "+tableName+" where"+whereColoumn+" ="
			// +whereColumnValue);
			ResultSet res = st.executeQuery("SELECT * FROM " + tableName
					+ " where " + whereColoumn + " =");
			while (res.next()) {
				id = res.getInt("id");
				msg = res.getString("email");
				// System.out.println(id + "\t" + msg);
				result = id + "," + msg;
			}
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;

	}

	public String run(String dbUrl, String userName, String passWord,
			String dbName, String tableName, String whereColoumn,
			int whereColumnValue) {
		int id;
		String msg = null;
		String result = null;

		try {
			Class.forName(driver).newInstance();
			Connection conn = DriverManager.getConnection(dbUrl + dbName,
					userName, passWord);
			Statement st = (Statement) conn.createStatement();

			ResultSet res = st.executeQuery("SELECT * FROM " + tableName
					+ " where " + whereColoumn + " = " + whereColumnValue);

			while (res.next()) {
				id = res.getInt("id");
				msg = res.getString("email");
				// System.out.println(id + "\t" + msg);
				result = id + "," + msg;
			}

			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;

	}

	/*
	 * private String tableName(String tableName) {
	 * 
	 * return null;
	 * 
	 * }
	 */
}
