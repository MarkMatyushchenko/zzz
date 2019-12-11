package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.view.auth.LoginView;
import com.github.markmatyushchenko.vt1.view.auth.ProfileView;
import com.github.markmatyushchenko.vt1.view.auth.RegisterView;
import com.github.markmatyushchenko.vt1.view.request.AdminRequestView;
import com.github.markmatyushchenko.vt1.view.request.ClientRequestView;
import com.github.markmatyushchenko.vt1.view.request.NewRequestView;
import com.github.markmatyushchenko.vt1.view.roomtype.AvailableRoomsView;
import com.github.markmatyushchenko.vt1.view.roomtype.RoomTypesView;

import java.util.HashMap;
import java.util.Map;

public class Navigation {

	private Map<Route, CLIView> route;
	private AppService appService;

	public enum Route {
		ROOM_TYPES,
		REGISTER,
		LOGIN,
		AVAILABLE_ROOMS,
		REQUESTS,
		ADMIN_REQUESTS,
		CLIENT_REQUESTS,
		NEW_REQUEST
	}

	public Navigation(AppService service) {
		this.appService = service;
		route = new HashMap<>();

		var roomTypeView = new RoomTypesView(service.getRoomTypeModel(), this);
		service.getRoomTypeModel().addViewModel(roomTypeView);
		route.put(Route.ROOM_TYPES, roomTypeView);

		var accountView = new ProfileView(service.getAccountModel());
		service.getAccountModel().addViewModel(accountView);

		var registerView = new RegisterView(service.getRegisterModel(), this);
		service.getRegisterModel().addViewModel(registerView);
		route.put(Route.REGISTER, registerView);

		var loginView = new LoginView(service.getLoginModel(), this);
		service.getLoginModel().addViewModel(loginView);
		route.put(Route.LOGIN, loginView);

		var availableRoomsView = new AvailableRoomsView(service.getAvailableRoomTypesModel(), this);
		service.getAvailableRoomTypesModel().addViewModel(availableRoomsView);
		route.put(Route.AVAILABLE_ROOMS, availableRoomsView);


		var adminRequestsView = new AdminRequestView(service.getAdminRequestsModel(), this);
		service.getAdminRequestsModel().addViewModel(adminRequestsView);
		route.put(Route.ADMIN_REQUESTS, adminRequestsView);

		var clientRequestsView = new ClientRequestView(service.getClientRequestsModel(), this);
		service.getClientRequestsModel().addViewModel(clientRequestsView);
		route.put(Route.CLIENT_REQUESTS, clientRequestsView);


		var newRequestView = new NewRequestView(service.getClientRequestsModel());
		route.put(Route.NEW_REQUEST, newRequestView);
	}

	public void navigateTo(Route to) {
		if (to == Route.REQUESTS) {
			appService.getAccountModel().getAccount().ifPresentOrElse(
					(user) -> {
						if (user instanceof Administrator) {
							route.get(Route.ADMIN_REQUESTS).render();
						} else if (user instanceof Client) {
							route.get(Route.CLIENT_REQUESTS).render();
						} else {
							System.out.println("Need to authorize");
							route.get(Route.LOGIN).render();
						}
					},
					() -> {
						System.out.println("Need to authorize");
						route.get(Route.LOGIN).render();
					}
			);

		} else if (to == Route.NEW_REQUEST) {
			appService.getAccountModel().getAccount().ifPresentOrElse(
					(user) -> {
						if (user instanceof Client) {
							route.get(Route.NEW_REQUEST).render();
						} else {
							System.out.println("Need to authorize as client");
							route.get(Route.LOGIN).render();
						}
					},
					() -> {
						System.out.println("Need to authorize");
						route.get(Route.LOGIN).render();
					}
			);
		} else {
			route.get(to).render();
		}
	}
}
