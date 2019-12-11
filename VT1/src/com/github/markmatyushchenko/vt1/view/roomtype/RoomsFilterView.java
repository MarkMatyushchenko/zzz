package com.github.markmatyushchenko.vt1.view.roomtype;

import com.github.markmatyushchenko.vt1.view.CLIView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RoomsFilterView implements CLIView {

	private Date fromDate;
	private Date toDate;

	public Date getFromDate() {
		return fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	@Override
	public void render() {
		Scanner in = new Scanner(System.in);
		DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		boolean flagCorrect = false;

		while (!flagCorrect) {
			try {
				System.out.println("Input from date in format dd.mm.yyyy:");
				String dateFromRaw = in.nextLine();
				fromDate = format.parse(dateFromRaw);

				System.out.println("Input to date in format dd.mm.yyyy:");
				String dateToRaw = in.nextLine();
				toDate = format.parse(dateToRaw);

				flagCorrect = true;
			} catch (ParseException exc) {
				flagCorrect = false;
			}
		}
	}
}
