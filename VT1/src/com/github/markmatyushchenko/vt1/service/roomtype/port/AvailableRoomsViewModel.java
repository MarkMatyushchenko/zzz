package com.github.markmatyushchenko.vt1.service.roomtype.port;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;

import java.util.List;
import java.util.Optional;

public interface AvailableRoomsViewModel {

	void setPage(int page);

	void setTotalCount(int count);

	Optional<AvailableRoomTypesFilter> getFilter();

	int getRecordsPerPage();

	AvailableRoomTypes getSelectedRoomType();

	void setActualAvailableRoomTypes(List<AvailableRoomTypes> roomTypes);
}
