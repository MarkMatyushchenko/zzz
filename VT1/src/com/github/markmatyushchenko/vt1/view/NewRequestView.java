package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.request.RequestsFilter;
import com.github.markmatyushchenko.vt1.service.request.port.RequestsViewModel;

import java.util.List;
import java.util.Optional;

public class NewRequestView implements RequestsViewModel {


    private NewRequestView newRequestView;

    public NewRequestView(NewRequestView newRequestView)
    {
        this.newRequestView = newRequestView;
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
