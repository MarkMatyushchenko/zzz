package com.github.markmatyushchenko.vt1;

import com.github.markmatyushchenko.vt1.dataprovider.AppDataProvider;
import com.github.markmatyushchenko.vt1.service.HotelAppService;
import com.github.markmatyushchenko.vt1.view.AppViewModel;
import com.github.markmatyushchenko.vt1.view.MainView;
import com.github.markmatyushchenko.vt1.view.Navigation;

import java.nio.file.Paths;

public class App {

	public static void main(String[] args) {

		var service = new HotelAppService(
				new AppDataProvider(Paths.get("teststorage")),
				new AppViewModel()
		);

		Navigation navigation = new Navigation(service);

		var mainView = new MainView(navigation);
		mainView.render();
	}
}
