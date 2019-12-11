package com.github.markmatyushchenko.vt1.view.request;

import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.service.request.ClientRequestsModel;
import com.github.markmatyushchenko.vt1.view.CLIView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class NewRequestView implements CLIView {

	private ClientRequestsModel model;

	public NewRequestView(ClientRequestsModel model) {
		this.model = model;
	}

	@Override
	public void render() {
		Scanner in = new Scanner(System.in);
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		boolean flagCorrect = false;

		Date arrivalDate = null;
		Date departureDate = null;
		int numOfPersons = 0;
		while (!flagCorrect) {
			try {
				System.out.println("Input arrival date in format dd.mm.yyyy:");
				String dateFromRaw = in.nextLine();
				arrivalDate = format.parse(dateFromRaw);

				System.out.println("Input departure date in format dd.mm.yyyy:");
				String dateToRaw = in.nextLine();
				departureDate = format.parse(dateToRaw);

				System.out.println("Input number of persons:");
				numOfPersons = in.nextInt();

				flagCorrect = true;
			} catch (ParseException exc) {
				flagCorrect = false;
			}
		}

		Optional<Request> result = model.createRequest(arrivalDate, departureDate, numOfPersons);
		if (result.isPresent()) {
			System.out.println("Successfully created request for " + result.get().getRoomType().getTypeName());
		} else {
			System.out.println("Can't create request");
		}
	}
}
