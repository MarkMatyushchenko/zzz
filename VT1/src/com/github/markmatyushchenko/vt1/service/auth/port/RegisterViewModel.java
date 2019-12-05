package com.github.markmatyushchenko.vt1.service.auth.port;

public interface RegisterViewModel {

	void setLogin(String login);
	void setLoginError(String loginError);

	void setPassword(String password);

	void setEmail(String email);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	void setPhoneNumber(String phoneNumber);
}
