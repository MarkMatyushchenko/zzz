package com.github.markmatyushchenko.vt1.view;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesModel;
import com.github.markmatyushchenko.vt1.service.roomtype.port.AvailableRoomsViewModel;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AvailableRoomsView implements CLIView, AvailableRoomsViewModel {

    private AvailableRoomTypesModel availableRoomTypesModel;

    private Navigation navigation;

    public  AvailableRoomsView(AvailableRoomTypesModel availableRoomTypesModel, Navigation navigation){
        this.availableRoomTypesModel = availableRoomTypesModel;
        this.navigation = navigation;
    }


    @Override
    public void setPage(int page) {

    }

    @Override
    public void setTotalCount(int count) {

    }

    @Override
    public Optional<AvailableRoomTypesFilter> getFilter() {
        return Optional.empty();
    }

    @Override
    public void setAvailableServices(List<String> services) {

    }

    @Override
    public int getRecordsPerPage() {

        return 0;
    }

    @Override
    public AvailableRoomTypes getSelectedRoomType() {
        return null;
    }

    @Override
    public void setActualAvailableRoomTypes(List<AvailableRoomTypes> roomTypes) {
        int i=0;
        roomTypes.forEach((roomType -> {

            System.out.println(roomType.getTypeName() + " " + roomType.getCost() + " " + roomType.getServices().collect(Collectors.joining())+"\n");
        }));
    }

    @Override
    public void render() {
        int page = 1;
        availableRoomTypesModel.getRoomTypesOnPage(page, true);
      //  Scanner scan = new Scanner(System.in);
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

       /* System.out.println("" +
                "2 - next page\n");
        var nextPage =scan.nextInt();
        page++;
        switch (nextPage){
            case 2:{
                availableRoomTypesModel.getRoomTypesOnPage(page, true);
                break;
            }
        }*/
    }
}
