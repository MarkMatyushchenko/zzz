package com.github.markmatyushchenko.vt1.service.account.port;

import com.github.markmatyushchenko.vt1.entity.user.User;

import java.util.Optional;

public interface AccountViewModel {

	void setAccount(User account);

	Optional<User> getAccount();
}
