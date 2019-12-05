package com.github.markmatyushchenko.vt1.dataprovider.user;

import com.github.markmatyushchenko.vt1.service.port.UsersApi;
import com.github.markmatyushchenko.vt1.service.port.exceptions.ExistingAccountException;
import com.github.markmatyushchenko.vt1.service.port.exceptions.IncorrectPasswordException;
import com.github.markmatyushchenko.vt1.service.port.exceptions.NoSuchUserException;
import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@SuppressWarnings("unchecked")
public class UserDataProvider implements UsersApi {

	private Path userPath;
	private CSVUserSerializer userSerializer;

	public UserDataProvider(Path userPath) {
		this.userPath = userPath;
		userSerializer = new CSVUserSerializer();
	}

	public Optional<User> getUserByLogin(String login) {
		Map<String, String> rawUser = findRawAdministrator(login).orElse(null);
		if (rawUser == null) {
			rawUser = findRawClient(login).orElse(null);
			if (rawUser == null) {
				return Optional.empty();
			} else {
				return Optional.of(userSerializer.clientFromMap(rawUser));
			}
		} else {
			return Optional.of(userSerializer.adminFromMap(rawUser));
		}
	}

	@Override
	public Either<User, Exception> register(String login, String password, String firstName, String lastName, String email, String phoneNumber) {
		Map<String, String> rawUser = findRawAdministrator(login).orElse(null);
		if (rawUser == null) {
			rawUser = findRawClient(login).orElse(null);
			if (rawUser == null) {
				try {
					Client newUser = new Client(
							login, md5(login + password), email, firstName, lastName, phoneNumber
					);
					Files.writeString(
							userPath.resolve("client.csv"),
							"\n" + userSerializer.serializeClient(newUser),
							StandardOpenOption.APPEND
					);
					return Either.left(newUser);
				} catch (IOException exc) {
					return Either.right(new Exception("Can't write to storage"));
				}
			} else {
				return Either.right(new ExistingAccountException());
			}
		} else {
			return Either.right(new ExistingAccountException());
		}
	}

	@Override
	public Either<User, Exception> authorize(String login, String password) {
		String receivedToken = md5(login + password);
		Map<String, String> rawUser = findRawAdministrator(login).orElse(null);
		if (rawUser == null) {
			rawUser = findRawClient(login).orElse(null);
			if (rawUser == null) {
				return Either.right(new NoSuchUserException());
			} else {
				if (rawUser.get("token").equals(receivedToken)) {
					return Either.left(userSerializer.clientFromMap(rawUser));
				} else {
					return Either.right(new IncorrectPasswordException());
				}
			}
		} else {
			if (rawUser.get("token").equals(receivedToken)) {
				return Either.left(userSerializer.adminFromMap(rawUser));
			} else {
				return Either.right(new IncorrectPasswordException());
			}
		}
	}

	@Override
	public Either<User, Exception> update(User user, String oldPassword, String password, String firstName, String lastName, String email, String phoneNumber) {
		String receivedToken = md5(user.getLogin() + oldPassword);
		try {
			ArrayList<String> adminLines = new ArrayList<>(Files.readAllLines(userPath.resolve("admin.csv")));
			Pair<Integer, Map<String, String>> rawUserIndexed =
					findRawAdministratorWithIndex(user.getLogin(), adminLines).orElse(null);
			if (rawUserIndexed == null) {
				ArrayList<String> clientLines = new ArrayList<>(Files.readAllLines(userPath.resolve("client.csv")));
				rawUserIndexed = findRawClientWithIndex(user.getLogin(), clientLines).orElse(null);
				if (rawUserIndexed == null) {
					return Either.right(new NoSuchUserException());
				} else {
					if (rawUserIndexed.getSecond().get("token").equals(receivedToken)) {
						Client newClient = new Client(
								user.getLogin(),
								md5(user.getLogin() + password),
								email,
								firstName,
								lastName,
								phoneNumber
						);
						clientLines.set(rawUserIndexed.getFirst(), userSerializer.serializeClient(newClient));
						Files.write(userPath.resolve("client.csv"), clientLines);
						return Either.left(newClient);
					} else {
						return Either.right(new IncorrectPasswordException());
					}
				}
			} else {
				if (rawUserIndexed.getSecond().get("token").equals(receivedToken)) {
					Administrator newAdmin = new Administrator(
							user.getLogin(),
							md5(user.getLogin() + password),
							firstName,
							lastName
					);
					adminLines.set(rawUserIndexed.getFirst(), userSerializer.serializeAdmin(newAdmin));
					Files.write(userPath.resolve("client.csv"), adminLines);
					return Either.left(newAdmin);
				} else {
					return Either.right(new IncorrectPasswordException());
				}
			}
		} catch (IOException exc) {
			return Either.right(new Exception("Can't access storage"));
		}
	}

	private Optional<Map<String, String>> findRawAdministrator(String login) {
		try {
			return findRawAdministrator(login, Files.readAllLines(userPath.resolve("admin.csv")));
		} catch (IOException exc) {
			return Optional.empty();
		}
	}

	private Optional<Map<String, String>> findRawAdministrator(String login, List<String> lines) {
		return lines.stream()
				.map(l -> userSerializer.parseAdmin(l))
				.filter(map -> map.get("login") != null)
				.filter(map -> map.get("login").equals(login))
				.findFirst();
	}

	private Optional<Pair<Integer, Map<String, String>>> findRawAdministratorWithIndex(String login, List<String> lines) {
		Pair<Integer, Map<String, String>> result = new Pair<>(null, null);
		lines.stream()
				.map(l -> userSerializer.parseAdmin(l))
				.collect(HashMap<Integer, Map<String, String>>::new, (map, o) -> map.put(map.size(), o), (map, o) -> {})
				.forEach((i, map) -> {
					if (map.get("login") != null && map.get("login").equals(login)) {
						result.setFirst(i);
						result.setSecond(map);
					}
				});
		if (result.getSecond() == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}

	private Optional<Map<String, String>> findRawClient(String login) {
		try {
			return findRawClient(login, Files.readAllLines(userPath.resolve("client.csv")));
		} catch (IOException exc) {
			return Optional.empty();
		}
	}

	private Optional<Map<String, String>> findRawClient(String login, List<String> lines) {
		return lines.stream()
				.map(l -> userSerializer.parseClient(l))
				.filter(map -> map.get("login") != null)
				.filter(map -> map.get("login").equals(login))
				.findFirst();
	}

	private Optional<Pair<Integer, Map<String, String>>> findRawClientWithIndex(String login, List<String> lines) {
		Pair<Integer, Map<String, String>> result = new Pair<>(null, null);
		lines.stream()
				.map(l -> userSerializer.parseClient(l))
				.collect(HashMap<Integer, Map<String, String>>::new, (map, o) -> map.put(map.size(), o), (map, o) -> {})
				.forEach((i, map) -> {
					if (map.get("login") != null && map.get("login").equals(login)) {
						result.setFirst(i);
						result.setSecond(map);
					}
				});
		if (result.getSecond() == null) {
			return Optional.empty();
		} else {
			return Optional.of(result);
		}
	}

	private String md5(String st) {
		byte[] digest = new byte[0];

		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(st.getBytes());
			digest = messageDigest.digest();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		BigInteger bigInt = new BigInteger(1, digest);
		StringBuilder md5Hex = new StringBuilder(bigInt.toString(16));

		while (md5Hex.length() < 32) {
			md5Hex.insert(0, "0");
		}

		return md5Hex.toString();
	}
}
