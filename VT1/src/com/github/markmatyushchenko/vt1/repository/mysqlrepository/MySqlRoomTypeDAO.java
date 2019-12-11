package com.github.markmatyushchenko.vt1.repository.mysqlrepository;

import com.github.markmatyushchenko.vt1.repository.DAO;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MySqlRoomTypeDAO implements DAO<RoomType> {

	private MySqlConfigurationManager configurationManager;

	public MySqlRoomTypeDAO(MySqlConfigurationManager configurationManager) {
		this.configurationManager = configurationManager;
	}

	private Connection getConnection() throws SQLException {
		return configurationManager.getConnection();
	}

	@Override
	public void save(Iterable<RoomType> objects) {
		objects.forEach(this::saveRoomType);
	}

	public void clear() {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM roomNumbers");
			preparedStatement.execute();
			PreparedStatement preparedStatement1 = connection.prepareStatement("DELETE FROM roomType");
			preparedStatement1.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Iterable<RoomType> read() {
		List<RoomType> ans = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roomType;");
			preparedStatement.execute();
			ResultSet resultSet = preparedStatement.getResultSet();
			while (resultSet.next()) {
				String typeName = resultSet.getString(1);
				List<Integer> roomNumbers = roomNumbers(typeName);
				List<String> services = services(typeName);
				List<URL> images = images(typeName).stream()
						.map(x -> {
							try {
								return new URL(x);
							} catch (MalformedURLException e) {
								e.printStackTrace();
							}
							return null;
						}).collect(Collectors.toList());
				URL smallImage = new URL(resultSet.getString(4));
				RoomType type = new RoomType(
						typeName,
						resultSet.getInt(2),
						resultSet.getInt(3),
						smallImage,
						images,
						resultSet.getDouble(5),
						roomNumbers,
						services
				);
				ans.add(type);
			}
			resultSet.close();
			preparedStatement.close();
			connection.close();
		} catch (SQLException | MalformedURLException e) {
			e.printStackTrace();
		}
		return ans;
	}

	void saveRoomType(RoomType roomType) {
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO `roomType` (`typeName`, `numOfPlaces`, `cost`, `smallImage`, `area`) VALUES (?, ?, ?, ?, ?);");
			preparedStatement.setString(1, roomType.getTypeName());
			preparedStatement.setInt(2, roomType.getNumOfPlaces());
			preparedStatement.setInt(3, roomType.getCost());
			if (roomType.getSmallImage().isPresent()) {
				preparedStatement.setString(4, roomType.getSmallImage().get().toString());
			} else {
				preparedStatement.setString(4, null);
			}
			preparedStatement.setDouble(5, roomType.getArea());
			preparedStatement.execute();
			preparedStatement.close();
			connection.close();
			roomType.getLargeImages().forEach(x -> {
				try {
					Connection connection1 = getConnection();
					PreparedStatement preparedStatement1 = connection1.prepareStatement("INSERT INTO `largeImages` (`typeName`, `url`) VALUES (?, ?);");
					preparedStatement1.setString(1, roomType.getTypeName());
					preparedStatement1.setString(2, x.toString());
					preparedStatement1.execute();
					preparedStatement1.close();
					connection1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			roomType.getServices().forEach(x -> {
				try {
					Connection connection1 = getConnection();
					PreparedStatement preparedStatement1 = connection1.prepareStatement("INSERT INTO `services` (`typeName`, `service`) VALUES (?, ?);");
					preparedStatement1.setString(1, roomType.getTypeName());
					preparedStatement1.setString(2, x);
					preparedStatement1.execute();
					preparedStatement1.close();
					connection1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			roomType.getRoomNumbers().forEach(x -> {
				try {
					Connection connection1 = getConnection();
					PreparedStatement preparedStatement1 = connection1.prepareStatement("INSERT INTO `roomNumbers` (`typeName`, `number`) VALUES (?, ?);");
					preparedStatement1.setString(1, roomType.getTypeName());
					preparedStatement1.setInt(2, x);
					preparedStatement1.execute();
					preparedStatement1.close();
					connection1.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	List<Integer> roomNumbers(String typeName) throws SQLException {
		List<Integer> ans = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT `number` FROM `roomNumbers` WHERE `typeName` = ?;");
		preparedStatement.setString(1, typeName);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		while (resultSet.next()) {
			ans.add(resultSet.getInt(1));
		}
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return ans;
	}

	List<String> services(String typeName) throws SQLException {
		List<String> ans = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT `service` FROM `services` WHERE `typeName` = ?;");
		preparedStatement.setString(1, typeName);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		while (resultSet.next()) {
			ans.add(resultSet.getString(1));
		}
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return ans;
	}

	List<String> images(String typeName) throws SQLException {
		List<String> ans = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT `url` FROM `largeImages` WHERE `typeName` = ?;");
		preparedStatement.setString(1, typeName);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		while (resultSet.next()) {
			ans.add(resultSet.getString(1));
		}
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return ans;
	}

	RoomType getByType(String typeName) throws SQLException, MalformedURLException {
		RoomType type = null;
		Connection connection = getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM roomType WHERE typeName = ?;");
		preparedStatement.setString(1, typeName);
		preparedStatement.execute();
		ResultSet resultSet = preparedStatement.getResultSet();
		while (resultSet.next()) {
			List<Integer> roomNumbers = roomNumbers(typeName);
			List<String> services = services(typeName);
			List<URL> images = images(typeName).stream()
					.map(x -> {
						try {
							return new URL(x);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
						return null;
					}).collect(Collectors.toList());
			URL smallImage = new URL(resultSet.getString(4));
			type = new RoomType(
					typeName,
					resultSet.getInt(2),
					resultSet.getInt(3),
					smallImage,
					images,
					resultSet.getDouble(5),
					roomNumbers,
					services
			);
		}
		resultSet.close();
		preparedStatement.close();
		connection.close();
		return type;
	}

}

