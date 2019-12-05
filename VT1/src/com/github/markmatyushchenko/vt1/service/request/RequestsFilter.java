package com.github.markmatyushchenko.vt1.service.request;

import com.github.markmatyushchenko.vt1.utils.Range;

import java.util.Date;
import java.util.Optional;

public class RequestsFilter {

	private boolean isConfirmed;
	private boolean isRejected;
	private String roomTypeName;
	private String customerLogin;
	private Range<Integer> numberOfPersons;
	private Range<Date> arrivalDate;
	private Range<Date> departureDate;

	public RequestsFilter() {
		numberOfPersons = Range.empty();
		arrivalDate = Range.empty();
		departureDate = Range.empty();
		isConfirmed = false;
		isRejected = false;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean confirmed) {
		isConfirmed = confirmed;
	}

	public boolean isRejected() {
		return isRejected;
	}

	public void setRejected(boolean rejected) {
		isRejected = rejected;
	}

	public Optional<String> getRoomTypeName() {
		return Optional.ofNullable(roomTypeName);
	}

	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}

	public Optional<String> getCustomerLogin() {
		return Optional.ofNullable(customerLogin);
	}

	public void setCustomerLogin(String login) {
		customerLogin = login;
	}

	public Range<Integer> getNumberOfPersons() {
		return numberOfPersons;
	}

	public void setNumberOfPersonsFrom(Integer from) {
		numberOfPersons.setFrom(from);
	}

	public void setNumberOfPersonsTo(Integer to) {
		numberOfPersons.setTo(to);
	}

	public Range<Date> getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDateFrom(Date from) {
		arrivalDate.setFrom(from);
	}

	public void setArrivalDateTo(Date to) {
		arrivalDate.setTo(to);
	}

	public Range<Date> getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDateFrom(Date from) {
		departureDate.setFrom(from);
	}

	public void setDepartureDateTo(Date to) {
		departureDate.setTo(to);
	}

	@Override
	public String toString() {
		return String.format(
				"RequestsFilter(isConfirmed=%b, isRejected=%b, roomTypeName=%s, customerLogin=%s, numberOfPersons=%s, arrivalDate=%s, departureDate=%s)",
				isConfirmed,
				isRejected,
				roomTypeName,
				customerLogin,
				numberOfPersons,
				arrivalDate,
				departureDate
		);
	}
}
