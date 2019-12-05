package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.service.roomtype.port.AvailableRoomsViewModel;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;

public interface AvailableRoomTypesModel {

	void addViewModel(AvailableRoomsViewModel viewModel);

	void getRoomTypesOnPage(int page, boolean useCache);

	void getAvailableServices();

	AvailableRoomTypes getSelectedRoomType();
}
