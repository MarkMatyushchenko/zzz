package com.github.markmatyushchenko.vt1.service.auth.port;

public interface LoginViewModel {

	void setLogin(String login);
	void setPassword(String password);
	void setLoginError(String error);
	void setPasswordError(String error);
}
