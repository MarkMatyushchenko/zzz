package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.service.auth.port.RegisterViewModel;
import com.github.markmatyushchenko.vt1.service.port.exceptions.ExistingAccountException;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.utils.Either;

public class AppRegisterModel implements RegisterModel {

	private AppService appService;
	private RegisterViewModel viewModel;

	public AppRegisterModel(AppService appService) {
		this.appService = appService;
	}

	@Override
	public void addViewModel(RegisterViewModel viewModel) {
		this.viewModel = viewModel;
	}

	@Override
	public void register(String login, String password, String firstName, String lastName, String email, String phoneNumber) {
		Either<User, Exception> user = appService.getDataProvider().register(login, password, firstName, lastName, email, phoneNumber);
		if (user.isRight()) {
			if (user.getRight() instanceof ExistingAccountException) {
				viewModel.setLoginError("Login already exists");
				//viewModel.setLogin("");
			} else {
				appService.getViewModel().setError(user.getRight().getMessage());
			}
		//	viewModel.setPassword("");
		} else {
			appService.getAccountModel().setAccount(user.getLeft());
		}
	}
}
