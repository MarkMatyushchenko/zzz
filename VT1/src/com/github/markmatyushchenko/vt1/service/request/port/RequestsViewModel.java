package com.github.markmatyushchenko.vt1.service.request.port;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;

import java.util.List;
import java.util.Optional;

public interface RequestsViewModel {

	void setPage(int page);

	void setTotalCount(int count);

	Optional<RequestsFilter> getFilter();

	int getRecordsPerPage();

	void setActualRequests(List<Request> requests);
}
