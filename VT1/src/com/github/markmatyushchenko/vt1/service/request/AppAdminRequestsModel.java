package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.AppService;

import java.util.Optional;

public class AppAdminRequestsModel extends AppRequestsModel implements AdminRequestsModel {

	public AppAdminRequestsModel(AppService appService) {
		super(appService);
	}

	@Override
	public void confirmRequest(Request request, int roomNumber) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.confirmRequest(user, request, roomNumber);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
					} else {
						appService.getViewModel().showInfo("Successfully confirmed");
					}
				});
	}

	@Override
	public void rejectRequest(Request request, String comment) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.rejectRequest(user, request, comment);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
					} else {
						appService.getViewModel().showInfo("Successfully rejected");
					}
				});
	}
}
