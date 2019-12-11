package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.user.User;

import java.util.Date;
import java.util.Optional;

public interface ClientRequestsModel extends RequestsModel {

	Optional<Request> createRequest(Date arrivalDate, Date departureDate, int numberOfPersons);

	Optional<Request> cancelRequest(Request request);

	AvailableRoomTypes getSelectedRoomType();

	Optional<User> getAccount();
}
