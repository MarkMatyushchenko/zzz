package com.github.markmatyushchenko.vt1.service.roomtype.port;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;

import java.util.List;
import java.util.Optional;

public interface RoomTypeViewModel {

	void setPage(int page);

	void setTotalCount(int count);

	Optional<RoomTypeFilter> getFilter();

	int getRecordsPerPage();

	void setActualRoomTypes(List<RoomType> roomTypes);
}
