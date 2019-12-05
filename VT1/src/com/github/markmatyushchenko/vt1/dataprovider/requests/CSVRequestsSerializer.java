package com.github.markmatyushchenko.vt1.dataprovider.requests;

import com.github.markmatyushchenko.vt1.entity.request.ConfirmedRequest;
import com.github.markmatyushchenko.vt1.entity.request.RejectedRequest;
import com.github.markmatyushchenko.vt1.entity.request.Request;
import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Client;
import com.github.markmatyushchenko.vt1.entity.user.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Optional;

class CSVRequestsSerializer {

	private DateFormat dateFormat;

	CSVRequestsSerializer() {
		dateFormat = new SimpleDateFormat("dd.MM.yyy");
	}

	String serializeRequest(Request request) {
		return String.format(
				"%s|%s|%s|%s|%s|%s|%s",
				getRequestStatus(request),
				request.getRoomType().getTypeName(),
				dateFormat.format(request.getArrivalDate()),
				dateFormat.format(request.getDepartureDate()),
				Integer.toString(request.getNumberOfPersons()),
				request.getCustomer().getLogin(),
				getAdditionalInfo(request)
		);
	}

	Map<String, String> parseRequest(String line) {
		String[] fields = line.split("\\|");
		try {
			return Map.of(
					"status", fields[0],
					"roomTypeName", fields[1],
					"arrivalDate", fields[2],
					"departureDate", fields[3],
					"numOfPersons", fields[4],
					"customerLogin", fields[5],
					"additionalInfo", fields.length > 6 ? fields[6] : ""
			);
		} catch (ArrayIndexOutOfBoundsException exc) {
			exc.printStackTrace();
			return Map.of();
		}
	}

	Optional<Request> requestFromMap(Map<String, String> map, RoomType roomType, User customer) {
		if (roomType != null && customer instanceof Client) {
			try {
				Request request = new Request(
						roomType,
						dateFormat.parse(map.get("arrivalDate")),
						dateFormat.parse(map.get("departureDate")),
						Integer.parseInt(map.get("numOfPersons")),
						(Client) customer
				);
				switch (map.get("status")) {
					case "processing":
						return Optional.of(request);
					case "confirmed":
						return Optional.of(new ConfirmedRequest(
								request,
								Integer.parseInt(map.get("additionalInfo"))
						));
					case "rejected":
						return Optional.of(new RejectedRequest(
								request,
								map.get("additionalInfo")
						));
					default:
						return Optional.empty();
				}
			} catch (Exception exc) {
				exc.printStackTrace();
				return Optional.empty();
			}
		} else return Optional.empty();
	}

	boolean equalsMapWithRequest(Map<String, String> map, Request request) {
		return map.get("status") != null && map.get("status").equals(getRequestStatus(request))
				&& map.get("roomTypeName") != null && map.get("roomTypeName").equals(request.getRoomType().getTypeName())
				&& map.get("arrivalDate") != null && map.get("arrivalDate").equals(dateFormat.format(request.getArrivalDate()))
				&& map.get("departureDate") != null && map.get("departureDate").equals(dateFormat.format(request.getDepartureDate()))
				&& map.get("numOfPersons") != null && map.get("numOfPersons").equals(Integer.toString(request.getNumberOfPersons()))
				&& map.get("customerLogin") != null && map.get("customerLogin").equals(request.getCustomer().getLogin())
				&& map.get("additionalInfo") != null && map.get("additionalInfo").equals(getAdditionalInfo(request));
	}

	private String getRequestStatus(Request request) {
		if (request instanceof ConfirmedRequest) {
			return "confirmed";
		} else if (request instanceof RejectedRequest) {
			return "rejected";
		} else return "processing";
	}

	private String getAdditionalInfo(Request request) {
		if (request instanceof ConfirmedRequest) {
			return Integer.toString(((ConfirmedRequest) request).getRoomNumber());
		} else if (request instanceof RejectedRequest) {
			return ((RejectedRequest) request).getComment();
		} else return "";
	}
}
