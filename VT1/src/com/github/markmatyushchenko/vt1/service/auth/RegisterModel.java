package com.github.markmatyushchenko.vt1.service.auth;

import com.github.markmatyushchenko.vt1.service.auth.port.RegisterViewModel;

public interface RegisterModel {

	void addViewModel(RegisterViewModel viewModel);

	void register(String login, String password, String firstName, String lastName, String email, String phoneNumber);
}
