package com.github.markmatyushchenko.vt1.entity.request;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.util.Date;

public class Request {

    private RoomType roomType;
    private Date arrivalDate;
    private Date departureDate;
    private int numberOfPersons;
    private Client customer;

    public Request(RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons, Client customer) {
        this.roomType = roomType;
        this.arrivalDate = arrivalDate;
        this.departureDate = departureDate;
        this.numberOfPersons = numberOfPersons;
        this.customer = customer;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public Client getCustomer() {
        return customer;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Request) && roomType.equals(((Request) obj).roomType)
                && arrivalDate.equals(((Request) obj).arrivalDate)
                && departureDate.equals(((Request) obj).departureDate)
                && numberOfPersons == ((Request) obj).numberOfPersons
                && customer.equals(((Request) obj).customer);
    }

    @Override
    public String toString() {
        return String.format("Request(roomType=%s, arrivalDate=%s, departureDate=%s, numberOfPersons=%d, customer=%s)", roomType, arrivalDate, departureDate, numberOfPersons, customer);
    }
}
