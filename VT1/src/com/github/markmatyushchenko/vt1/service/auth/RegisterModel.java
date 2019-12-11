package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.auth.port.RegisterViewModel;

import java.util.Optional;

public interface RegisterModel {

	void addViewModel(RegisterViewModel viewModel);

	Optional<User> register(String login, String password, String firstName, String lastName, String email, String phoneNumber);
}
