// Robin Nandi - Spider Key

package com.google.u2f.db;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.u2f.U2FException;

public class DbConnectionManager {
	
	static final String jdbc_driver = "com.mysql.jdbc.Driver";
	static final String db_url = "jdbc:mysql://localhost/testdb";
	static final String db_user = "testuser";
	static final String db_pass = "SirMarlontheQuitePolite";
	
	private String query;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public DbConnectionManager() {
		conn = null;
		stmt = null;
		try {
			Class.forName(jdbc_driver);
			conn = DriverManager.getConnection(db_url, db_user, db_pass);
			stmt = conn.createStatement();
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setQuery(String query) {
		this.query = query;
	}
	
	public void executeQuery() {
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public String returnSingleResult() throws U2FException {
		// Used to verify username and password
		// require single result
		// return password hash
		String result = "";
		int count = 0;
		try {
			rs.last();
			count = rs.getRow();
		} catch (SQLException se) {
			se.printStackTrace();
		}
		if (count == 1) {
			try {
				result = rs.getString(1);
			} catch (SQLException se) {
				se.printStackTrace();
			}
		} else {
			throw new U2FException("Wrong username or password");
		}
		return result;
	}
	
	public void close() {
		try {
		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (SQLException se) {
			se.printStackTrace();
		}
	}
}