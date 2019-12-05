package com.github.markmatyushchenko.vt1.entity;

public class Address {

    private String city;
    private String street;
    private int houseNumber;
    private int building;

    public Address(String city, String street, int houseNumber) {
        this(city, street, houseNumber, -1);
    }

    public Address(String city, String street, int houseNumber, int building) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.building = building;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public int getBuilding() {
        return building;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Address)
                && city.equals(((Address) obj).city)
                && street.equals(((Address) obj).street)
                && houseNumber == ((Address) obj).houseNumber
                && building == ((Address) obj).building;
    }

    @Override
    public String toString() {
        return String.format("Address(city=%s, street=%s, houseNumber=%d, building=%d)", city, street, houseNumber, building);
    }
}
