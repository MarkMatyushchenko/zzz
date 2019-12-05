package com.github.markmatyushchenko.vt1.entity.roomtype;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RoomTypeBuilder {

    private String typeName;
    private int numOfPlaces;
    private int cost;
    private URL smallImage;
    private List<URL> largeImages;
    private double area;
    private List<Integer> roomNumbers;
    private List<String> services;

    public RoomTypeBuilder() {
        largeImages = new ArrayList<>();
        roomNumbers = new ArrayList<>();
        services = new ArrayList<>();
    }

    public RoomTypeBuilder setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public RoomTypeBuilder setNumOfPlaces(int numOfPlaces) {
        this.numOfPlaces = numOfPlaces;
        return this;
    }

    public RoomTypeBuilder setCost(int cost) {
        this.cost = cost;
        return this;
    }

    public RoomTypeBuilder setSmallImage(URL smallImage) {
        this.smallImage = smallImage;
        return this;
    }

    public RoomTypeBuilder setLargeImages(List<URL> largeImages) {
        this.largeImages = largeImages;
        return this;
    }

    public RoomTypeBuilder setArea(double area) {
        this.area = area;
        return this;
    }

    public RoomTypeBuilder setRoomNumbers(List<Integer> roomNumbers) {
        this.roomNumbers = roomNumbers;
        return this;
    }

    public RoomTypeBuilder setServices(List<String> services) {
        this.services = services;
        return this;
    }

    public RoomType build() {
        return new RoomType(typeName, numOfPlaces, cost, smallImage, largeImages, area, roomNumbers, services);
    }
}
