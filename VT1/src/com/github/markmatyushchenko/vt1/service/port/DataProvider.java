package com.github.markmatyushchenko.vt1.service.port;

import com.github.markmatyushchenko.vt1.service.port.roomtype.AvailableRoomTypesApi;
import com.github.markmatyushchenko.vt1.service.port.roomtype.RoomTypeApi;

public interface DataProvider extends UsersApi, RoomTypeApi, AvailableRoomTypesApi, RequestsApi {

}
