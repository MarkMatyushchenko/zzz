package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlRootElement(name = "data")
@XmlType(propOrder = {"list"})
@XmlSeeAlso({XMLUser.class, XMLClient.class, XMLAdministrator.class,
        XMLRequest.class, XMLRejectedRequest.class, XMLConfirmedRequest.class,
        XMLRoomType.class, XMLAvailableRoomType.class})
public class XMLObjectsList<T> {

    private List<T> list;

    @XmlElement(name = "item")
    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
