package com.github.markmatyushchenko.vt1.service.port.roomtype;

import com.github.markmatyushchenko.vt1.service.roomtype.RoomTypeFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.List;

public interface RoomTypeApi {

	Either<List<RoomType>, Exception> getAllRoomTypes(User user);

	Either<Pair<List<RoomType>, Pagination>, Exception>
	getRoomTypes(User user, Pagination pagination);

	Either<Pair<List<RoomType>, Pagination>, Exception>
	getRoomTypes(User user, Pagination pagination, RoomTypeFilter filter);

	Either<List<String>, Exception> getAvailableServices(User user);
}
