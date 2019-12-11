package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.service.roomtype.port.AvailableRoomsViewModel;

public interface AvailableRoomTypesModel {

	void addViewModel(AvailableRoomsViewModel viewModel);

	void getRoomTypesOnPage(int page, boolean useCache);

	AvailableRoomTypes getSelectedRoomType();
}
