package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.utils.Either;

import java.util.Date;
import java.util.Optional;

public class AppClientRequestsModel extends AppRequestsModel implements ClientRequestsModel {

	public AppClientRequestsModel(AppService appService) {
		super(appService);
	}

	@Override
	public AvailableRoomTypes getSelectedRoomType() {
		return appService.getAvailableRoomTypesModel().getSelectedRoomType();
	}

	@Override
	public Optional<User> getAccount() {
		return appService.getAccountModel().getAccount();
	}

	@Override
	public Optional<Request> createRequest(Date arrivalDate, Date departureDate, int numberOfPersons) {
		return appService.getAccountModel().getAccount()
				.map(user -> {
					if (appService.getAvailableRoomTypesModel().getSelectedRoomType() != null) {
						Either<Request, Exception> response = appService
								.getDataProvider()
								.createRequest(
										user,
										getSelectedRoomType(),
										arrivalDate, departureDate, numberOfPersons
								);
						if (response.isRight()) {
							appService.getViewModel().setError(response.getRight().getMessage());
						} else {
							appService.getViewModel().showInfo("Successfully created");
							return response.getLeft();
						}
					}
					return null;
				});
	}

	@Override
	public Optional<Request> cancelRequest(Request request) {
		return appService.getAccountModel().getAccount()
				.map(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.cancelRequest(user, request);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
						return null;
					} else {
						appService.getViewModel().showInfo("Successfully canceled");
						return request;
					}
				});
	}
}
