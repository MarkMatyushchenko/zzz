package com.github.markmatyushchenko.vt1.service.port;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.utils.Either;

public interface UsersApi {

	Either<User, Exception> register(String login, String password,
	                                 String firstName, String lastName, String email, String phoneNumber);

	Either<User, Exception> authorize(String login, String password);

	Either<User, Exception> update(User user, String oldPassword, String password,
	                               String firstName, String lastName, String email, String phoneNumber);
}
