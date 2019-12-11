package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import com.github.markmatyushchenko.vt1.repository.DAO;
import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.net.MalformedURLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlConfirmedRequestDAO implements DAO<ConfirmedRequest> {

	private MySqlRoomTypeDAO roomTypeDAO;
	private MySqlConfigurationManager configurationManager;

	public MySqlConfirmedRequestDAO(MySqlConfigurationManager configurationManager, MySqlRoomTypeDAO mySqlRoomTypeDAO) {
		this.configurationManager = configurationManager;
		this.roomTypeDAO = mySqlRoomTypeDAO;
	}

	private Connection getConnection() throws SQLException {
		return configurationManager.getConnection();
	}

	static Date convertDate(java.util.Date date) {
		return new Date(date.getTime());
	}

	static java.util.Date convertDate(Date date) {
		return new Date(date.getTime());
	}

	public void clear() {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM confirmedRequests");
			preparedStatement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void save(Iterable<ConfirmedRequest> objects) {
		objects.forEach(x -> {
			try {
				Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `confirmedRequests` (typeName, arrivalDate, departureDate, numberOfPersons, client, roomNumber) VALUES (?, ?, ?, ?, ?, ?);");
				preparedStatement.setString(1, x.getRoomType().getTypeName());
				preparedStatement.setDate(2, convertDate(x.getArrivalDate()));
				preparedStatement.setDate(3, convertDate(x.getDepartureDate()));
				preparedStatement.setInt(4, x.getNumberOfPersons());
				preparedStatement.setString(5, x.getCustomer().getLogin());
				preparedStatement.setInt(6, x.getRoomNumber());
				preparedStatement.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public Iterable<ConfirmedRequest> read() {
		List<ConfirmedRequest> ans = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM requests INNER JOIN clients c on requests.client = c.login");
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				ConfirmedRequest request = new ConfirmedRequest(
						roomTypeDAO.getByType(resultSet.getString(1)),
						convertDate(resultSet.getDate(2)),
						convertDate(resultSet.getDate(3)),
						resultSet.getInt(4),
						new Client(
								resultSet.getString(6),
								resultSet.getString(7),
								resultSet.getString(8),
								resultSet.getString(9),
								resultSet.getString(10)
						),
						resultSet.getInt(5)
				);
				ans.add(request);
			}
		} catch (SQLException | MalformedURLException e) {
			e.printStackTrace();
		}
		return ans;
	}
}

