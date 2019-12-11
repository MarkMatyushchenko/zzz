package com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.repository.jaxbrepository.adapter.entity.XMLUser;

public class XMLUserAdapter {

	public static XMLUser toXml(User user) {
		XMLUser ans = new XMLUser();
		ans.setLogin(user.getLogin());
		ans.setToken(user.getToken());
		return ans;
	}

	public static User toUser(XMLUser user) {
		return new User(user.getLogin(), user.getToken());
	}

}
