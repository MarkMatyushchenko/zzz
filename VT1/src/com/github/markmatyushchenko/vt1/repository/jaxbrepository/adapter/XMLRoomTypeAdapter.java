package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLRoomType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class XMLRoomTypeAdapter {

    public static XMLRoomType toXmlRoomType(RoomType roomType) {
        XMLRoomType ans = new XMLRoomType();
        ans.setTypeName(roomType.getTypeName());
        ans.setArea(roomType.getArea());
        ans.setCost(roomType.getCost());
        ans.setNumOfPlaces(roomType.getNumOfPlaces());
        ans.setSmallImage(roomType.getSmallImage().toString());
        ans.setLargeImages(roomType.getLargeImages().map(URL::toString).collect(Collectors.toList()));
        ans.setRoomNumbers(roomType.getRoomNumbers().collect(Collectors.toList()));
        ans.setServices(roomType.getServices().collect(Collectors.toList()));
        return ans;
    }

    public static RoomType toRoomType(XMLRoomType xmlRoomType) throws MalformedURLException {
        return new RoomType(
                xmlRoomType.getTypeName(),
                xmlRoomType.getNumOfPlaces(),
                xmlRoomType.getCost(),
                xmlRoomType.getSmallImage().isEmpty() ? null : new URL(xmlRoomType.getSmallImage()),
                xmlRoomType.getLargeImages().stream().map(x -> {
                    URL ans = null;
                    try {
                        ans = new URL(x);
                    } catch (MalformedURLException e) {}
                    return ans;
                }).collect(Collectors.toList()),
                xmlRoomType.getArea(),
                xmlRoomType.getRoomNumbers(),
                xmlRoomType.getServices()
        );
    }

}
