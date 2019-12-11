package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.user.Administrator;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLAdministrator;

public class XMLAdministratorAdapter {

    public static XMLAdministrator toXml(Administrator administrator) {
        XMLAdministrator ans = new XMLAdministrator();
        ans.setLogin(administrator.getLogin());
        ans.setToken(administrator.getToken());
        ans.setFirstName(administrator.getFirstName());
        ans.setLastName(administrator.getLastName());
        return ans;
    }

    public static Administrator toAdministrator(XMLAdministrator administrator) {
        return new Administrator(
                administrator.getLogin(),
                administrator.getToken(),
                administrator.getFirstName(),
                administrator.getLastName()
        );
    }

}
