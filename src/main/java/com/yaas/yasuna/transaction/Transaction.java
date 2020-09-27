package com.yaas.yasuna.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Transaction {
	private Connection connection = null;

	private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	public Connection getConnection() {
		if (System.getProperty("RDS_HOSTNAME") != null) {
			 try {
					Class.forName(DRIVER_NAME);
				      String dbName = System.getProperty("RDS_DB_NAME");
				      String userName = System.getProperty("RDS_USERNAME");
				      String password = System.getProperty("RDS_PASSWORD");
				      String hostname = System.getProperty("RDS_HOSTNAME");
				      String port = System.getProperty("RDS_PORT");
				      String jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName + "?serverTimezone=JST";
				connection = DriverManager.getConnection(jdbcUrl, userName, password);
				connection.setAutoCommit(false);


				return connection;

				 }catch (ClassNotFoundException e) {
					e.printStackTrace();
					throw new IllegalStateException(e);
				}catch(SQLException e) {
					e.printStackTrace();
					throw new IllegalStateException(e);
				}
			} else {
				return null;
			}
		}
}
