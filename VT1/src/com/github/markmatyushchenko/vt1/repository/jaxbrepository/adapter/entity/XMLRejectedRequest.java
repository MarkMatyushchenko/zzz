package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

@XmlType(propOrder = {"roomType", "arrivalDate", "departureDate", "numberOfPersons", "customer", "comment"})
public class XMLRejectedRequest {

    private XMLRoomType roomType;
    private Date arrivalDate;
    private Date departureDate;
    private int numberOfPersons;
    private XMLClient customer;

    private String comment;

    @XmlElement(name = "roomType")
    public XMLRoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(XMLRoomType roomType) {
        this.roomType = roomType;
    }

    @XmlElement(name = "arrivalDate")
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @XmlElement(name = "departureDate")
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    @XmlElement(name = "numberOfPersons")
    public int getNumberOfPersons() {
        return numberOfPersons;
    }

    public void setNumberOfPersons(int numberOfPersons) {
        this.numberOfPersons = numberOfPersons;
    }

    @XmlElement(name = "customer")
    public XMLClient getCustomer() {
        return customer;
    }

    public void setCustomer(XMLClient customer) {
        this.customer = customer;
    }

    @XmlElement(name = "comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
