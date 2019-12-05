package com.github.markmatyushchenko.vt1.entity.request;

import com.github.markmatyushchenko.vt1.entity.roomtype.RoomType;
import com.github.markmatyushchenko.vt1.entity.user.Client;

import java.util.Date;

public class RejectedRequest extends Request {

	private String comment;

	public RejectedRequest(Request request, String comment) {
		this(request.getRoomType(), request.getArrivalDate(), request.getDepartureDate(), request.getNumberOfPersons(), request.getCustomer(), comment);
	}

	public RejectedRequest(RoomType roomType, Date arrivalDate, Date departureDate, int numberOfPersons, Client customer, String comment) {
		super(roomType, arrivalDate, departureDate, numberOfPersons, customer);
		this.comment = comment;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof RejectedRequest)
				&& super.equals(obj)
				&& comment.equals(((RejectedRequest) obj).comment);
	}

	@Override
	public String toString() {
		return String.format("RejectedRequest(comment=%s, roomType=%s, arrivalDate=%s, departureDate=%s, numberOfPersons=%d, customer=%s)", comment, getRoomType(), getArrivalDate(), getDepartureDate(), getNumberOfPersons(), getCustomer());
	}
}
