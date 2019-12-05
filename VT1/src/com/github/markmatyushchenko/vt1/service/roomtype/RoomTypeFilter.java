package com.github.markmatyushchenko.vt1.service.roomtype;

import com.github.markmatyushchenko.vt1.utils.Range;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class RoomTypeFilter {

	private String typeName;
	private Range<Integer> numOfPlaces;
	private Range<Integer> cost;
	private Range<Double> area;
	private List<String> services;

	public RoomTypeFilter() {
		numOfPlaces = Range.empty();
		cost = Range.empty();
		area = Range.empty();
		services = new ArrayList<>();
	}

	public Optional<String> getTypeName() {
		return Optional.ofNullable(typeName);
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Range<Integer> getNumOfPlaces() {
		return numOfPlaces;
	}

	public void setNumOfPlacesFrom(Integer from) {
		numOfPlaces.setFrom(from);
	}

	public void setNumOfPlacesTo(Integer to) {
		numOfPlaces.setTo(to);
	}

	public Range<Integer> getCost() {
		return cost;
	}

	public void setCostFrom(Integer from) {
		cost.setFrom(from);
	}

	public void setCostTo(Integer to) {
		cost.setTo(to);
	}

	public Range<Double> getArea() {
		return area;
	}

	public void setAreaFrom(Double from) {
		area.setFrom(from);
	}

	public void setAreaTo(Double to) {
		area.setTo(to);
	}

	public Stream<String> getServices() {
		return services.stream();
	}

	public void setServices(List<String> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return String.format(
				"RoomTypeFilter(typeName=%s, numOfPlaces=%s, cost=%s, area=%s, services=%s)",
				typeName,
				numOfPlaces,
				cost,
				area,
				services
		);
	}
}
