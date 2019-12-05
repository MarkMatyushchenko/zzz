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
	public void createRequest(Date arrivalDate, Date departureDate, int numberOfPersons) {
		System.out.println("Create Request: " + getSelectedRoomType());
		System.out.println(arrivalDate);
		System.out.println(departureDate);
		System.out.println(numberOfPersons);
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
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
						}
					}
				});
	}

	@Override
	public void cancelRequest(Request request) {
		appService.getAccountModel().getAccount()
				.ifPresent(user -> {
					Optional<Exception> result = appService
							.getDataProvider()
							.cancelRequest(user, request);
					if (result.isPresent()) {
						appService.getViewModel().setError(result.get().getMessage());
					} else {
						appService.getViewModel().showInfo("Successfully canceled");
					}
				});
	}
}
