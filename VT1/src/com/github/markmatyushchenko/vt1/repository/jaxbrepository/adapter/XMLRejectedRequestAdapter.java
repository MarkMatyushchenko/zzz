package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLRejectedRequest;

import java.net.MalformedURLException;

public class XMLRejectedRequestAdapter {

    public static RejectedRequest toRequest(XMLRejectedRequest xmlRequest) throws MalformedURLException {
        return new RejectedRequest(
                XMLRoomTypeAdapter.toRoomType(xmlRequest.getRoomType()),
                xmlRequest.getArrivalDate(),
                xmlRequest.getDepartureDate(),
                xmlRequest.getNumberOfPersons(),
                XMLClientAdapter.toClient(xmlRequest.getCustomer()),
                xmlRequest.getComment()
        );
    }

    public static XMLRejectedRequest toXmlRequest(RejectedRequest request) {
        XMLRejectedRequest ans = new XMLRejectedRequest();
        ans.setRoomType(XMLRoomTypeAdapter.toXmlRoomType(request.getRoomType()));
        ans.setArrivalDate(request.getArrivalDate());
        ans.setDepartureDate(request.getDepartureDate());
        ans.setCustomer(XMLClientAdapter.toXml(request.getCustomer()));
        ans.setNumberOfPersons(request.getNumberOfPersons());
        ans.setComment(request.getComment());
        return ans;
    }

}
