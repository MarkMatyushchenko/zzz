package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConfigurationManager {

	private static MySqlConfigurationManager INSTANCE = null;

	private String url;
	private String login;
	private String password;

	private MySqlConfigurationManager() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static MySqlConfigurationManager getInstance() {
		if (INSTANCE == null) {
			synchronized (MySqlConfigurationManager.class) {
				INSTANCE = new MySqlConfigurationManager();
			}
		}
		return INSTANCE;
	}

	public static MySqlConfigurationManager getInstance(String url, String login, String password) {
		if (INSTANCE == null) {
			synchronized (MySqlConfigurationManager.class) {
				INSTANCE = new MySqlConfigurationManager();
				INSTANCE.url = url;
				INSTANCE.login = login;
				INSTANCE.password = password;
			}
		}
		return INSTANCE;
	}

	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, login, password);
	}

}
