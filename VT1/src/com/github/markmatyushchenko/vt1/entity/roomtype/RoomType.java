package com.github.markmatyushchenko.vt1.entity.roomtype;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RoomType {

    private String typeName;
    private int numOfPlaces;
    private int cost;
    private URL smallImage;
    private List<URL> largeImages;
    private double area;
    private List<Integer> roomNumbers;
    private List<String> services;

    public RoomType(String typeName, int numOfPlaces, int cost, URL smallImage, List<URL> largeImages, double area, List<Integer> roomNumbers, List<String> services) {
        this.typeName = typeName;
        this.numOfPlaces = numOfPlaces;
        this.cost = cost;
        this.smallImage = smallImage;
        this.largeImages = largeImages;
        this.area = area;
        this.roomNumbers = roomNumbers;
        this.services = services;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getNumOfPlaces() {
        return numOfPlaces;
    }

    public int getCost() {
        return cost;
    }

    public Optional<URL> getSmallImage() {
        return Optional.ofNullable(smallImage);
    }

    public Stream<URL> getLargeImages() {
        return largeImages.stream();
    }

    public double getArea() {
        return area;
    }

    public Stream<Integer> getRoomNumbers() {
        return roomNumbers.stream();
    }

    public Stream<String> getServices() {
        return services.stream();
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof RoomType)
                && typeName.equals(((RoomType) obj).typeName)
                && numOfPlaces == ((RoomType) obj).numOfPlaces
                && cost == ((RoomType) obj).cost
                && area == ((RoomType) obj).area
                && services.equals(((RoomType) obj).services);
    }

    @Override
    public String toString() {
        return String.format("RoomType(typeName=%s, numOfPlaces=%d, cost=%d, area=%f, services=%s)", typeName, numOfPlaces, cost, area, services);
    }
}
