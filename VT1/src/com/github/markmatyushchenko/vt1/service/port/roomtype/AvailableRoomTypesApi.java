package com.github.markmatyushchenko.vt1.service.port.roomtype;

import com.github.markmatyushchenko.vt1.service.roomtype.AvailableRoomTypesFilter;
import com.github.markmatyushchenko.vt1.service.utils.Pagination;
import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;
import com.github.markmatyushchenko.vt1.utils.Pair;

import java.util.List;

public interface AvailableRoomTypesApi {

	Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception>
	getAvailableRoomTypes(User user, Pagination pagination);

	Either<Pair<List<AvailableRoomTypes>, Pagination>, Exception>
	getAvailableRoomTypes(User user, Pagination pagination, AvailableRoomTypesFilter filter);
}
