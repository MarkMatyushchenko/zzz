package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class AppRequestsModel implements RequestsModel {

	AppService appService;
	private RequestsViewModel viewModel;

	private List<Request> allRequests;

	AppRequestsModel(AppService appService) {
		this.appService = appService;
		allRequests = new ArrayList<>();
	}

	@Override
	public void addViewModel(RequestsViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void getRequestsOnPage(int page, boolean useCache) {
		int offset = (page - 1) * viewModel.getRecordsPerPage();
		int count = viewModel.getRecordsPerPage();
		if (useCache && offset + count < allRequests.size()) {
			viewModel.setActualRequests(allRequests.subList(offset, offset + count));
		} else {
			loadRequestsOnPage(page);
		}
		viewModel.setPage(page);
	}

	private void loadRequestsOnPage(int page) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					int offset = (page - 1) * viewModel.getRecordsPerPage();
					Either<Pair<List<Request>, Pagination>, Exception> newRequests =
							viewModel.getFilter().isPresent() ?
									appService.getDataProvider().getRequests(user,
											new Pagination(viewModel.getRecordsPerPage(), page),
											viewModel.getFilter().get()) :
									appService.getDataProvider().getRequests(user,
											new Pagination(viewModel.getRecordsPerPage(), page));
					if (newRequests.isRight()) {
						var exceptionMessage = newRequests.getRight().getMessage();
						appService.getViewModel().setError(exceptionMessage);
					} else {
						var list = newRequests.getLeft().getFirst();
						var pagination = newRequests.getLeft().getSecond();

						int rewrited = 0;
						for (int i = offset; i < allRequests.size() && rewrited < list.size(); i++) {
							allRequests.set(i, list.get(rewrited));
							rewrited++;
						}

						for (int i = rewrited; i < list.size(); i++) {
							allRequests.add(list.get(i));
						}

						viewModel.setPage(pagination.getPage());
						viewModel.setTotalCount(pagination.getTotalCount());
						viewModel.setActualRequests(list);
					}
				});
	}
}
