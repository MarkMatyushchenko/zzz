package com.github.markmatyushchenko.vt1.entity.request;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.util.Date;

public class ConfirmedRequest extends Request {

    private int roomNumber;

    public ConfirmedRequest(Request request, int roomNumber) {
        this(request.getRoomType(), request.getArrivalDate(), request.getDepartureDate(), request.getNumberOfPersons(), request.getCustomer(), roomNumber);
    }

    public ConfirmedRequest(RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons, Client customer, int roomNumber) {
        super(roomType, arrivalDate, departureDate, numberOfPersons, customer);
        this.roomNumber = roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof ConfirmedRequest)
                && super.equals(obj)
                && roomNumber == ((ConfirmedRequest) obj).roomNumber;
    }

    @Override
    public String toString() {
        return String.format("ConfirmedRequest(roomNumber=%d, roomType=%s, arrivalDate=%s, departureDate=%s, numberOfPersons=%d, customer=%s)", roomNumber, getRoomType(), getArrivalDate(), getDepartureDate(), getNumberOfPersons(), getCustomer());
    }
}
