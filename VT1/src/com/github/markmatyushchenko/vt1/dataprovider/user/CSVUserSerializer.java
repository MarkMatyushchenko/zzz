package com.github.markmatyushchenko.vt1.dataprovider.user;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.util.Map;

class CSVUserSerializer {

	String serializeClient(Client client) {
		return String.format("%s|%s|%s|%s|%s|%s",
				client.getLogin(),
				client.getToken(),
				client.getFirstName(),
				client.getLastName(),
				client.getEmail(),
				client.getPhoneNumber()
		);
	}

	String serializeAdmin(Administrator admin) {
		return String.format("%s|%s|%s|%s",
				admin.getLogin(),
				admin.getToken(),
				admin.getFirstName(),
				admin.getLastName()
		);
	}

	Map<String, String> parseAdmin(String line) {
		String[] fields = line.split("\\|");
		try {
			return Map.of(
					"login", fields[0],
					"token", fields[1],
					"firstName", fields[2],
					"lastName", fields[3]
			);
		} catch (ArrayIndexOutOfBoundsException exc) {
			return Map.of();
		}
	}

	Map<String, String> parseClient(String line) {
		String[] fields = line.split("\\|");
		try {
			return Map.of(
					"login", fields[0],
					"token", fields[1],
					"firstName", fields[2],
					"lastName", fields[3],
					"email", fields[4],
					"phoneNumber", fields.length > 5 ? fields[5] : ""
			);
		} catch (ArrayIndexOutOfBoundsException exc) {
			return Map.of();
		}
	}

	Client clientFromMap(Map<String, String> map) {
		return new Client(
				map.get("login"),
				map.get("token"),
				map.get("email"),
				map.get("firstName"),
				map.get("lastName"),
				map.get("phoneNumber")
		);
	}

	Administrator adminFromMap(Map<String, String> map) {
		return new Administrator(
				map.get("login"),
				map.get("token"),
				map.get("firstName"),
				map.get("lastName")
		);
	}
}
