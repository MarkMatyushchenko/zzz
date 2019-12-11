package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(propOrder = {"typeName", "numOfPlaces", "cost", "smallImage",
        "largeImages", "area", "roomNumbers", "services"})
public class XMLRoomType {

    private String typeName;
    private int numOfPlaces;
    private int cost;
    private String smallImage;
    private List<String> largeImages;
    private double area;
    private List<Integer> roomNumbers;
    private List<String> services;

    @XmlElement(name = "typeName")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @XmlElement(name = "numOfPlaces")
    public int getNumOfPlaces() {
        return numOfPlaces;
    }

    public void setNumOfPlaces(int numOfPlaces) {
        this.numOfPlaces = numOfPlaces;
    }

    @XmlElement(name = "cost")
    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @XmlElement(name = "smallImage")
    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    @XmlElementWrapper(name = "largeImages")
    @XmlElement(name  = "image")
    public List<String> getLargeImages() {
        return largeImages;
    }

    public void setLargeImages(List<String> largeImages) {
        this.largeImages = largeImages;
    }

    @XmlElement(name = "area")
    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    @XmlElementWrapper(name = "roomNumbers")
    @XmlElement(name = "number")
    public List<Integer> getRoomNumbers() {
        return roomNumbers;
    }

    public void setRoomNumbers(List<Integer> roomNumbers) {
        this.roomNumbers = roomNumbers;
    }

    @XmlElementWrapper(name = "services")
    @XmlElement(name = "service")
    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
}
