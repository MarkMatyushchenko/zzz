package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.request.ClientRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientRequestView  implements CLIView,ClientRequestsModel,RequestsViewModel {

    private ClientRequestsModel clientRequestsModel;
    private Navigation navigation;

    public ClientRequestView(ClientRequestsModel clientRequestsModel, Navigation navigation) {
        this.clientRequestsModel = clientRequestsModel;
        this.navigation =navigation;
    }

    @Override
    public void createRequest(Date arrivalDate, Date departureDate, int numberOfPersons) {

    }

    @Override
    public void cancelRequest(Request request) {

    }

    @Override
    public AvailableRoomTypes getSelectedRoomType() {
        return null;
    }

    @Override
    public Optional<User> getAccount() {
        return Optional.empty();
    }

    @Override
    public void addViewModel(RequestsViewModel viewModel) {

    }

    @Override
    public void getRequestsOnPage(int page, boolean useCache) {

    }

    @Override
    public void setPage(int page) {

    }

    @Override
    public void setTotalCount(int count) {

    }

    @Override
    public Optional<RequestsFilter> getFilter() {
        return Optional.empty();
    }

    @Override
    public int getRecordsPerPage() {
        return 2;
    }

    @Override
    public void setActualRequests(List<Request> requests) {

    }

    @Override
    public void render() {
        System.out.println("DAROVA");




        Scanner in = new Scanner(System.in);

        System.out.println("Input arrivalDate");
        String arrivalDate = in.nextLine();
        Date date1=null;
        try {
            date1=new SimpleDateFormat("dd.MM.yyyy").parse(arrivalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Input departureDate");
        String departureDate = in.nextLine();
        Date date2=null;
        try {
             date2=new SimpleDateFormat("dd.MM.yyyy").parse(departureDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Input number of persons");
        int numberOfPersons = in.nextInt();

        clientRequestsModel.createRequest(date1,date2,1);

    }

}
