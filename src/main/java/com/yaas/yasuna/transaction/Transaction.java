package com.yaas.yasuna.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Transaction {
	private Connection connection = null;

	private static final String DRIVER_NAME = "org.mariadb.jdbc.Driver";
	private static final String DRIVER_URL = "jdbc:mariadb://localhost:3306/gtd";
	private static final String NAME = "root";
	private static final String PASSWORD = "root";

	public Connection getConnection() {

		 try {
			Class.forName(DRIVER_NAME);

		connection = DriverManager.getConnection(DRIVER_URL,NAME,PASSWORD);
		connection.setAutoCommit(false);

		return connection;

		 }catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}catch(SQLException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

}
