package com.github.markmatyushchenko.vt1.dataprovider;

import com.github.markmatyushchenko.vt1.dataprovider.requests.RequestsDataProvider;
import com.github.markmatyushchenko.vt1.dataprovider.roomtype.AvailableRoomsDataProvider;
import com.github.markmatyushchenko.vt1.dataprovider.roomtype.RoomTypeDataProvider;
import com.github.markmatyushchenko.vt1.dataprovider.user.UserDataProvider;
import com.github.markmatyushchenko.vt1.service.port.DataProvider;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.nio.file.Path;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class AppDataProvider implements DataProvider {

	private Path rootPath;

	private UserDataProvider userDataProvider;
	private RoomTypeDataProvider roomTypeDataProvider;
	private AvailableRoomsDataProvider availableRoomsDataProvider;
	private RequestsDataProvider requestsDataProvider;

	public AppDataProvider(Path rootPath) {
		userDataProvider = new UserDataProvider(rootPath.resolve("users"));
		roomTypeDataProvider = new RoomTypeDataProvider(rootPath.resolve("roomtypes"));
		requestsDataProvider = new RequestsDataProvider(rootPath.resolve("requests"), this);
		availableRoomsDataProvider = new AvailableRoomsDataProvider(rootPath.resolve("roomtypes"),this);
	}

	public UserDataProvider getUserDataProvider() {
		return userDataProvider;
	}

	public RoomTypeDataProvider getRoomTypeDataProvider() {
		return roomTypeDataProvider;
	}

	public RequestsDataProvider getRequestsDataProvider() {
		return requestsDataProvider;
	}

	public AvailableRoomsDataProvider getAvailableRoomsDataProvider() {
		return availableRoomsDataProvider;
	}

	/**Requests*/

	@Override
	public Either<List<Request>, Exception> getAllRequests(User user) {
		return requestsDataProvider.getAllRequests(user);
	}

	@Override
	public Either<Pair<List<Request>, Pagination>, Exception> getRequests(User user, Pagination pagination) {
		return requestsDataProvider.getRequests(user, pagination);
	}

	@Override
	public Either<Pair<List<Request>, Pagination>, Exception> getRequests(User user, Pagination pagination, RequestsFilter filter) {
		return requestsDataProvider.getRequests(user, pagination, filter);
	}

	@Override
	public Either<Request, Exception> createRequest(User user, RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons) {
		return requestsDataProvider.createRequest(user, roomType, arrivalDate, departureDate, numberOfPersons);
	}

	@Override
	public Optional<Exception> confirmRequest(User user, Request request, int roomNumber) {
		return requestsDataProvider.confirmRequest(user, request, roomNumber);
	}

	@Override
	public Optional<Exception> cancelRequest(User user, Request request) {
		return requestsDataProvider.cancelRequest(user, request);
	}

	@Override
	public Optional<Exception> rejectRequest(User user, Request request, String comment) {
		return requestsDataProvider.rejectRequest(user, request, comment);
	}



	/**User*/

	@Override
	public Either<User, Exception> register(String login, String password, String firstName, String lastName, String email, String phoneNumber) {
		return userDataProvider.register(login, password, firstName, lastName, email, phoneNumber);
	}

	@Override
	public Either<User, Exception> authorize(String login, String password) {
		return userDataProvider.authorize(login, password);
	}

	@Override
	public Either<User, Exception> update(User user, String oldPassword, String password, String firstName, String lastName, String email, String phoneNumber) {
		return userDataProvider.update(user, oldPassword, password, firstName, lastName, email, phoneNumber);
	}



	/**Available Room Types*/

	@Override
	public Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception> getAvailableRoomTypes(User user, Pagination pagination) {
		return availableRoomsDataProvider.getAvailableRoomTypes(user, pagination);
	}

	@Override
	public Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception> getAvailableRoomTypes(User user, Pagination pagination, AvailableRoomTypesFilter filter) {
		return availableRoomsDataProvider.getAvailableRoomTypes(user, pagination, filter);
	}



	/**Room Types*/

	@Override
	public Either<List<RoomType>, Exception> getAllRoomTypes(User user) {
		return roomTypeDataProvider.getAllRoomTypes(user);
	}

	@Override
	public Either<Pair<List<RoomType>, Pagination>, Exception> getRoomTypes(User user, Pagination pagination) {
		return roomTypeDataProvider.getRoomTypes(user, pagination);
	}

	@Override
	public Either<Pair<List<RoomType>, Pagination>, Exception> getRoomTypes(User user, Pagination pagination, RoomTypeFilter filter) {
		return roomTypeDataProvider.getRoomTypes(user, pagination, filter);
	}

	@Override
	public Either<List<String>, Exception> getAvailableServices(User user) {
		return roomTypeDataProvider.getAvailableServices(user);
	}
}
