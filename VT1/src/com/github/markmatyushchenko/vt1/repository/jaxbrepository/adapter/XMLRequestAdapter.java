package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLRequest;

import java.net.MalformedURLException;

public class XMLRequestAdapter {

    public static Request toRequest(XMLRequest xmlRequest) throws MalformedURLException {
        return new Request(
                XMLRoomTypeAdapter.toRoomType(xmlRequest.getRoomType()),
                xmlRequest.getArrivalDate(),
                xmlRequest.getDepartureDate(),
                xmlRequest.getNumberOfPersons(),
                XMLClientAdapter.toClient(xmlRequest.getCustomer())
        );
    }

    public static XMLRequest toXmlRequest(Request request) {
        XMLRequest ans = new XMLRequest();
        ans.setRoomType(XMLRoomTypeAdapter.toXmlRoomType(request.getRoomType()));
        ans.setArrivalDate(request.getArrivalDate());
        ans.setDepartureDate(request.getDepartureDate());
        ans.setCustomer(XMLClientAdapter.toXml(request.getCustomer()));
        ans.setNumberOfPersons(request.getNumberOfPersons());
        return ans;
    }

}
