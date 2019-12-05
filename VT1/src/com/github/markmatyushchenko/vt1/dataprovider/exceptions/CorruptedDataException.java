package com.github.markmatyushchenko.vt1.dataprovider.exceptions;

public class CorruptedDataException extends Exception {

	private String data;

	public CorruptedDataException(String data) {
		this.data = data;
	}

	@Override
	public String getMessage() {
		return String.format("CorruptedDataException(data=%s)", data);
	}
}
