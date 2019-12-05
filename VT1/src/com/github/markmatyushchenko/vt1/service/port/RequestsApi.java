package com.github.markmatyushchenko.vt1.service.port;

import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RequestsApi {

	Either<List<Request>, Exception> getAllRequests(User user);

	Either<Pair<List<Request>, Pagination>, Exception>
	getRequests(User user, Pagination pagination);

	Either<Pair<List<Request>, Pagination>, Exception>
	getRequests(User user, Pagination pagination, RequestsFilter filter);

	Either<Request, Exception>
	createRequest(User user, RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons);

	Optional<Exception> confirmRequest(User user, Request request, int roomNumber);

	Optional<Exception> cancelRequest(User user, Request request);

	Optional<Exception> rejectRequest(User user, Request request, String comment);
}
