package com.github.markmatyushchenko.vt1.repository;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StandardRepository implements Repository {

    private AdministratorDAO administratorDAO;
    private ClientDAO clientDAO;
    private UserDAO userDAO;

    private RoomTypeDAO roomTypeDAO;
    private AvailableRoomTypesDAO availableRoomTypesDAO;

    private RequestDAO requestDAO;
    private RejectedRequestDAO rejectedRequestDAO;
    private ConfirmedRequestDAO confirmedRequestDAO;

    public StandardRepository(AdministratorDAO administratorDAO, ClientDAO clientDAO,
                              UserDAO userDAO, RoomTypeDAO roomTypeDAO,
                              AvailableRoomTypesDAO availableRoomTypesDAO,
                              RequestDAO requestDAO, RejectedRequestDAO rejectedRequestDAO,
                              ConfirmedRequestDAO confirmedRequestDAO) {
        this.administratorDAO = administratorDAO;
        this.clientDAO = clientDAO;
        this.userDAO = userDAO;
        this.roomTypeDAO = roomTypeDAO;
        this.availableRoomTypesDAO = availableRoomTypesDAO;
        this.requestDAO = requestDAO;
        this.rejectedRequestDAO = rejectedRequestDAO;
        this.confirmedRequestDAO = confirmedRequestDAO;
    }

    @Override
    public void saveUsers(Iterable<User> users) {

        List<Administrator> administrators = filterAdministrators(users);
        administratorDAO.save(administrators);

        List<Client> clients = filterClients(users);
        clientDAO.save(clients);

        Set<User> ans = new HashSet<>();
        users.forEach(ans::add);
        administrators.forEach(ans::remove);
        clients.forEach(ans::remove);
        userDAO.save(ans);

    }

    @Override
    public void saveRoomTypes(Iterable<RoomType> roomTypes) {
        List<AvailableRoomTypes> availableRoomTypes = filterAvailableRoomTypes(roomTypes);
        availableRoomTypesDAO.save(availableRoomTypes);

        Set<RoomType> ans = new HashSet<>();
        roomTypes.forEach(ans::add);
        availableRoomTypes.forEach(ans::remove);
        roomTypeDAO.save(ans);
    }

    @Override
    public void saveRequests(Iterable<Request> requests) {
        List<RejectedRequest> rejectedRequests = filterRejectedRequests(requests);
        rejectedRequestDAO.save(rejectedRequests);

        List<ConfirmedRequest> confirmedRequests = filterConfirmedRequests(requests);
        confirmedRequestDAO.save(confirmedRequests);

        Set<Request> ans = new HashSet<>();
        requests.forEach(ans::add);
        rejectedRequests.forEach(ans::remove);
        confirmedRequests.forEach(ans::remove);
        requestDAO.save(ans);
    }

    private List<Administrator> filterAdministrators(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .filter(x -> x instanceof Administrator)
                .map(x -> (Administrator)x)
                .collect(Collectors.toList());
    }

    private List<Client> filterClients(Iterable<User> users) {
        return StreamSupport.stream(users.spliterator(), false)
                .filter(x -> x instanceof Client)
                .map(x -> (Client)x)
                .collect(Collectors.toList());
    }

    private List<AvailableRoomTypes> filterAvailableRoomTypes(Iterable<RoomType> roomTypes) {
        return StreamSupport.stream(roomTypes.spliterator(), false)
                .filter(x -> x instanceof AvailableRoomTypes)
                .map(x -> (AvailableRoomTypes)x)
                .collect(Collectors.toList());
    }

    private List<RejectedRequest> filterRejectedRequests(Iterable<Request> requests) {
        return StreamSupport.stream(requests.spliterator(), false)
                .filter(x -> x instanceof RejectedRequest)
                .map(x -> (RejectedRequest)x)
                .collect(Collectors.toList());
    }

    private List<ConfirmedRequest> filterConfirmedRequests(Iterable<Request> requests) {
        return StreamSupport.stream(requests.spliterator(), false)
                .filter(x -> x instanceof ConfirmedRequest)
                .map(x -> (ConfirmedRequest)x)
                .collect(Collectors.toList());
    }


    @Override
    public Iterable<User> getUsers() {
        List<User> ans = new ArrayList<>();
        userDAO.read().forEach(ans::add);
        administratorDAO.read().forEach(ans::add);
        clientDAO.read().forEach(ans::add);
        return ans;
    }

    @Override
    public Iterable<RoomType> getRoomTypes() {
        List<RoomType> ans = new ArrayList<>();
        roomTypeDAO.read().forEach(ans::add);
        availableRoomTypesDAO.read().forEach(ans::add);
        return ans;
    }

    @Override
    public Iterable<Request> getRequests() {
        List<Request> ans = new ArrayList<>();
        requestDAO.read().forEach(ans::add);
        confirmedRequestDAO.read().forEach(ans::add);
        rejectedRequestDAO.read().forEach(ans::add);
        return ans;
    }

}
