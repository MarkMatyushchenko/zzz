package com.github.markmatyushchenko.vt1;
import java.io.*;
import java.util.Scanner;

import com.github.markmatyushchenko.vt1.dataprovider.AppDataProvider;
import com.github.markmatyushchenko.vt1.service.HotelAppService;
import com.github.markmatyushchenko.vt1.view.AppViewModel;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.nio.file.Paths;
import  com.github.markmatyushchenko.vt1.dataprovider.exceptions.CorruptedDataException;
public class App {

	public static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args)  {

		var service = new HotelAppService(
				new AppDataProvider(Paths.get("teststorage")),
				new AppViewModel()
		);

		Navigation navigation = new Navigation(service);

		while (true) {
			System.out.println("######################################");
			System.out.println("#      Please choice the action      #");
			System.out.println("######################################");
			System.out.println("# 1 - Show room types");
			System.out.println("# 2 - Show available rooms");
			System.out.println("# 3 - Login");
			System.out.println("# 4 - Register");
			System.out.println("# 0 - Exit");
			System.out.println("######################################");

			Scanner in = new Scanner(System.in);
			int action = in.nextInt();

			switch (action) {
				case 1:
					navigation.navigateTo("roomTypeView");
					break;
				case 2:
					navigation.navigateTo("availableRoomsView");
					break;
				case 3:
					navigation.navigateTo("loginView");
					break;
				case 4:
					navigation.navigateTo("registerView");
					break;
				case 0:
					return;
			}
		}

	}
}
