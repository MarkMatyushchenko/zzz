package com.github.markmatyushchenko.vt1.utils;

public class Range<T extends Comparable<T>> {

	private T from;
	private T to;

	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public T getFrom() {
		return from;
	}

	public T getTo() {
		return to;
	}

	public void setTo(T to) {
		this.to = to;
	}

	public static <T extends Comparable<T>> Range<T> empty() {
		return new Range<>(null, null);
	}

	public static <T extends Comparable<T>> Range<T> from(T from) {
		return new Range<>(from, null);
	}

	public static <T extends Comparable<T>> Range<T> to(T to) {
		return new Range<>(null, to);
	}

	public boolean isEmpty() {
		return from == null && to == null;
	}

	public boolean contains(T value) {
		if (value == null) {
			return false;
		} else {
			if (from == null && to == null) {
				return true;
			} else if (from == null) {
				return value.compareTo(to) <= 0;
			} else if (to == null) {
				return value.compareTo(from) >= 0;
			} else {
				return value.compareTo(from) >= 0 && value.compareTo(to) <= 0;
			}
		}
	}

	@Override
	public String toString() {
		return String.format("[%s, %s]", from != null ? from : "∞", to != null ? to : "∞");
	}
}
