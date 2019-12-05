package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.utils.Range;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailableRoomTypesFilter extends RoomTypeFilter {

	private Range<Integer> availablePlaces;
	private Date arrivalDate;
	private Date departureDate;

	public AvailableRoomTypesFilter() {
		super();
		availablePlaces = Range.empty();
	}

	public Range<Integer> getAvailablePlaces() {
		return availablePlaces;
	}

	public void setAvailablePlacesFrom(Integer from) {
		availablePlaces.setFrom(from);
	}

	public void setAvailablePlacesTo(Integer to) {
		availablePlaces.setTo(to);
	}

	public Optional<Date> getArrivalDate() {
		return Optional.ofNullable(arrivalDate);
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Optional<Date> getDepartureDate() {
		return Optional.ofNullable(departureDate);
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	@Override
	public String toString() {
		return String.format(
				"AvailableRoomsFilter(typeName=%s, numOfPlaces=%s, cost=%s, area=%s, services=%s, availablePlaces=%s, arrivalDate=%s, departureDate=%s)",
				getTypeName(),
				getNumOfPlaces(),
				getCost(),
				getArea(),
				getServices().collect(Collectors.toList()),
				availablePlaces,
				arrivalDate,
				departureDate
		);
	}
}
