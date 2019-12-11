package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import com.github.markmatyushchenko.vt1.repository.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class MySqlDAO<T> implements DAO<T> {

	private MySqlConfigurationManager configurationManager;

	private Connection getConnection() throws SQLException {
		return configurationManager.getConnection();
	}

	public MySqlDAO(MySqlConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	protected String getInsertRequest() {
		return "INSERT INTO `" + getTableName() + "` (" + convertFieldsToRequestForm() + ") VALUES " + convertValuesToRequestForm() + ';';
	}

	protected String getSelectRequest() {
		return "SELECT " + convertFieldsToRequestForm() + " FROM " + getTableName() + ';';
	}

	abstract String getTableName();

	abstract List<String> getFields();

	private String convertFieldsToRequestForm() {
		return getFields().stream()
				.map(x -> '`' + x + '`')
				.collect(Collectors.joining(", "));
	}

	private String convertValuesToRequestForm() {
		return getFields().stream()
				.map(x -> "?")
				.collect(Collectors.joining(",", "(", ")"));
	}

	abstract void fillPreparedStatement(PreparedStatement preparedStatement, T object) throws SQLException;

	abstract T getObject(ResultSet resultSet) throws SQLException;


	public void clear() {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM " + getTableName());
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void save(Iterable<T> objects) {
		objects.forEach(x -> {
			Connection connection = null;
			try {
				connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(getInsertRequest());
				fillPreparedStatement(preparedStatement, x);
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (connection != null) {
					try {
						connection.close();
					} catch (SQLException exc) {
						exc.printStackTrace();
					}
				}
			}
		});
	}

	public Iterable<T> read() {
		List<T> list = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(getSelectRequest());
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				list.add(getObject(resultSet));
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

}
