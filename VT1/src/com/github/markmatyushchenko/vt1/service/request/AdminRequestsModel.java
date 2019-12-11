package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;

import java.util.Optional;

public interface AdminRequestsModel extends RequestsModel{

	Optional<Request> confirmRequest(Request request, int roomNumber);

	Optional<Request> rejectRequest(Request request, String comment);
}
