package com.github.markmatyushchenko.vt1.view.auth;

import com.github.markmatyushchenko.vt1.entity.user.User;
import com.github.markmatyushchenko.vt1.service.auth.RegisterModel;
import com.github.markmatyushchenko.vt1.service.auth.port.RegisterViewModel;
import com.github.markmatyushchenko.vt1.view.CLIView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.util.Optional;
import java.util.Scanner;

public class RegisterView implements CLIView, RegisterViewModel {

	private Navigation navigation;
	private RegisterModel registerModel;

	public RegisterView(RegisterModel registerModel, Navigation navigation) {
		this.registerModel = registerModel;
		this.navigation = navigation;
	}

	@Override
	public void setLoginError(String loginError) {
		System.out.println("Ошибка");
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

			System.out.println("Email:");
			String email = in.nextLine();

			System.out.println("First name:");
			String name = in.nextLine();

			System.out.println("Last name:");
			String lastName = in.nextLine();

			System.out.println("Phone number:");
			String phoneNumber = in.nextLine();

			result = registerModel.register(login, password, email, name, lastName, phoneNumber);
		}
		System.out.println("Logged as " + result.get().getLogin());
	}
}
