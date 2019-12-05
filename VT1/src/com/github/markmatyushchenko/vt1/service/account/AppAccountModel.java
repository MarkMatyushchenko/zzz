package com.github.markmatyushchenko.vt1.service.account;

import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;
import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.AppService;
import com.github.markmatyushchenko.vt1.utils.Either;

import java.util.Optional;

public class AppAccountModel implements AccountModel {

	private AppService appService;
	private AccountViewModel viewModel;

	public AppAccountModel(AppService appService) {
		this.appService = appService;
		this.viewModel = new AccountViewModelComposer();
	}

	@Override
	public void addViewModel(AccountViewModel viewModel) {
		((AccountViewModelComposer) this.viewModel).addViewModel(viewModel);
	}

	@Override
	public Optional<User> getAccount() {
		return viewModel.getAccount();
	}

	@Override
	public void setAccount(User user) {
		viewModel.setAccount(user);
	}

	@Override
	public void update(String oldPassword, String password, String firstName, String lastName, String email, String phoneNumber) {
		viewModel.getAccount().ifPresent(user -> {
			Either<User, Exception> updatedUser = appService.getDataProvider().update(user,
					oldPassword, password, firstName, lastName, email, phoneNumber);
			if (updatedUser.isRight()) {
				appService.getViewModel().setError(updatedUser.getRight().getMessage());
			} else {
				setAccount(updatedUser.getLeft());
			}
		});
	}
}
