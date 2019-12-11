package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;

import java.util.Optional;

public interface LoginModel {

	void addViewModel(LoginViewModel viewModel);

	Optional<User> login(String login, String password);
}
