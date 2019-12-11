package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.AppService;

import java.util.Optional;

public class AppAdminRequestsModel extends AppRequestsModel implements AdminRequestsModel {

	public AppAdminRequestsModel(AppService appService) {
		super(appService);
	}

	@Override
	public Optional<Request> confirmRequest(Request request, int roomNumber) {
		return appService.getAccountModel().getAccount()
				.map(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.confirmRequest(user, request, roomNumber);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
						return null;
					} else {
						appService.getViewModel().showInfo("Successfully confirmed");
						return new ConfirmedRequest(request, roomNumber);
					}
				});
	}

	@Override
	public Optional<Request> rejectRequest(Request request, String comment) {
		return appService.getAccountModel().getAccount()
				.map(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.rejectRequest(user, request, comment);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
						return null;
					} else {
						appService.getViewModel().showInfo("Successfully rejected");
						return new RejectedRequest(request, comment);
					}
				});
	}
}
