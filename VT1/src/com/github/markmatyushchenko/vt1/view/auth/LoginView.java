package com.github.markmatyushchenko.vt1.view.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.auth.LoginModel;
import com.github.markmatyushchenko.vt1.service.auth.port.LoginViewModel;
import com.github.markmatyushchenko.vt1.view.CLIView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.util.Optional;
import java.util.Scanner;

public class LoginView implements CLIView, LoginViewModel {

	private Navigation navigation;
	private LoginModel loginModel;

	public LoginView(LoginModel loginModel, Navigation navigation) {
		this.loginModel = loginModel;
		this.navigation = navigation;
	}

	@Override
	public void setLoginError(String error) {
		System.out.println(error);
	}

	@Override
	public void setPasswordError(String error) {
		System.out.println(error);
	}

	@Override
	public void render() {
		Scanner in = new Scanner(System.in);

		Optional<User> result = Optional.empty();
		while (result.isEmpty()) {
			System.out.println("Input login:");
			String login = in.nextLine();

			System.out.println("Input password:");
			String password = in.nextLine();

			result = loginModel.login(login, password);
		}
		System.out.println("Logged as " + result.get().getLogin());
	}
}