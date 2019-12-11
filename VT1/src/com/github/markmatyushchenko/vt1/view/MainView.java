package com.github.markmatyushchenko.vt1.view;

import java.util.Scanner;

public class MainView implements CLIView {

	private Navigation navigation;

	public MainView(Navigation navigation) {
		this.navigation = navigation;
	}

	@Override
	public void render() {
		Scanner in = new Scanner(System.in);
		int action = -1;

		while (action != 0) {
			System.out.println("┌────────────────────────────────────┐");
			System.out.println("│      Please choice the action      │");
			System.out.println("├────────────────────────────────────┤");
			System.out.println("├─ 1 - Show room types               │");
			System.out.println("├─ 2 - Show available rooms          │");
			System.out.println("├─ 3 - Show requests                 │");
			System.out.println("├─ 4 - Login                         │");
			System.out.println("├─ 5 - Register                      │");
			System.out.println("├─ 0 - Exit                          │");
			System.out.println("└────────────────────────────────────┘");
			System.out.print("↪ ");

			action = in.nextInt();

			switch (action) {
				case 1:
					navigation.navigateTo(Navigation.Route.ROOM_TYPES);
					break;
				case 2:
					navigation.navigateTo(Navigation.Route.AVAILABLE_ROOMS);
					break;
				case 3:
					navigation.navigateTo(Navigation.Route.REQUESTS);
					break;
				case 4:
					navigation.navigateTo(Navigation.Route.LOGIN);
					break;
				case 5:
					navigation.navigateTo(Navigation.Route.REGISTER);
					break;
			}
		}
	}
}
