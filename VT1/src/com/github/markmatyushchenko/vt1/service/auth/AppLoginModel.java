package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;
import com.github.markmatyushchenko.vt1.service.port.exceptions.IncorrectPasswordException;
import com.github.markmatyushchenko.vt1.service.port.exceptions.NoSuchUserException;
import com.github.markmatyushchenko.vt1.utils.Either;

import java.util.Optional;

public class AppLoginModel implements LoginModel {

	private AppService appService;
	private LoginViewModel viewModel;

	public AppLoginModel(AppService appService) {
		this.appService = appService;
	}

	@Override
	public void addViewModel(LoginViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public Optional<User> login(String login, String password) {
		Either<User, Exception> user = appService.getDataProvider().authorize(login, password);
		if (user.isRight()) {
			if (user.getRight() instanceof NoSuchUserException) {
				viewModel.setLoginError("No such user");
			} else if (user.getRight() instanceof IncorrectPasswordException) {
				viewModel.setPasswordError("Incorrect Password");
			} else {
				appService.getViewModel().setError(user.getRight().getMessage());
			}
			return Optional.empty();
		} else {
			appService.getAccountModel().setAccount(user.getLeft());
			return Optional.of(user.getLeft());
		}
	}
}
