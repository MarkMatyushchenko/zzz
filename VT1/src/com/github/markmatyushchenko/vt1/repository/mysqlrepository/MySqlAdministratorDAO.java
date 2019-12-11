package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class MySqlAdministratorDAO extends MySqlDAO<Administrator> {

	public MySqlAdministratorDAO(MySqlConfigurationManager configurationManager) {
		super(configurationManager);
	}

	@Override
	String getTableName() {
		return "administrators";
	}

	@Override
	List<String> getFields() {
		return Arrays.asList("login", "token", "firstName", "lastName");
	}

	@Override
	void fillPreparedStatement(PreparedStatement preparedStatement, Administrator object) throws SQLException {
		preparedStatement.setString(1, object.getLogin());
		preparedStatement.setString(2, object.getToken());
		preparedStatement.setString(3, object.getFirstName());
		preparedStatement.setString(4, object.getLastName());
	}

	@Override
	Administrator getObject(ResultSet resultSet) throws SQLException {
		return new Administrator(
				resultSet.getString(1),
				resultSet.getString(2),
				resultSet.getString(3),
				resultSet.getString(4)
		);
	}
}
