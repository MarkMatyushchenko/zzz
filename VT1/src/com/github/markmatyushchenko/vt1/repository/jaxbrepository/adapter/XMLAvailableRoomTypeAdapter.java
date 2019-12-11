package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.roomtype.AvailableRoomTypes;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLAvailableRoomType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class XMLAvailableRoomTypeAdapter {

    public static XMLAvailableRoomType toXmlRoomType(AvailableRoomTypes roomType) {
        XMLAvailableRoomType ans = new XMLAvailableRoomType();
        ans.setTypeName(roomType.getTypeName());
        ans.setArea(roomType.getArea());
        ans.setCost(roomType.getCost());
        ans.setNumOfPlaces(roomType.getNumOfPlaces());
        ans.setSmallImage(roomType.getSmallImage().toString());
        ans.setLargeImages(roomType.getLargeImages().map(URL::toString).collect(Collectors.toList()));
        ans.setRoomNumbers(roomType.getRoomNumbers().collect(Collectors.toList()));
        ans.setServices(roomType.getServices().collect(Collectors.toList()));
        ans.setAvailableRooms(roomType.getAvailableRooms());
        return ans;
    }

    public static AvailableRoomTypes toAvailableRoomTypes(XMLAvailableRoomType xmlRoomType) throws MalformedURLException {
        return new AvailableRoomTypes(
                xmlRoomType.getTypeName(),
                xmlRoomType.getNumOfPlaces(),
                xmlRoomType.getCost(),
                new URL(xmlRoomType.getSmallImage()),
                xmlRoomType.getLargeImages().stream().map(x -> {
                    URL ans = null;
                    try {
                        ans = new URL(x);
                    } catch (MalformedURLException e) {}
                    return ans;
                }).collect(Collectors.toList()),
                xmlRoomType.getArea(),
                xmlRoomType.getRoomNumbers(),
                xmlRoomType.getServices(),
                xmlRoomType.getAvailableRooms()
        );
    }

}
