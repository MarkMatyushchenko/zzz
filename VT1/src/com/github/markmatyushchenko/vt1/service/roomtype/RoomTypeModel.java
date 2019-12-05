package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.service.roomtype.port.RoomTypeViewModel;

public interface RoomTypeModel {

	void addViewModel(RoomTypeViewModel viewModel);

	void getRoomTypesOnPage(int page, boolean useCache);

	void getAvailableServices();
}
