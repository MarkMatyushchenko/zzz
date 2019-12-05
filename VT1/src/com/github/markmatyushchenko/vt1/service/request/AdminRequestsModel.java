package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;

public interface AdminRequestsModel extends RequestsModel{

	void confirmRequest(Request request, int roomNumber);

	void rejectRequest(Request request, String comment);
}
