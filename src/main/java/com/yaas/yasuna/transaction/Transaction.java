package com.yaas.yasuna.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Transaction {
	private Connection connection = null;

	private static final String DRIVER_NAME = "org.mariadb.jdbc.Driver";

	public Connection getConnection() {
		if (System.getenv("RDS_HOSTNAME") != null) {
			 try {
					Class.forName(DRIVER_NAME);
				      String dbName = System.getenv("RDS_DB_NAME");
				      String userName = System.getenv("RDS_USERNAME");
				      String password = System.getenv("RDS_PASSWORD");
				      String hostname = System.getenv("RDS_HOSTNAME");
				      String port = System.getenv("RDS_PORT");
				      String jdbcUrl = "jdbc:postgresql://" + hostname + ":" + port + "/" + dbName + "?user=" + userName + "&password=" + password;
				connection = DriverManager.getConnection(jdbcUrl);
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
