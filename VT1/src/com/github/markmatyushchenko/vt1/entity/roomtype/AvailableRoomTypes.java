package com.github.markmatyushchenko.vt1.entity.roomtype;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

public class AvailableRoomTypes extends RoomType {

	private int availableRooms;

	public AvailableRoomTypes(String typeName, int numOfPlaces, int cost, URL smallImage, List<URL> largeImages, double area, List<Integer> roomNumbers, List<String> services, int availableRooms) {
		super(typeName, numOfPlaces, cost, smallImage, largeImages, area, roomNumbers, services);
		this.availableRooms = availableRooms;
	}

	public AvailableRoomTypes(RoomType roomType, int availableRooms) {
		super(
				roomType.getTypeName(),
				roomType.getNumOfPlaces(),
				roomType.getCost(),
				roomType.getSmallImage().orElse(null),
				roomType.getLargeImages().collect(Collectors.toList()),
				roomType.getArea(),
				roomType.getRoomNumbers().collect(Collectors.toList()),
				roomType.getServices().collect(Collectors.toList())
		);
		this.availableRooms = availableRooms;
	}

	public int getAvailableRooms() {
		return availableRooms;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof AvailableRoomTypes)
				&& super.equals(obj)
				&& availableRooms == ((AvailableRoomTypes) obj).availableRooms;
	}

	@Override
	public String toString() {
		return String.format(
				"AvailableRoomTypes(availableRooms=%d, typeName=%s, numOfPlaces=%d, cost=%d, area=%f, services=%s)",
				availableRooms,
				getTypeName(),
				getNumOfPlaces(),
				getCost(),
				getArea(),
				getServices().collect(Collectors.toList())
		);
	}
}
