package com.github.markmatyushchenko.vt1.repository;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.User;

public interface Repository {

    void saveUsers(Iterable<User> users);
    void saveRoomTypes(Iterable<RoomType> roomTypes);
    void saveRequests(Iterable<Request> requests);

    Iterable<User> getUsers();
    Iterable<RoomType> getRoomTypes();
    Iterable<Request> getRequests();

}
