package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;
import com.github.markmatyushchenko.vt1.service.port.exceptions.IncorrectPasswordException;
import com.github.markmatyushchenko.vt1.service.port.exceptions.NoSuchUserException;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.utils.Either;

public class AppLoginModel implements LoginModel{

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
	public void login(String login, String password) {
		viewModel.setLoginError("Error");
		Either<User, Exception> user = appService.getDataProvider().authorize(login, password);
		if (user.isRight()) {
			if (user.getRight() instanceof NoSuchUserException) {
				viewModel.setLoginError("No such user");
				viewModel.setLogin("");
			} else if (user.getRight() instanceof IncorrectPasswordException) {
				viewModel.setPasswordError("Incorrect Password");
			} else {
				appService.getViewModel().setError(user.getRight().getMessage());
			}
			viewModel.setPassword("");
		} else {
			appService.getAccountModel().setAccount(user.getLeft());
			viewModel.setLoginError("");
			viewModel.setPasswordError("");
		}
	}
}
