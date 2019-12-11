package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLClient;

public class XMLClientAdapter {

    public static XMLClient toXml(Client client) {
        XMLClient ans = new XMLClient();
        ans.setLogin(client.getLogin());
        ans.setToken(client.getToken());
        ans.setEmail(client.getEmail());
        ans.setFirstName(client.getFirstName());
        ans.setLastName(client.getLastName());
        ans.setPhoneNumber(client.getPhoneNumber());
        return ans;
    }

    public static Client toClient(XMLClient client) {
        return new Client(
                client.getLogin(),
                client.getToken(),
                client.getEmail(),
                client.getFirstName(),
                client.getLastName(),
                client.getPhoneNumber()
        );
    }

}
