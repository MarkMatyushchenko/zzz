package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeModel;
import com.github.markmatyushchenko.vt1.service.roomtype.port.RoomTypeViewModel;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class RoomTypesView implements CLIView, RoomTypeViewModel {

    private RoomTypeModel model;
    private Navigation navigation;

    public RoomTypesView(RoomTypeModel model, Navigation navigation)
    {
        this.model = model;
        this.navigation = navigation;
    }

    @Override
    public void render() {
        int page = 1;
        model.getRoomTypesOnPage(page, true);
        Scanner scan = new Scanner(System.in);
        /*
        System.out.println("" +
                "1 - refresh\n");
        var choice = scan.nextInt();
        switch (choice) {
            case 1: {
                model.getRoomTypesOnPage(1, true);
                break;
            }
        }*/
        //scan.nextInt();
        System.out.println("" +
                "2 - next page\n");
        var nextPage =scan.nextInt();
        page++;
        switch (nextPage){
            case 2:{
                model.getRoomTypesOnPage(page, true);
                break;
            }
        }

    }

    @Override
    public void setPage(int page) {

    }

    @Override
    public void setTotalCount(int count) {
        System.out.println("Show 5 of "+ count);
    }

    @Override
    public Optional<RoomTypeFilter> getFilter() {
        return Optional.empty();
    }

    @Override
    public void setAvailableServices(List<String> services) {

    }

    @Override
    public int getRecordsPerPage() {
        return 5;
    }

    @Override
    public void setActualRoomTypes(List<RoomType> roomTypes) {
        roomTypes.forEach((roomType -> {
            System.out.println(roomType.getTypeName() + " " + roomType.getCost() + " " + roomType.getServices().collect(Collectors.joining())+"\n");
        }));
    }
}
