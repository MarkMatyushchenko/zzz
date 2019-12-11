package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MySqlClientDAO extends MySqlDAO<Client> {

	public MySqlClientDAO(MySqlConfigurationManager configurationManager) {
		super(configurationManager);
	}

	@Override
	String getTableName() {
		return "clients";
	}

	@Override
	List<String> getFields() {
		return Arrays.asList("login", "token", "email", "firstName", "lastName", "phoneNumber");
	}

	@Override
	void fillPreparedStatement(PreparedStatement preparedStatement, Client object) throws SQLException {
		preparedStatement.setString(1, object.getLogin());
		preparedStatement.setString(2, object.getToken());
		preparedStatement.setString(3, object.getEmail());
		preparedStatement.setString(4, object.getFirstName());
		preparedStatement.setString(5, object.getLastName());
		preparedStatement.setString(6, object.getPhoneNumber());
	}

	@Override
	Client getObject(ResultSet resultSet) throws SQLException {
		return new Client(
				resultSet.getString(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getString(4),
				resultSet.getString(5),
				resultSet.getString(6)
		);
	}
}

