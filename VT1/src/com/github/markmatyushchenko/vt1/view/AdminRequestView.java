package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.request.AdminRequestsModel;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;

import java.util.List;
import java.util.Optional;

public class AdminRequestView implements CLIView,AdminRequestsModel,RequestsViewModel {


    private AdminRequestsModel adminRequestsModel;
    private Navigation navigation;

    public  AdminRequestView(AdminRequestsModel adminRequestsModel, Navigation navigation){
        this.navigation = navigation;
        this.adminRequestsModel = adminRequestsModel;
    }

    @Override
    public void confirmRequest(Request request, int roomNumber) {

    }

    @Override
    public void rejectRequest(Request request, String comment) {

    }

    @Override
    public void addViewModel(RequestsViewModel viewModel) {

    }

    @Override
    public void getRequestsOnPage(int page, boolean useCache) {

    }

    @Override
    public void render() {

        System.out.println("DAROVA1");
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
        return 0;
    }

    @Override
    public void setActualRequests(List<Request> requests) {

    }
}
