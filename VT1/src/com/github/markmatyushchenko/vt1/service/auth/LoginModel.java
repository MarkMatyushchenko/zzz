package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;

public interface LoginModel {

	void addViewModel(LoginViewModel viewModel);

	void login(String login, String password);
}
