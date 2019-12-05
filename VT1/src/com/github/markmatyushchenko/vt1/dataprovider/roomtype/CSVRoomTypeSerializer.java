package com.github.markmatyushchenko.vt1.dataprovider.roomtype;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class CSVRoomTypeSerializer {

	String serializeRoomType(RoomType roomType) {
		return String.format(
				"%s|%s|%s|%s|%s|%s|%s|%s",
				roomType.getTypeName(),
				Integer.toString(roomType.getNumOfPlaces()),
				Integer.toString(roomType.getCost()),
				roomType.getSmallImage().orElse(null),
				roomType.getLargeImages().collect(Collectors.toList()),
				Double.toString(roomType.getArea()),
				roomType.getRoomNumbers().collect(Collectors.toList()),
				roomType.getServices().collect(Collectors.toList())
		);
	}

	Optional<RoomType> roomTypeFromString(String str) {
		String[] fields = str.split("\\|");
		try {
			String typeName = fields[0];
			int numOfPlaces = Integer.parseInt(fields[1]);
			int cost = Integer.parseInt(fields[2]);
			URL smallImage = parseURL(fields[3]);
			List<URL> images = Arrays.stream(parseList(fields[4]))
					.map(this::parseURL)
					.collect(Collectors.toList());
			double area = Double.parseDouble(fields[5]);
			List<Integer> roomNumbers = Arrays.stream(parseList(fields[6]))
					.map(Integer::parseInt)
					.collect(Collectors.toList());
			List<String> services = Arrays.asList(parseList(fields[7]));
			return Optional.of(
					new RoomType(typeName, numOfPlaces, cost, smallImage, images, area, roomNumbers, services)
			);
		} catch (Exception exc) {
			exc.printStackTrace();
			return Optional.empty();
		}
	}

	Optional<List<String>> servicesFromString(String str) {
		try {
			return Optional.of(Arrays.asList(parseList(str)));
		} catch (Exception exc) {
			exc.printStackTrace();
			return Optional.empty();
		}
	}

	private String[] parseList(String str) {
		return str.substring(1, str.length() - 1)
				.split(",");
	}

	private URL parseURL(String str) {
		if (str.equals("null")) {
			return null;
		} else {
			try {
				return new URL(str);
			} catch (MalformedURLException exc) {
				return null;
			}
		}
	}
}
