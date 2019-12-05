package com.github.markmatyushchenko.vt1.service.account;

import com.github.markmatyushchenko.vt1.service.account.port.AccountViewModel;
import com.github.markmatyushchenko.vt1.entity.user.User;

import java.util.Optional;

public interface AccountModel {

	void addViewModel(AccountViewModel viewModel);

	Optional<User> getAccount();

	void setAccount(User user);

	void update(String oldPassword, String password, String firstName, String lastName, String email, String phoneNumber);
}
