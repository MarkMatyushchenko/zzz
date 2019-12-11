package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLConfirmedRequest;

import java.net.MalformedURLException;

public class XMLConfirmedRequestAdapter {

    public static ConfirmedRequest toRequest(XMLConfirmedRequest xmlRequest) throws MalformedURLException {
        return new ConfirmedRequest(
                XMLRoomTypeAdapter.toRoomType(xmlRequest.getRoomType()),
                xmlRequest.getArrivalDate(),
                xmlRequest.getDepartureDate(),
                xmlRequest.getNumberOfPersons(),
                XMLClientAdapter.toClient(xmlRequest.getCustomer()),
                xmlRequest.getRoomNumber()
        );
    }

    public static XMLConfirmedRequest toXmlRequest(ConfirmedRequest request) {
        XMLConfirmedRequest ans = new XMLConfirmedRequest();
        ans.setRoomType(XMLRoomTypeAdapter.toXmlRoomType(request.getRoomType()));
        ans.setArrivalDate(request.getArrivalDate());
        ans.setDepartureDate(request.getDepartureDate());
        ans.setCustomer(XMLClientAdapter.toXml(request.getCustomer()));
        ans.setNumberOfPersons(request.getNumberOfPersons());
        ans.setRoomNumber(request.getRoomNumber());
        return ans;
    }

}
