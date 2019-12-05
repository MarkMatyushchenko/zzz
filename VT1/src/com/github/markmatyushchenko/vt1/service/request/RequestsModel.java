package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;

public interface RequestsModel {

	void addViewModel(RequestsViewModel viewModel);

	void getRequestsOnPage(int page, boolean useCache);
}
