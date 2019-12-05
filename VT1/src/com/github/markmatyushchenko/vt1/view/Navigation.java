package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;

import java.util.HashMap;
import java.util.Map;

public class Navigation {


    private Map<String, CLIView> route;

    public Navigation(AppService service){
        route = new HashMap<>();

        var roomTypeView = new RoomTypesView(service.getRoomTypeModel(), this);
        service.getRoomTypeModel().addViewModel(roomTypeView);
        route.put("roomTypeView",roomTypeView);

        var accountView = new ProfileView(service.getAccountModel());
        service.getAccountModel().addViewModel(accountView);
        route.put("accountView",  accountView);

        var registerView = new RegisterView(service.getRegisterModel(), this);
        service.getRegisterModel().addViewModel( registerView);
        route.put("registerView",registerView);

        var loginView = new LoginView(service.getLoginModel(), this);
        service.getLoginModel().addViewModel(loginView);
        route.put("loginView",loginView);

        var availableRoomsView = new AvailableRoomsView( service.getAvailableRoomTypesModel(), this);
        service.getAvailableRoomTypesModel().addViewModel(availableRoomsView);
        route.put("availableRoomsView", availableRoomsView);

        var adminRequestsView = new AdminRequestView(service.getAdminRequestsModel(), this);
        service.getAdminRequestsModel().addViewModel(adminRequestsView);
        route.put("adminRequestView", adminRequestsView);

        //TODO: clientRequestsView with route "clientRequestsView"

        var clientRequestsView = new ClientRequestView(service.getClientRequestsModel(), this);
        service.getClientRequestsModel().addViewModel(clientRequestsView);
        route.put("clientRequestsView", clientRequestsView);
    }


    public void navigateTo(String to) {
        if (to.equals("requestsView")) {
            var user = ((AccountViewModel) route.get("accountView")).getAccount().get();
            System.out.println(user.getLogin());
            if (user instanceof Administrator) {
                route.get("adminRequestView").render();

            } else if (user instanceof Client) {
                route.get("clientRequestsView").render();
            }
        } else {
            route.get(to).render();
        }
    }
}
