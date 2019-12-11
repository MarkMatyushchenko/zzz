package com.github.markmatyushchenko.vt1.view.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.account.AccountModel;
import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;

import java.util.Optional;

public class ProfileView implements AccountViewModel {

	private AccountModel accountModel;
	private User user;

	public ProfileView(AccountModel accountModel) {
		this.accountModel = accountModel;
		this.user = User.GUEST;
	}

	@Override
	public void setAccount(User account) {
		this.user = account;
	}

	@Override
	public Optional<User> getAccount() {
		return Optional.ofNullable(user);
	}
}
