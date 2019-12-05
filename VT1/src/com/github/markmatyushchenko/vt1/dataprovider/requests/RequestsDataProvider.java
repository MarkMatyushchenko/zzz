package com.github.markmatyushchenko.vt1.dataprovider.requests;

import com.github.markmatyushchenko.vt1.service.port.RequestsApi;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.dataprovider.AppDataProvider;
import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;
import com.github.markmatyushchenko.vt1.utils.Range;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class RequestsDataProvider implements RequestsApi {

	private Path requestsPath;
	private CSVRequestsSerializer requestsSerializer;
	private AppDataProvider dataProvider;

	public RequestsDataProvider(Path requestsPath, AppDataProvider parent) {
		this.requestsPath = requestsPath;
		this.dataProvider = parent;
		requestsSerializer = new CSVRequestsSerializer();
	}

	public Optional<List<Request>> getConfirmedRequestsForRoomType(Range<Date> range, String roomTypeName) {
		try {
			List<Map<String, String>> totalList = Files.readAllLines(requestsPath.resolve("requests.csv"))
					.stream()
					.map(requestsSerializer::parseRequest)
					.filter(map -> !map.keySet().isEmpty())
					.collect(Collectors.toList());
			List<Request> filteredResult = totalList.stream()
					.map(map -> requestsSerializer.requestFromMap(
							map,
							dataProvider.getRoomTypeDataProvider().getRoomTypeByName(map.get("roomTypeName")).orElse(null),
							dataProvider.getUserDataProvider().getUserByLogin(map.get("customerLogin")).orElse(null)
					))
					.filter(Optional::isPresent)
					.map(Optional::get)
					.filter(r -> r instanceof ConfirmedRequest)
					.filter(r -> r.getRoomType().getTypeName().equals(roomTypeName))
					.filter(r -> {
						Range<Date> requestRange = new Range<>(r.getArrivalDate(), r.getDepartureDate());
						return requestRange.contains(range.getFrom()) || requestRange.contains(range.getTo());
					})
					.collect(Collectors.toList());

			return Optional.of(filteredResult);
		} catch (IOException exc) {
			return Optional.empty();
		}
	}

	@Override
	public Either<List<Request>, Exception> getAllRequests(User user) {
		if (user instanceof Administrator || user instanceof Client) {
			try {
				List<Map<String, String>> totalList = Files.readAllLines(requestsPath.resolve("requests.csv"))
						.stream()
						.map(requestsSerializer::parseRequest)
						.filter(map -> !map.keySet().isEmpty())
						.collect(Collectors.toList());
				if (!(user instanceof Administrator)) {
					totalList = totalList.stream()
							.filter(map -> map.get("customerLogin").equals(user.getLogin()))
							.collect(Collectors.toList());
				}
				List<Request> result = totalList.stream()
						.map(map -> requestsSerializer.requestFromMap(
								map,
								dataProvider.getRoomTypeDataProvider().getRoomTypeByName(map.get("roomTypeName")).orElse(null),
								dataProvider.getUserDataProvider().getUserByLogin(map.get("customerLogin")).orElse(null)
						))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.collect(Collectors.toList());
				return Either.left(result);
			} catch (IOException exc) {
				return Either.right(new Exception("Can't read storage"));
			}
		} else return Either.right(new Exception("Not authorized"));
	}

	@Override
	public Either<Pair<List<Request>, Pagination>, Exception> getRequests(User user, Pagination pagination) {
		return getRequests(user, pagination, new RequestsFilter());
	}

	@Override
	public Either<Pair<List<Request>, Pagination>, Exception> getRequests(User user, Pagination pagination, RequestsFilter filter) {
		if (user instanceof Client || user instanceof Administrator) {
			try {
				List<Map<String, String>> totalList = Files.readAllLines(requestsPath.resolve("requests.csv"))
						.stream()
						.map(requestsSerializer::parseRequest)
						.filter(map -> !map.keySet().isEmpty())
						.collect(Collectors.toList());
				if (!(user instanceof Administrator)) {
					totalList = totalList.stream()
							.filter(map -> map.get("customerLogin").equals(user.getLogin()))
							.collect(Collectors.toList());
				}
				List<Request> filteredResult = totalList.stream()
						.map(map -> requestsSerializer.requestFromMap(
								map,
								dataProvider.getRoomTypeDataProvider().getRoomTypeByName(map.get("roomTypeName")).orElse(null),
								dataProvider.getUserDataProvider().getUserByLogin(map.get("customerLogin")).orElse(null)
						))
						.filter(Optional::isPresent)
						.map(Optional::get)
						.filter(r -> !filter.isConfirmed() || r instanceof ConfirmedRequest)
						.filter(r -> !filter.isRejected() || r instanceof RejectedRequest)
						.filter(r -> filter.getRoomTypeName().isEmpty() || r.getRoomType().getTypeName().equals(filter.getRoomTypeName().get()))
						.filter(r -> filter.getCustomerLogin().isEmpty() || r.getCustomer().getLogin().equals(filter.getCustomerLogin().get()))
						.filter(r -> filter.getNumberOfPersons().contains(r.getNumberOfPersons()))
						.filter(r -> filter.getArrivalDate().contains(r.getArrivalDate()))
						.filter(r -> filter.getDepartureDate().contains(r.getDepartureDate()))
						.collect(Collectors.toList());

				List<Request> subList = filteredResult
						.subList(
								(pagination.getPage() - 1) * pagination.getRecordsPerPage(),
								(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
										pagination.getRecordsPerPage() <= filteredResult.size() ?
										(pagination.getPage() - 1) * pagination.getRecordsPerPage() +
												pagination.getRecordsPerPage() : filteredResult.size()
						);
				return Either.left(new Pair(
						subList,
						new Pagination(pagination.getRecordsPerPage(), pagination.getPage(), filteredResult.size())
				));
			} catch (IOException exc) {
				return Either.right(new Exception("Can't read storage"));
			}
		} else return Either.right(new Exception("Not authorized"));
	}

	@Override
	public Either<Request, Exception> createRequest(User user, RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons) {
		if (user instanceof Client) {
			Request newRequest = new Request(
					roomType,
					arrivalDate,
					departureDate,
					numberOfPersons,
					(Client) user
			);
			String rawRequest = requestsSerializer.serializeRequest(newRequest);
			try {
				Files.write(
						requestsPath.resolve("requests.csv"),
						Collections.singletonList("\n" + rawRequest),
						StandardOpenOption.APPEND
				);
				return Either.left(newRequest);
			} catch (IOException exc) {
				return Either.right(new Exception("Can't access storage"));
			}
		} else return Either.right(new Exception("Not authorized"));
	}

	@Override
	public Optional<Exception> confirmRequest(User user, Request request, int roomNumber) {
		if (user instanceof Administrator) {
			try {
				ArrayList<String> lines = new ArrayList<>(Files.readAllLines(requestsPath.resolve("requests.csv")));
				Pair<Integer, Map<String, String>> rawRequestWithIndex = findRawRequstWithIndex(request, lines).orElse(null);
				if (rawRequestWithIndex == null) {
					return Optional.of(new Exception("No such request"));
				} else {
					ConfirmedRequest newRequest = new ConfirmedRequest(
							request,
							roomNumber
					);
					lines.set(rawRequestWithIndex.getFirst(), requestsSerializer.serializeRequest(newRequest));
					Files.write(requestsPath.resolve("requests.csv"), lines);
				}
				return Optional.empty();
			} catch (IOException exc) {
				return Optional.of(new Exception("Can't access storage"));
			}
		} else return Optional.of(new Exception("Not authorized"));
	}

	@Override
	public Optional<Exception> cancelRequest(User user, Request request) {
		if (user instanceof Client) {
			try {
				ArrayList<String> lines = new ArrayList<>(Files.readAllLines(requestsPath.resolve("requests.csv")));
				Pair<Integer, Map<String, String>> rawRequestWithIndex = findRawRequstWithIndex(request, lines).orElse(null);
				if (rawRequestWithIndex == null) {
					return Optional.of(new Exception("No such request"));
				} else {
					lines.remove(rawRequestWithIndex.getFirst().intValue());
					Files.write(requestsPath.resolve("requests.csv"), lines);
				}
				return Optional.empty();
			} catch (IOException exc) {
				return Optional.of(new Exception("Can't access storage"));
			}
		} else return Optional.of(new Exception("Not authorized"));
	}

	@Override
	public Optional<Exception> rejectRequest(User user, Request request, String comment) {
		if (user instanceof Administrator) {
			try {
				ArrayList<String> lines = new ArrayList<>(Files.readAllLines(requestsPath.resolve("requests.csv")));
				Pair<Integer, Map<String, String>> rawRequestWithIndex = findRawRequstWithIndex(request, lines).orElse(null);
				if (rawRequestWithIndex == null) {
					return Optional.of(new Exception("No such request"));
				} else {
					RejectedRequest newRequest = new RejectedRequest(
							request,
							comment
					);
					lines.set(rawRequestWithIndex.getFirst(), requestsSerializer.serializeRequest(newRequest));
					Files.write(requestsPath.resolve("requests.csv"), lines);
				}
				return Optional.empty();
			} catch (IOException exc) {
				return Optional.of(new Exception("Can't access storage"));
			}
		} else return Optional.of(new Exception("Not authorized"));
	}

	private Optional<Pair<Integer, Map<String, String>>> findRawRequstWithIndex(Request request, List<String> lines) {
		Pair<Integer, Map<String, String>> result = new Pair<>(null, null);
		lines.stream()
				.map(l -> requestsSerializer.parseRequest(l))
				.collect(HashMap<Integer, Map<String, String>>::new, (map, o) -> map.put(map.size(), o), (map, o) -> {})
				.forEach((i, map) -> {
					if (requestsSerializer.equalsMapWithRequest(map, request)) {
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
}
