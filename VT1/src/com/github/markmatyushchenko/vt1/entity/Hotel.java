package com.github.markmatyushchenko.vt1.entity;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Hotel {

    private Address address;
    private List<PhoneContact> phoneContacts;
    private List<RoomType> roomTypes;

    public Hotel(Address address, List<RoomType> roomTypes) {
        this(address, new ArrayList<>(), roomTypes);
    }

    public Hotel(Address address, List<PhoneContact> phoneContacts, List<RoomType> roomTypes) {
        this.address = address;
        this.phoneContacts = phoneContacts;
        this.roomTypes = roomTypes;
    }

    public Address getAddress() {
        return address;
    }

    public Stream<PhoneContact> getPhoneContacts() {
        return phoneContacts.stream();
    }

    public Stream<RoomType> getRoomTypes() {
        return roomTypes.stream();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Hotel)
                && address.equals(((Hotel) obj).address);
    }

    @Override
    public String toString() {
        return String.format("Hotel(address=%s, phoneContacts=%s, roomTypes=%s)", address, phoneContacts, roomTypes);
    }
}
